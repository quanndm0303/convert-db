package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HanhViVPHC_NhomHanhViVPHC;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class HanhViVPHC_NhomHanhViVPHCConverter implements AttributeConverter<HanhViVPHC_NhomHanhViVPHC, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(HanhViVPHC_NhomHanhViVPHC attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting HanhViVPHC_NhomHanhViVPHC to JSON", e);
        }
    }

    @Override
    public HanhViVPHC_NhomHanhViVPHC convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, HanhViVPHC_NhomHanhViVPHC.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to HanhViVPHC_NhomHanhViVPHC", e);
        }
    }

}
