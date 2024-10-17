package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_CanBoXuLy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Converter(autoApply = true)
public class XuLyDonThu_CanBoXuLyConverter implements AttributeConverter<List<XuLyDonThu_CanBoXuLy>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<XuLyDonThu_CanBoXuLy> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<XuLyDonThu_CanBoXuLy> to JSON", e);
        }
    }

    @Override
    public List<XuLyDonThu_CanBoXuLy> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<XuLyDonThu_CanBoXuLy>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<XuLyDonThu_CanBoXuLy>", e);
        }
    }
}
