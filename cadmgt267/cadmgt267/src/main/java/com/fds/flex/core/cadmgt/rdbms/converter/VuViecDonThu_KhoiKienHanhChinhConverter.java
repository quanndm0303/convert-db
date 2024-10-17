package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.VuViecDonThu_KhoiKienHanhChinh;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class VuViecDonThu_KhoiKienHanhChinhConverter implements AttributeConverter<VuViecDonThu_KhoiKienHanhChinh, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(VuViecDonThu_KhoiKienHanhChinh attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting VuViecDonThu_KhoiKienHanhChinh to JSON", e);
        }
    }

    @Override
    public VuViecDonThu_KhoiKienHanhChinh convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, VuViecDonThu_KhoiKienHanhChinh.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to VuViecDonThu_KhoiKienHanhChinh", e);
        }
    }
}
