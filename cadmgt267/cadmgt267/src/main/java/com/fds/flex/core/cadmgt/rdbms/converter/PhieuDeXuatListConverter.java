package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.S_Model.PhieuDeXuat;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Converter(autoApply = true)
public class PhieuDeXuatListConverter implements AttributeConverter<List<PhieuDeXuat>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<PhieuDeXuat> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<PhieuDeXuat>", e);
        }
    }

    @Override
    public List<PhieuDeXuat> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<PhieuDeXuat>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<PhieuDeXuat>", e);
        }
    }
}
