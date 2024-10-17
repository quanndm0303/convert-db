package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HanhViVPHC_NghiDinhXPVPHC;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class HanhViVPHC_NghiDinhXPVPHCConverter implements AttributeConverter<HanhViVPHC_NghiDinhXPVPHC, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(HanhViVPHC_NghiDinhXPVPHC attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting HanhViVPHC_NghiDinhXPVPHC to JSON", e);
        }
    }

    @Override
    public HanhViVPHC_NghiDinhXPVPHC convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, HanhViVPHC_NghiDinhXPVPHC.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to HanhViVPHC_NghiDinhXPVPHC", e);
        }
    }

}
