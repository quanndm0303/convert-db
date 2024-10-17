package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.NoiDungVPHC;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class NoiDungVPHCConverter implements AttributeConverter<List<NoiDungVPHC>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<NoiDungVPHC> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<NoiDungVPHC> to JSON", e);
        }
    }

    @Override
    public List<NoiDungVPHC> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<NoiDungVPHC>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<NoiDungVPHC>", e);
        }
    }

}
