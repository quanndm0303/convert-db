package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_LinhVucChuyenNganh;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class HoatDongThanhTra_LinhVucChuyenNganhConverter implements AttributeConverter<HoatDongThanhTra_LinhVucChuyenNganh, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(HoatDongThanhTra_LinhVucChuyenNganh attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting HoatDongThanhTra_LinhVucChuyenNganh to JSON", e);
        }
    }

    @Override
    public HoatDongThanhTra_LinhVucChuyenNganh convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, HoatDongThanhTra_LinhVucChuyenNganh.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to HoatDongThanhTra_LinhVucChuyenNganh", e);
        }
    }

}
