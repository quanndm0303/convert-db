package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.VuViecDonThu_LoaiChiTietVuViec;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class VuViecDonThu_LoaiChiTietVuViecConverter implements AttributeConverter<VuViecDonThu_LoaiChiTietVuViec, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(VuViecDonThu_LoaiChiTietVuViec attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting VuViecDonThu_LoaiChiTietVuViec to JSON", e);
        }
    }

    @Override
    public VuViecDonThu_LoaiChiTietVuViec convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, VuViecDonThu_LoaiChiTietVuViec.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to VuViecDonThu_LoaiChiTietVuViec", e);
        }
    }
}
