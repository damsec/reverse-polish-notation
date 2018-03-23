package com.example.io.reader;

import com.example.io.exception.FileMissingException;
import com.example.io.exception.JsonFormatException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class JsonReader implements Reader {

    @Override
    public List<String> read(String filePath) {
        InputStream inputStream = getInputStream(filePath);
        JSONObject jsonObject = getJsonObject(inputStream);
        try {
            JSONArray infixExpressions = jsonObject.getJSONArray("infixExpressions");
            return getInfixExpressions(infixExpressions);
        } catch (JSONException exception) {
            throw new JsonFormatException("JSON should contains following key/value pair \"infixExpressions\": []");
        }
    }

    private List<String> getInfixExpressions(JSONArray infixExpressions) {
        return infixExpressions.toList()
                .stream()
                .filter(expression -> !isNull(expression))
                .map(String::valueOf)
                .filter(expression -> !expression.isEmpty())
                .collect(toList());
    }

    private JSONObject getJsonObject(InputStream inputStream) {
        try {
            JSONTokener jsonTokener = new JSONTokener(inputStream);
            return new JSONObject(jsonTokener);
        } catch (JSONException exception) {
            throw new JsonFormatException(exception.getMessage());
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
