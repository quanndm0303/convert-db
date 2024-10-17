package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.ThongBaoKetLuan_CoQuanBanHanh;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ThongBaoKetLuan_CoQuanBanHanhConverter implements AttributeConverter<ThongBaoKetLuan_CoQuanBanHanh, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ThongBaoKetLuan_CoQuanBanHanh attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting ThongBaoKetLuan_CoQuanBanHanh to JSON", e);
        }
    }

    @Override
    public ThongBaoKetLuan_CoQuanBanHanh convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ThongBaoKetLuan_CoQuanBanHanh.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to ThongBaoKetLuan_CoQuanBanHanh", e);
        }
    }

}
