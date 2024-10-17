package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.CaNhanToChuc_GioiTinh;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class CaNhanToChuc_GioiTinhConverter implements AttributeConverter<CaNhanToChuc_GioiTinh, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CaNhanToChuc_GioiTinh attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting CaNhanToChuc_GioiTinh to JSON", e);
        }
    }

    @Override
    public CaNhanToChuc_GioiTinh convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, CaNhanToChuc_GioiTinh.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to CaNhanToChuc_GioiTinh", e);
        }
    }

}
