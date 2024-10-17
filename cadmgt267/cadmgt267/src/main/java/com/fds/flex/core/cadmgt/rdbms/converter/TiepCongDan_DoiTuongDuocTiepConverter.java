package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.TiepCongDan_DoiTuongDuocTiep;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class TiepCongDan_DoiTuongDuocTiepConverter implements AttributeConverter<TiepCongDan_DoiTuongDuocTiep, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TiepCongDan_DoiTuongDuocTiep attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting TiepCongDan_DoiTuongDuocTiep to JSON", e);
        }
    }

    @Override
    public TiepCongDan_DoiTuongDuocTiep convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, TiepCongDan_DoiTuongDuocTiep.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to TiepCongDan_DoiTuongDuocTiep", e);
        }
    }
}
