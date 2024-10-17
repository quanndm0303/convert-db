package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.LoaiChiTietVuViec_LoaiVuViecDonThu;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class LoaiChiTietVuViec_LoaiVuViecDonThuConverter implements AttributeConverter<LoaiChiTietVuViec_LoaiVuViecDonThu, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(LoaiChiTietVuViec_LoaiVuViecDonThu attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting LoaiChiTietVuViec_LoaiVuViecDonThu to JSON", e);
        }
    }

    @Override
    public LoaiChiTietVuViec_LoaiVuViecDonThu convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, LoaiChiTietVuViec_LoaiVuViecDonThu.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to LoaiChiTietVuViec_LoaiVuViecDonThu", e);
        }
    }
}
