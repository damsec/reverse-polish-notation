package com.example.io.writer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileNameGenerator {

     String getFileName() {
        return "result-" + getFormattedLocalDateTime() + ".json";
    }
    
    private LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
    private String getFormattedLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return getCurrentDateTime().format(formatter);
    }
}
