package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.CaNhanToChuc_CanBoChienSi;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class CaNhanToChuc_CanBoChienSiConverter implements AttributeConverter<CaNhanToChuc_CanBoChienSi, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CaNhanToChuc_CanBoChienSi attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting CaNhanToChuc_CanBoChienSi to JSON", e);
        }
    }

    @Override
    public CaNhanToChuc_CanBoChienSi convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, CaNhanToChuc_CanBoChienSi.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to CaNhanToChuc_CanBoChienSi", e);
        }
    }

}
