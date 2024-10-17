package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_TiepCongDan;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyDonThu_TiepCongDanConverter implements AttributeConverter<XuLyDonThu_TiepCongDan, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyDonThu_TiepCongDan attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyDonThu_TiepCongDan to JSON", e);
        }
    }

    @Override
    public XuLyDonThu_TiepCongDan convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyDonThu_TiepCongDan.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyDonThu_TiepCongDan", e);
        }
    }
}
