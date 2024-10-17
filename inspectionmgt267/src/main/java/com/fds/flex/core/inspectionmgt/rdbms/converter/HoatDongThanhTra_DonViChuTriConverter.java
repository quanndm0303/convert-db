package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_DonViChuTri;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class HoatDongThanhTra_DonViChuTriConverter implements AttributeConverter<HoatDongThanhTra_DonViChuTri, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(HoatDongThanhTra_DonViChuTri attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting HoatDongThanhTra_DonViChuTri to JSON", e);
        }
    }

    @Override
    public HoatDongThanhTra_DonViChuTri convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, HoatDongThanhTra_DonViChuTri.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to HoatDongThanhTra_DonViChuTri", e);
        }
    }

}
