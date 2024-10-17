package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.TiepNhanVuViec;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class TiepNhanVuViecConverter implements AttributeConverter<TiepNhanVuViec, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TiepNhanVuViec attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting TiepNhanVuViec to JSON", e);
        }
    }

    @Override
    public TiepNhanVuViec convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, TiepNhanVuViec.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to TiepNhanVuViec", e);
        }
    }

}
