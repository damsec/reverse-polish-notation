package com.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeGenerator {

    public static String getFormattedDateTime(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return getCurrentDateTime().format(formatter);
    }

    private static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
