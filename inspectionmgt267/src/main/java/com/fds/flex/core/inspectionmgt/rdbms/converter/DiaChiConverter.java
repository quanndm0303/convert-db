package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.DiaChi;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class DiaChiConverter implements AttributeConverter<DiaChi, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DiaChi attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting DiaChi to JSON", e);
        }
    }

    @Override
    public DiaChi convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, DiaChi.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to DiaChi", e);
        }
    }

}
