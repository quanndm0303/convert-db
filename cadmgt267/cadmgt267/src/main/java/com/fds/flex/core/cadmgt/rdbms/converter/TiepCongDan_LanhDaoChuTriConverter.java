package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.TiepCongDan_LanhDaoChuTri;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class TiepCongDan_LanhDaoChuTriConverter implements AttributeConverter<TiepCongDan_LanhDaoChuTri, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TiepCongDan_LanhDaoChuTri attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting TiepCongDan_LanhDaoChuTri to JSON", e);
        }
    }

    @Override
    public TiepCongDan_LanhDaoChuTri convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, TiepCongDan_LanhDaoChuTri.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to TiepCongDan_LanhDaoChuTri", e);
        }
    }
}
