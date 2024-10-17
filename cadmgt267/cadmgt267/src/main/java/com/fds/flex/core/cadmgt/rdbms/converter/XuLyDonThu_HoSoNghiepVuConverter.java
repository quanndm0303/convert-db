package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_HoSoNghiepVu;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_LanhDaoChuyen;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyDonThu_HoSoNghiepVuConverter implements AttributeConverter<XuLyDonThu_HoSoNghiepVu, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyDonThu_HoSoNghiepVu attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyDonThu_HoSoNghiepVu to JSON", e);
        }
    }

    @Override
    public XuLyDonThu_HoSoNghiepVu convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyDonThu_HoSoNghiepVu.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyDonThu_HoSoNghiepVu", e);
        }    }
}
