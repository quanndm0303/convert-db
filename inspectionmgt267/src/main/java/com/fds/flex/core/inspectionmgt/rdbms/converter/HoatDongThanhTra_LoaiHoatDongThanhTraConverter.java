package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_LoaiHoatDongThanhTra;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class HoatDongThanhTra_LoaiHoatDongThanhTraConverter implements AttributeConverter<HoatDongThanhTra_LoaiHoatDongThanhTra, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(HoatDongThanhTra_LoaiHoatDongThanhTra attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting HoatDongThanhTra_LoaiHoatDongThanhTra to JSON", e);
        }
    }

    @Override
    public HoatDongThanhTra_LoaiHoatDongThanhTra convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, HoatDongThanhTra_LoaiHoatDongThanhTra.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to HoatDongThanhTra_LoaiHoatDongThanhTra", e);
        }
    }

}
