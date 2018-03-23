package com.example.io.writer;

import static com.example.utils.DateTimeGenerator.getFormattedDateTime;

public class FileNameGenerator {

    public static String getFileName() {
        return "result-" + getFormattedDateTime("yyyyMMdd-HHmmss") + ".json";
    }
}
