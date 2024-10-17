package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.S_Model.DeXuatKienNghi;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class DeXuatKienNghiConverter implements AttributeConverter<List<DeXuatKienNghi>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<DeXuatKienNghi> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<DeXuatKienNghi> to JSON", e);
        }
    }

    @Override
    public List<DeXuatKienNghi> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<DeXuatKienNghi>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<DeXuatKienNghi>", e);
        }
    }

}
