package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.CaNhanToChuc_VuViecDonThu;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class CaNhanToChuc_VuViecDonThuConverter implements AttributeConverter<List<CaNhanToChuc_VuViecDonThu>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<CaNhanToChuc_VuViecDonThu> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<CaNhanToChuc_VuViecDonThu> to JSON", e);
        }
    }

    @Override
    public List<CaNhanToChuc_VuViecDonThu> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<CaNhanToChuc_VuViecDonThu>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<CaNhanToChuc_VuViecDonThu>", e);
        }
    }

}
