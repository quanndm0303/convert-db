package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_LoaiCheDoThanhTra;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class HoatDongThanhTra_LoaiCheDoThanhTraConverter implements AttributeConverter<HoatDongThanhTra_LoaiCheDoThanhTra, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(HoatDongThanhTra_LoaiCheDoThanhTra attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting HoatDongThanhTra_LoaiCheDoThanhTra to JSON", e);
        }
    }

    @Override
    public HoatDongThanhTra_LoaiCheDoThanhTra convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, HoatDongThanhTra_LoaiCheDoThanhTra.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to HoatDongThanhTra_LoaiCheDoThanhTra", e);
        }
    }

}
