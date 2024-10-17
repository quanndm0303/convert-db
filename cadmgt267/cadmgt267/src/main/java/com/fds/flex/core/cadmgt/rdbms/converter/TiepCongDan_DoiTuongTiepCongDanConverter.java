package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.TiepCongDan_DoiTuongTiepCongDan;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class TiepCongDan_DoiTuongTiepCongDanConverter implements AttributeConverter<TiepCongDan_DoiTuongTiepCongDan, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TiepCongDan_DoiTuongTiepCongDan attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting TiepCongDan_DoiTuongTiepCongDan to JSON", e);
        }
    }

    @Override
    public TiepCongDan_DoiTuongTiepCongDan convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, TiepCongDan_DoiTuongTiepCongDan.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to TiepCongDan_DoiTuongTiepCongDan", e);
        }
    }
}
