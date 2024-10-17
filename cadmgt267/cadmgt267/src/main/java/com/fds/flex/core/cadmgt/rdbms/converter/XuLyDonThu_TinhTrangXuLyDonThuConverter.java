package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_TinhTrangXuLyDonThu;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyDonThu_TinhTrangXuLyDonThuConverter implements AttributeConverter<XuLyDonThu_TinhTrangXuLyDonThu, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyDonThu_TinhTrangXuLyDonThu attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyDonThu_TinhTrangXuLyDonThu to JSON", e);
        }
    }

    @Override
    public XuLyDonThu_TinhTrangXuLyDonThu convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyDonThu_TinhTrangXuLyDonThu.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyDonThu_TinhTrangXuLyDonThu", e);
        }
    }
}
