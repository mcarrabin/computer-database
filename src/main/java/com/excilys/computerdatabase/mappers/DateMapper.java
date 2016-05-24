package com.excilys.computerdatabase.mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component("dateMapper")
public class DateMapper {
    // INSTANCE;
    // Pattern of the date
    private final static String FORMAT_TYPE = "dd-MM-yyyy";

    /**
     * Method that will translate a String to a LocalDateTime.
     *
     * @param date
     *            is the String to transform.
     * @return the LocalDateTime.
     */

    public static LocalDate toLocalDate(String date) {
        LocalDate result = null;
        if (date != null && date.length() != 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_TYPE);
            result = LocalDate.parse(date, formatter);
        }
        return result;
    }

    /**
     * Method that will translate a LocalDateTime date to a String.
     *
     * @param date
     *            is the LocalDateTime date to transform.
     * @return the String format date.
     */
    public static String toString(LocalDate date) {
        if (date == null) {
            return "";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_TYPE);
            String result = date.format(formatter);
            return result;
        }
    }
}
