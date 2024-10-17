package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.ThanhPhanGiayTo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class ThanhPhanGiayToConverter implements AttributeConverter<List<ThanhPhanGiayTo>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ThanhPhanGiayTo> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<ThanhPhanGiayTo> to JSON", e);
        }
    }

    @Override
    public List<ThanhPhanGiayTo> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<ThanhPhanGiayTo>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<ThanhPhanGiayTo>", e);
        }
    }

}
