package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.TiepCongDan_NguoiDuocTiep;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class TiepCongDan_NguoiDuocTiepConverter implements AttributeConverter<List<TiepCongDan_NguoiDuocTiep>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<TiepCongDan_NguoiDuocTiep> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<TiepCongDan_NguoiDuocTiep> to JSON", e);
        }
    }

    @Override
    public List<TiepCongDan_NguoiDuocTiep> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<TiepCongDan_NguoiDuocTiep>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<TiepCongDan_NguoiDuocTiep>", e);
        }
    }
}
