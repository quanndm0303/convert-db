package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.LinhVucChuyenNganh_LoaiVuViecDonThu;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Converter(autoApply = true)
public class LinhVucChuyenNganh_LoaiVuViecDonThuConverter implements AttributeConverter<List<LinhVucChuyenNganh_LoaiVuViecDonThu>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<LinhVucChuyenNganh_LoaiVuViecDonThu> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<LinhVucChuyenNganh_LoaiVuViecDonThu> to JSON", e);
        }    }

    @Override
    public List<LinhVucChuyenNganh_LoaiVuViecDonThu> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<LinhVucChuyenNganh_LoaiVuViecDonThu>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<LinhVucChuyenNganh_LoaiVuViecDonThu>", e);
        }
    }
}
