package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.XuLyVPHC_CanBoTheoDoi;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class XuLyVPHC_CanBoTheoDoiConverter implements AttributeConverter<List<XuLyVPHC_CanBoTheoDoi>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<XuLyVPHC_CanBoTheoDoi> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<XuLyVPHC_CanBoTheoDoi> to JSON", e);
        }
    }

    @Override
    public List<XuLyVPHC_CanBoTheoDoi> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<XuLyVPHC_CanBoTheoDoi>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<XuLyVPHC_CanBoTheoDoi>", e);
        }
    }

}
