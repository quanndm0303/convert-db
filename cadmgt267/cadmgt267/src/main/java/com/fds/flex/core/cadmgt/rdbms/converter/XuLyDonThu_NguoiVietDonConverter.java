package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_NguoiVietDon;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class XuLyDonThu_NguoiVietDonConverter implements AttributeConverter<List<XuLyDonThu_NguoiVietDon>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<XuLyDonThu_NguoiVietDon> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<XuLyDonThu_NguoiVietDon> to JSON", e);
        }
    }

    @Override
    public List<XuLyDonThu_NguoiVietDon> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<XuLyDonThu_NguoiVietDon>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<XuLyDonThu_NguoiVietDon>", e);
        }
    }
}
