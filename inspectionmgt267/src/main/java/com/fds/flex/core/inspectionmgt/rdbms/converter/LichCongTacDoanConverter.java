package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.LichCongTacDoan;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class LichCongTacDoanConverter implements AttributeConverter<List<LichCongTacDoan>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<LichCongTacDoan> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<LichCongTacDoan> to JSON", e);
        }
    }

    @Override
    public List<LichCongTacDoan> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<LichCongTacDoan>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<LichCongTacDoan>", e);
        }
    }

}
