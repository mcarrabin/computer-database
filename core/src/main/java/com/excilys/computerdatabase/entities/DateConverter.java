package com.excilys.computerdatabase.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDateTime, java.sql.Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime arg0) {
        System.out.println("date converter on");
        return (arg0 == null ? null : Timestamp.valueOf(arg0));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp arg0) {
        // System.out.println("date converter on 2: " + arg0.toString());
        // Timestamp temp = new Timestamp(0, 0, 0, 0, 0, 0, 0);
        // if(temp.equals(arg0))
        // arg0.e
        return (arg0 == null ? null : arg0.toLocalDateTime());
    }

}