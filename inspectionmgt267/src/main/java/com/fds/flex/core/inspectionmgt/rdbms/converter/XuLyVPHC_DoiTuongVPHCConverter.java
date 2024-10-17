package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.XuLyVPHC_DoiTuongVPHC;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyVPHC_DoiTuongVPHCConverter implements AttributeConverter<XuLyVPHC_DoiTuongVPHC, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyVPHC_DoiTuongVPHC attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyVPHC_DoiTuongVPHC to JSON", e);
        }
    }

    @Override
    public XuLyVPHC_DoiTuongVPHC convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyVPHC_DoiTuongVPHC.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyVPHC_DoiTuongVPHC", e);
        }
    }

}
