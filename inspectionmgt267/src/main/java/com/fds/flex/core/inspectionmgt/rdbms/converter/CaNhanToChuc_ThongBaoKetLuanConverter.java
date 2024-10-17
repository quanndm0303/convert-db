package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.CaNhanToChuc_ThongBaoKetLuan;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class CaNhanToChuc_ThongBaoKetLuanConverter implements AttributeConverter<List<CaNhanToChuc_ThongBaoKetLuan>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<CaNhanToChuc_ThongBaoKetLuan> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<CaNhanToChuc_ThongBaoKetLuan> to JSON", e);
        }
    }

    @Override
    public List<CaNhanToChuc_ThongBaoKetLuan> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<CaNhanToChuc_ThongBaoKetLuan>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<CaNhanToChuc_ThongBaoKetLuan>", e);
        }
    }

}
