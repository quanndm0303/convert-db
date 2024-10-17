package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.GiayToChungNhan;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class GiayToChungNhanConverter implements AttributeConverter<GiayToChungNhan, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(GiayToChungNhan attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting GiayToChungNhan to JSON", e);
        }
    }

    @Override
    public GiayToChungNhan convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, GiayToChungNhan.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to GiayToChungNhan", e);
        }
    }

}
