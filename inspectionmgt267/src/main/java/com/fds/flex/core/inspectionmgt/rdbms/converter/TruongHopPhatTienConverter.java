package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.TruongHopPhatTien;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class TruongHopPhatTienConverter implements AttributeConverter<TruongHopPhatTien, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TruongHopPhatTien attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting TruongHopPhatTien to JSON", e);
        }
    }

    @Override
    public TruongHopPhatTien convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, TruongHopPhatTien.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to TruongHopPhatTien", e);
        }
    }

}
