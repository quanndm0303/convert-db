package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.VuViecDonThu_TinhTrangXuLyVuViec;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class VuViecDonThu_TinhTrangXuLyVuViecConverter implements AttributeConverter<VuViecDonThu_TinhTrangXuLyVuViec, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(VuViecDonThu_TinhTrangXuLyVuViec attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting VuViecDonThu_TinhTrangXuLyVuViec to JSON", e);
        }
    }

    @Override
    public VuViecDonThu_TinhTrangXuLyVuViec convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, VuViecDonThu_TinhTrangXuLyVuViec.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to VuViecDonThu_TinhTrangXuLyVuViec", e);
        }
    }
}
