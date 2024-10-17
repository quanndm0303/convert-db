package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_DoiTuongThanhTra;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class HoatDongThanhTra_DoiTuongThanhTraConverter implements AttributeConverter<List<HoatDongThanhTra_DoiTuongThanhTra>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<HoatDongThanhTra_DoiTuongThanhTra> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<HoatDongThanhTra_DoiTuongThanhTra> to JSON", e);
        }
    }

    @Override
    public List<HoatDongThanhTra_DoiTuongThanhTra> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<HoatDongThanhTra_DoiTuongThanhTra>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<HoatDongThanhTra_DoiTuongThanhTra>", e);
        }
    }

}
