package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.ChuyenCoQuanDieuTra;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ChuyenCoQuanDieuTraConverter implements AttributeConverter<ChuyenCoQuanDieuTra, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ChuyenCoQuanDieuTra attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting ChuyenCoQuanDieuTra to JSON", e);
        }
    }

    @Override
    public ChuyenCoQuanDieuTra convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ChuyenCoQuanDieuTra.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to ChuyenCoQuanDieuTra", e);
        }
    }

}
