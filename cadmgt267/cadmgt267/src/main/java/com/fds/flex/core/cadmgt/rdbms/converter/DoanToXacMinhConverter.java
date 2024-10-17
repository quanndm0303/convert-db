package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.S_Model.DoanToXacMinh;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class DoanToXacMinhConverter implements AttributeConverter<DoanToXacMinh, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DoanToXacMinh attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting DoanToXacMinh to JSON", e);
        }
    }

    @Override
    public DoanToXacMinh convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, DoanToXacMinh.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to DoanToXacMinh", e);
        }
    }
}
