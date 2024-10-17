package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.DanhBaLienLac;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class DanhBaLienLacConverter implements AttributeConverter<DanhBaLienLac, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DanhBaLienLac attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting DanhBaLienLac to JSON", e);
        }
    }

    @Override
    public DanhBaLienLac convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<DanhBaLienLac>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to DanhBaLienLac", e);
        }
    }

}
