package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_LoaiDoiTuongDungTen;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class XuLyDonThu_LoaiDoiTuongDungTenConverter implements AttributeConverter<XuLyDonThu_LoaiDoiTuongDungTen, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(XuLyDonThu_LoaiDoiTuongDungTen attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting XuLyDonThu_LoaiDoiTuongDungTen to JSON", e);
        }
    }

    @Override
    public XuLyDonThu_LoaiDoiTuongDungTen convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, XuLyDonThu_LoaiDoiTuongDungTen.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to XuLyDonThu_LoaiDoiTuongDungTen", e);
        }
    }
}
