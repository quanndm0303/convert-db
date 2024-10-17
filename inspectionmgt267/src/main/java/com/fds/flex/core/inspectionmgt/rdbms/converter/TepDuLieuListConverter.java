package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.TepDuLieu;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class TepDuLieuListConverter implements AttributeConverter<List<TepDuLieu>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<TepDuLieu> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<TepDuLieu> to JSON", e);
        }
    }

    @Override
    public List<TepDuLieu> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<TepDuLieu>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<TepDuLieu>", e);
        }
    }

}
