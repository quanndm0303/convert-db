package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_CoQuanQuanLy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class HoatDongThanhTra_CoQuanQuanLyConverter implements AttributeConverter<HoatDongThanhTra_CoQuanQuanLy, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(HoatDongThanhTra_CoQuanQuanLy attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting HoatDongThanhTra_CoQuanQuanLy to JSON", e);
        }
    }

    @Override
    public HoatDongThanhTra_CoQuanQuanLy convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, HoatDongThanhTra_CoQuanQuanLy.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to HoatDongThanhTra_CoQuanQuanLy", e);
        }
    }

}
