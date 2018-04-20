package com.example.io.writer;

import com.example.io.exception.JsonIOException;
import com.example.result.ResultInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import static com.example.io.writer.FileNameGenerator.getFileName;

public class JsonResultInfoWriter implements ResultInfoWriter {

    @Override
    public void write(ResultInfo resultInfo, String pathToWriteFile) {
        String fileName = pathToWriteFile + "/" + getFileName();
        File file = new File(fileName);
        try  {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, resultInfo);
        } catch (IOException exception) {
            throw new JsonIOException(exception.getMessage());
        }
    }
}
