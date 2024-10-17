package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.ThongBaoKetLuan_LoaiGiayKetLuan;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ThongBaoKetLuan_LoaiGiayKetLuanConverter implements AttributeConverter<ThongBaoKetLuan_LoaiGiayKetLuan, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ThongBaoKetLuan_LoaiGiayKetLuan attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting ThongBaoKetLuan_LoaiGiayKetLuan to JSON", e);
        }
    }

    @Override
    public ThongBaoKetLuan_LoaiGiayKetLuan convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ThongBaoKetLuan_LoaiGiayKetLuan.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to ThongBaoKetLuan_LoaiGiayKetLuan", e);
        }
    }

}
