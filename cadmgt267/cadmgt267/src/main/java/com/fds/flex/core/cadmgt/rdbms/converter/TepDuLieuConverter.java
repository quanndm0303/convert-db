package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.S_Model.TepDuLieu;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class TepDuLieuConverter implements AttributeConverter<TepDuLieu, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TepDuLieu attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting TepDuLieu to JSON", e);
        }
    }

    @Override
    public TepDuLieu convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, TepDuLieu.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to TepDuLieu", e);
        }
    }
}
