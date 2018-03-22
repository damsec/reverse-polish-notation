package com.example.io.writer;

import com.example.model.GeneralResult;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter implements Writer {

    private FileNameGenerator fileNameGenerator;

    public JsonWriter(FileNameGenerator fileNameGenerator) {
        this.fileNameGenerator = fileNameGenerator;
    }

    @Override
    public void write(GeneralResult generalResult, String pathToWriteFile) {
        try (FileWriter fileWriter = new FileWriter(pathToWriteFile + "/" + fileNameGenerator.getFileName())) {
            JSONObject jsonObject = mapGeneralResultToJsonObject(generalResult);
            jsonObject.write(fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private JSONObject mapGeneralResultToJsonObject(GeneralResult generalResult) {
        return new JSONObject()
                .put("creationDateTime", generalResult.getCreationDateTime())
                .put("results", generalResult.getResults())
                .put("totalExpressionsNumber", generalResult.getTotalExpressionsNumber())
                .put("successNumber", generalResult.getSuccessNumber())
                .put("errorNumber", generalResult.getErrorNumber());
    }
}
