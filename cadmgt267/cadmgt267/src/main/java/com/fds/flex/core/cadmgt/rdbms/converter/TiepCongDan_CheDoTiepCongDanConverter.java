package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.TiepCongDan_CheDoTiepCongDan;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class TiepCongDan_CheDoTiepCongDanConverter implements AttributeConverter<TiepCongDan_CheDoTiepCongDan, String>{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TiepCongDan_CheDoTiepCongDan attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting TiepCongDan_CheDoTiepCongDan to JSON", e);
        }
    }

    @Override
    public TiepCongDan_CheDoTiepCongDan convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, TiepCongDan_CheDoTiepCongDan.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to TiepCongDan_CheDoTiepCongDan", e);
        }
    }
}
