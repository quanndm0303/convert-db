package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.XuLyVPHC_TrangThaiXuLyVPHC;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyVPHC_TrangThaiXuLyVPHCConverter implements AttributeConverter<XuLyVPHC_TrangThaiXuLyVPHC, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyVPHC_TrangThaiXuLyVPHC attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyVPHC_TrangThaiXuLyVPHC to JSON", e);
        }
    }

    @Override
    public XuLyVPHC_TrangThaiXuLyVPHC convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyVPHC_TrangThaiXuLyVPHC.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyVPHC_TrangThaiXuLyVPHC", e);
        }
    }

}
