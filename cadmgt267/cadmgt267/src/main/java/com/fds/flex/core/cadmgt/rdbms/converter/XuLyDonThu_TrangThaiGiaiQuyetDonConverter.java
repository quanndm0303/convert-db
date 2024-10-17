package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_TrangThaiGiaiQuyetDon;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyDonThu_TrangThaiGiaiQuyetDonConverter implements AttributeConverter<XuLyDonThu_TrangThaiGiaiQuyetDon, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyDonThu_TrangThaiGiaiQuyetDon attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyDonThu_TrangThaiGiaiQuyetDon to JSON", e);
        }
    }

    @Override
    public XuLyDonThu_TrangThaiGiaiQuyetDon convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyDonThu_TrangThaiGiaiQuyetDon.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyDonThu_TrangThaiGiaiQuyetDon", e);
        }
    }
}
