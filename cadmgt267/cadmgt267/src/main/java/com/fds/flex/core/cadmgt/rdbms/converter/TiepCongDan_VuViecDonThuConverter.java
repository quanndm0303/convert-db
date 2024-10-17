package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.TiepCongDan_VuViecDonThu;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class TiepCongDan_VuViecDonThuConverter implements AttributeConverter<TiepCongDan_VuViecDonThu, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TiepCongDan_VuViecDonThu attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting TiepCongDan_VuViecDonThu to JSON", e);
        }
    }

    @Override
    public TiepCongDan_VuViecDonThu convertToEntityAttribute(String dbData) {
            try {
                return objectMapper.readValue(dbData, TiepCongDan_VuViecDonThu.class);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error converting JSON to TiepCongDan_VuViecDonThu", e);
            }
    }
}
