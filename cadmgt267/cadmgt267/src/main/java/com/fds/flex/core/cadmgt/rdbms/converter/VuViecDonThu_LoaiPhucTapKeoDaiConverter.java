package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.VuViecDonThu_LoaiPhucTapKeoDai;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class VuViecDonThu_LoaiPhucTapKeoDaiConverter implements AttributeConverter<VuViecDonThu_LoaiPhucTapKeoDai, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(VuViecDonThu_LoaiPhucTapKeoDai attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting VuViecDonThu_LoaiPhucTapKeoDai to JSON", e);
        }
    }

    @Override
    public VuViecDonThu_LoaiPhucTapKeoDai convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, VuViecDonThu_LoaiPhucTapKeoDai.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to VuViecDonThu_LoaiPhucTapKeoDai", e);
        }
    }
}
