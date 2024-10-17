package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.CaNhanToChuc_QuocTich;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class CaNhanToChuc_QuocTichConverter implements AttributeConverter<CaNhanToChuc_QuocTich, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CaNhanToChuc_QuocTich attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting CaNhanToChuc_QuocTich to JSON", e);
        }
    }

    @Override
    public CaNhanToChuc_QuocTich convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, CaNhanToChuc_QuocTich.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to CaNhanToChuc_QuocTich", e);
        }
    }

}
