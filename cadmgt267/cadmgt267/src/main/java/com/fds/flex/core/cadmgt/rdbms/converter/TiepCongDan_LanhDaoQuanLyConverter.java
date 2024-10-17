package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.TiepCongDan_LanhDaoQuanLy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class TiepCongDan_LanhDaoQuanLyConverter implements AttributeConverter<TiepCongDan_LanhDaoQuanLy, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TiepCongDan_LanhDaoQuanLy attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting TiepCongDan_LanhDaoQuanLy to JSON", e);
        }
    }

    @Override
    public TiepCongDan_LanhDaoQuanLy convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, TiepCongDan_LanhDaoQuanLy.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to TiepCongDan_LanhDaoQuanLy", e);
        }
    }
}
