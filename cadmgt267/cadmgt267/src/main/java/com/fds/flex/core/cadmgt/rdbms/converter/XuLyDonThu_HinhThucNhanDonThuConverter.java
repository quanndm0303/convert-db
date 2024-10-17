package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_HinhThucNhanDonThu;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyDonThu_HinhThucNhanDonThuConverter implements AttributeConverter<XuLyDonThu_HinhThucNhanDonThu, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyDonThu_HinhThucNhanDonThu attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyDonThu_HinhThucNhanDonThu to JSON", e);
        }
    }

    @Override
    public XuLyDonThu_HinhThucNhanDonThu convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyDonThu_HinhThucNhanDonThu.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyDonThu_HinhThucNhanDonThu", e);
        }    }
}
