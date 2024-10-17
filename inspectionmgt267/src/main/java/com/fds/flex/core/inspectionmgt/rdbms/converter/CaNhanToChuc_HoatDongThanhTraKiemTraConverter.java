package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.CaNhanToChuc_HoatDongThanhTraKiemTra;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class CaNhanToChuc_HoatDongThanhTraKiemTraConverter implements AttributeConverter<List<CaNhanToChuc_HoatDongThanhTraKiemTra>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<CaNhanToChuc_HoatDongThanhTraKiemTra> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<CaNhanToChuc_HoatDongThanhTraKiemTra> to JSON", e);
        }
    }

    @Override
    public List<CaNhanToChuc_HoatDongThanhTraKiemTra> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<CaNhanToChuc_HoatDongThanhTraKiemTra>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<CaNhanToChuc_HoatDongThanhTraKiemTra>", e);
        }
    }

}
