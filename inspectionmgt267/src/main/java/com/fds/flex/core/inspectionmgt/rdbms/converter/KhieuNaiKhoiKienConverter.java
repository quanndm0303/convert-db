package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.KhieuNaiKhoiKien;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class KhieuNaiKhoiKienConverter implements AttributeConverter<List<KhieuNaiKhoiKien>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<KhieuNaiKhoiKien> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<KhieuNaiKhoiKien> to JSON", e);
        }
    }

    @Override
    public List<KhieuNaiKhoiKien> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<KhieuNaiKhoiKien>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<KhieuNaiKhoiKien>", e);
        }
    }

}
