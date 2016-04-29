package com.excilys.computerdatabase.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateMapper {
    private final static String FORMAT_TYPE = "dd/MM/yyyy";

    public static LocalDateTime toLocalDateTime(String date) {
        LocalDateTime result = null;
        if (date != null && date.length() != 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_TYPE);
            result = LocalDate.parse(date, formatter).atStartOfDay();
        }
        return result;
    }

    public static String toString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_TYPE);
        String result = date.format(formatter);
        return result;
    }
}
