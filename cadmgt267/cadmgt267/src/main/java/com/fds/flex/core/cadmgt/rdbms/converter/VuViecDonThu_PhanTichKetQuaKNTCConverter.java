package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.VuViecDonThu_PhanTichKetQuaKNTC;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class VuViecDonThu_PhanTichKetQuaKNTCConverter implements AttributeConverter<VuViecDonThu_PhanTichKetQuaKNTC, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(VuViecDonThu_PhanTichKetQuaKNTC attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting VuViecDonThu_PhanTichKetQuaKNTC to JSON", e);
        }
    }

    @Override
    public VuViecDonThu_PhanTichKetQuaKNTC convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, VuViecDonThu_PhanTichKetQuaKNTC.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to VuViecDonThu_PhanTichKetQuaKNTC", e);
        }
    }
}
