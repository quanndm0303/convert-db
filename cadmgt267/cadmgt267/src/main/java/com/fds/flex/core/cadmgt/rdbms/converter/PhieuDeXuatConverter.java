package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.S_Model.PhieuDeXuat;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class PhieuDeXuatConverter implements AttributeConverter<PhieuDeXuat, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(PhieuDeXuat attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting PhieuDeXuat to JSON", e);
        }
    }

    @Override
    public PhieuDeXuat convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, PhieuDeXuat.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to PhieuDeXuat", e);
        }
    }
}
