package com.example.io.reader;

import com.example.expression.InfixExpression;
import com.example.io.exception.FileMissingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class JsonExpressionReader implements ExpressionReader<InfixExpression> {

    @Override
    public List<InfixExpression> read(String filePath) {
        InputStream inputStream = getInputStream(filePath);
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<InfixExpression> infixExpressions = mapper.readValue(inputStream, new TypeReference<List<InfixExpression>>() {});
            return infixExpressions.stream()
                    .filter(infixExpression -> !isNull(infixExpression.getExpression()))
                    .filter(infixExpression -> !infixExpression.getExpression().isEmpty())
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private InputStream getInputStream(String filePath) {
        if (isNull(filePath) || filePath.isEmpty()) {
            throw new FileMissingException("File path can't be null or empty.");
        }
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException exception) {
            throw new FileMissingException("Couldn't find file.");
        }
    }
}
