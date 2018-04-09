package com.example.io.writer;

import com.example.io.exception.JsonIOException;
import com.example.io.writer.mapper.GeneralResultMapper;
import com.example.result.GeneralResult;

import java.io.FileWriter;
import java.io.IOException;

import static com.example.io.writer.FileNameGenerator.getFileName;

public class JsonGeneralResultWriter implements GeneralResultWriter {

    private GeneralResultMapper mapper;

    public JsonGeneralResultWriter(GeneralResultMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void write(GeneralResult generalResult, String pathToWriteFile) {
        String fileName = pathToWriteFile + "/" + getFileName();
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            String jsonString = mapper.mapGeneralResultToJsonString(generalResult);
            fileWriter.write(jsonString);
        } catch (IOException exception) {
            throw new JsonIOException(exception.getMessage());
        }
    }
}
