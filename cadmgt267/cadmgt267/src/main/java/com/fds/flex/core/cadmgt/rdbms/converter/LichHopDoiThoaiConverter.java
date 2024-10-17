package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.S_Model.LichHopDoiThoai;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class LichHopDoiThoaiConverter implements AttributeConverter<List<LichHopDoiThoai>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<LichHopDoiThoai> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<LichHopDoiThoai> to JSON", e);
        }
    }

    @Override
    public List<LichHopDoiThoai> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<LichHopDoiThoai>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<LichHopDoiThoai>", e);
        }
    }
}
