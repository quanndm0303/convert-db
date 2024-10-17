package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.VuViecDonThu_LanhDaoQuanLy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class VuViecDonThu_LanhDaoQuanLyConverter implements AttributeConverter<VuViecDonThu_LanhDaoQuanLy, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(VuViecDonThu_LanhDaoQuanLy attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting VuViecDonThu_LanhDaoQuanLy to JSON", e);
        }
    }

    @Override
    public VuViecDonThu_LanhDaoQuanLy convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, VuViecDonThu_LanhDaoQuanLy.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to VuViecDonThu_LanhDaoQuanLy", e);
        }
    }
}
