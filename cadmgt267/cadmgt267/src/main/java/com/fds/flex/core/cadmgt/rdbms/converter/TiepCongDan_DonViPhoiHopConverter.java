package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.TiepCongDan_DonViPhoiHop;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class TiepCongDan_DonViPhoiHopConverter implements AttributeConverter<TiepCongDan_DonViPhoiHop, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TiepCongDan_DonViPhoiHop attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting TiepCongDan_DonViPhoiHop to JSON", e);
        }
    }

    @Override
    public TiepCongDan_DonViPhoiHop convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, TiepCongDan_DonViPhoiHop.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to TiepCongDan_DonViPhoiHop", e);
        }
    }
}
