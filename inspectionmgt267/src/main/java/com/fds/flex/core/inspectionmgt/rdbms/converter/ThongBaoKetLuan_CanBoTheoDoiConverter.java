package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.ThongBaoKetLuan_CanBoTheoDoi;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ThongBaoKetLuan_CanBoTheoDoiConverter implements AttributeConverter<ThongBaoKetLuan_CanBoTheoDoi, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ThongBaoKetLuan_CanBoTheoDoi attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting ThongBaoKetLuan_CanBoTheoDoi to JSON", e);
        }
    }

    @Override
    public ThongBaoKetLuan_CanBoTheoDoi convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ThongBaoKetLuan_CanBoTheoDoi.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to ThongBaoKetLuan_CanBoTheoDoi", e);
        }
    }

}
