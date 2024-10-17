package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.VuViecDonThu_CanBoXuLy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class VuViecDonThu_CanBoXuLyConverter implements AttributeConverter<List<VuViecDonThu_CanBoXuLy>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<VuViecDonThu_CanBoXuLy> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<VuViecDonThu_CanBoXuLy> to JSON", e);
        }
    }

    @Override
    public List<VuViecDonThu_CanBoXuLy> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<VuViecDonThu_CanBoXuLy>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<VuViecDonThu_CanBoXuLy>", e);
        }
    }
}
