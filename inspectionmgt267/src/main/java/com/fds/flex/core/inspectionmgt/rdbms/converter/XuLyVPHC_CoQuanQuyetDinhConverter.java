package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.XuLyVPHC_CoQuanQuyetDinh;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyVPHC_CoQuanQuyetDinhConverter implements AttributeConverter<XuLyVPHC_CoQuanQuyetDinh, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyVPHC_CoQuanQuyetDinh attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyVPHC_CoQuanQuyetDinh to JSON", e);
        }
    }

    @Override
    public XuLyVPHC_CoQuanQuyetDinh convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyVPHC_CoQuanQuyetDinh.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyVPHC_CoQuanQuyetDinh", e);
        }
    }

}
