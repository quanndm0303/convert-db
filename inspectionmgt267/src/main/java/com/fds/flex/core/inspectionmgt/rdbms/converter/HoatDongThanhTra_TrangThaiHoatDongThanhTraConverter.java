package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_TrangThaiHoatDongThanhTra;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class HoatDongThanhTra_TrangThaiHoatDongThanhTraConverter implements AttributeConverter<HoatDongThanhTra_TrangThaiHoatDongThanhTra, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(HoatDongThanhTra_TrangThaiHoatDongThanhTra attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting HoatDongThanhTra_TrangThaiHoatDongThanhTra to JSON", e);
        }
    }

    @Override
    public HoatDongThanhTra_TrangThaiHoatDongThanhTra convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, HoatDongThanhTra_TrangThaiHoatDongThanhTra.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to HoatDongThanhTra_TrangThaiHoatDongThanhTra", e);
        }
    }

}
