package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.VuViecDonThu_LinhVucChuyenNganh;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class VuViecDonThu_LinhVucChuyenNganhConverter implements AttributeConverter<VuViecDonThu_LinhVucChuyenNganh, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(VuViecDonThu_LinhVucChuyenNganh attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting VuViecDonThu_LinhVucChuyenNganh to JSON", e);
        }
    }

    @Override
    public VuViecDonThu_LinhVucChuyenNganh convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, VuViecDonThu_LinhVucChuyenNganh.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to VuViecDonThu_LinhVucChuyenNganh", e);
        }
    }
}
