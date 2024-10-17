package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.S_Model.KiemTraThuLy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class KiemTraThuLyConverter implements AttributeConverter<KiemTraThuLy, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(KiemTraThuLy attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting KiemTraThuLy to JSON", e);
        }
    }

    @Override
    public KiemTraThuLy convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, KiemTraThuLy.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to KiemTraThuLy", e);
        }
    }
}
