package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_DonViTheoDoi;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyDonThu_DonViTheoDoiConverter implements AttributeConverter<XuLyDonThu_DonViTheoDoi, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyDonThu_DonViTheoDoi attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyDonThu_DonViTheoDoi to JSON", e);
        }
    }

    @Override
    public XuLyDonThu_DonViTheoDoi convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyDonThu_DonViTheoDoi.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyDonThu_DonViTheoDoi", e);
        }
    }
}
