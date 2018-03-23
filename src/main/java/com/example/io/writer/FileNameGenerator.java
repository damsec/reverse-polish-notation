package com.example.io.writer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class FileNameGenerator {

    static String getFileName() {
        return "result-" + getFormattedLocalDateTime() + ".json";
    }

    static private LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    static private String getFormattedLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return getCurrentDateTime().format(formatter);
    }
}
