package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.ThongBaoKetLuan_CoQuanQuanLy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ThongBaoKetLuan_CoQuanQuanLyConverter implements AttributeConverter<ThongBaoKetLuan_CoQuanQuanLy, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ThongBaoKetLuan_CoQuanQuanLy attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting ThongBaoKetLuan_CoQuanQuanLy to JSON", e);
        }
    }

    @Override
    public ThongBaoKetLuan_CoQuanQuanLy convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ThongBaoKetLuan_CoQuanQuanLy.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to ThongBaoKetLuan_CoQuanQuanLy", e);
        }
    }

}
