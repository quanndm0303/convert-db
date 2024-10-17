package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.ThanhVienDoan;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class ThanhVienDoanConverter implements AttributeConverter<List<ThanhVienDoan>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ThanhVienDoan> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<ThanhVienDoan> to JSON", e);
        }
    }

    @Override
    public List<ThanhVienDoan> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<ThanhVienDoan>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<ThanhVienDoan>", e);
        }
    }

}
