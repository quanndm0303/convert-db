package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.XuLyVPHC_ChuyenCoQuanThiHanh;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyVPHC_ChuyenCoQuanThiHanhConverter implements AttributeConverter<XuLyVPHC_ChuyenCoQuanThiHanh, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyVPHC_ChuyenCoQuanThiHanh attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyVPHC_ChuyenCoQuanThiHanh to JSON", e);
        }
    }

    @Override
    public XuLyVPHC_ChuyenCoQuanThiHanh convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyVPHC_ChuyenCoQuanThiHanh.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyVPHC_ChuyenCoQuanThiHanh", e);
        }
    }

}
