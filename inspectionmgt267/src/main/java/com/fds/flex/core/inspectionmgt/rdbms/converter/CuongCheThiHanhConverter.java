package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.CuongCheThiHanh;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class CuongCheThiHanhConverter implements AttributeConverter<List<CuongCheThiHanh>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<CuongCheThiHanh> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<CuongCheThiHanh> to JSON", e);
        }
    }

    @Override
    public List<CuongCheThiHanh> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<CuongCheThiHanh>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<CuongCheThiHanh>", e);
        }
    }

}
