package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.XuLyVPHC_DonViChuTri;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyVPHC_DonViChuTriConverter implements AttributeConverter<XuLyVPHC_DonViChuTri, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyVPHC_DonViChuTri attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyVPHC_DonViChuTri to JSON", e);
        }
    }

    @Override
    public XuLyVPHC_DonViChuTri convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyVPHC_DonViChuTri.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyVPHC_DonViChuTri", e);
        }
    }

}
