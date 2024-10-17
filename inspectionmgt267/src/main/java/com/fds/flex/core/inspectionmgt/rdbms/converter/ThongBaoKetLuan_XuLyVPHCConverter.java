package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.ThongBaoKetLuan_XuLyVPHC;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class ThongBaoKetLuan_XuLyVPHCConverter implements AttributeConverter<List<ThongBaoKetLuan_XuLyVPHC>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ThongBaoKetLuan_XuLyVPHC> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<ThongBaoKetLuan_XuLyVPHC> to JSON", e);
        }
    }

    @Override
    public List<ThongBaoKetLuan_XuLyVPHC> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<ThongBaoKetLuan_XuLyVPHC>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<ThongBaoKetLuan_XuLyVPHC>", e);
        }
    }

}
