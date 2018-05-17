package com.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeGenerator {

    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyyMMdd-HHmmss";

    public static String getFormattedDateTime() {
        return getFormattedDateTime(DEFAULT_DATE_TIME_FORMAT);
    }

    public static String getFormattedDateTime(String dateTimeFormat) {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(dateTimeFormat));
    }
}
