package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_VanBanPhucDap;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyDonThu_VanBanPhucDapConverter implements AttributeConverter<XuLyDonThu_VanBanPhucDap, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyDonThu_VanBanPhucDap attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyDonThu_VanBanPhucDap to JSON", e);
        }
    }

    @Override
    public XuLyDonThu_VanBanPhucDap convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyDonThu_VanBanPhucDap.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyDonThu_VanBanPhucDap", e);
        }
    }
}
