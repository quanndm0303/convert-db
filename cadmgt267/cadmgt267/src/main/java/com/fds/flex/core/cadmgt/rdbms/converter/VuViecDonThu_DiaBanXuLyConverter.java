package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.VuViecDonThu_DiaBanXuLy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class VuViecDonThu_DiaBanXuLyConverter implements AttributeConverter<VuViecDonThu_DiaBanXuLy, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(VuViecDonThu_DiaBanXuLy attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting VuViecDonThu_DiaBanXuLy to JSON", e);
        }
    }

    @Override
    public VuViecDonThu_DiaBanXuLy convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, VuViecDonThu_DiaBanXuLy.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to VuViecDonThu_DiaBanXuLy", e);
        }
    }
}
