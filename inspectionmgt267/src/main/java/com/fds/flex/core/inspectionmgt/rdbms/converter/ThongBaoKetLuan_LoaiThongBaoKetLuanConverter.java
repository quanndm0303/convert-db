package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.ThongBaoKetLuan_LoaiThongBaoKetLuan;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ThongBaoKetLuan_LoaiThongBaoKetLuanConverter implements AttributeConverter<ThongBaoKetLuan_LoaiThongBaoKetLuan, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ThongBaoKetLuan_LoaiThongBaoKetLuan attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting ThongBaoKetLuan_LoaiThongBaoKetLuan to JSON", e);
        }
    }

    @Override
    public ThongBaoKetLuan_LoaiThongBaoKetLuan convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ThongBaoKetLuan_LoaiThongBaoKetLuan.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to ThongBaoKetLuan_LoaiThongBaoKetLuan", e);
        }
    }

}
