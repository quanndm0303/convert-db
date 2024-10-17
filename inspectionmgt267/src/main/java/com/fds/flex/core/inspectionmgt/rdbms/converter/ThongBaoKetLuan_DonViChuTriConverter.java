package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.ThongBaoKetLuan_DonViChuTri;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ThongBaoKetLuan_DonViChuTriConverter implements AttributeConverter<ThongBaoKetLuan_DonViChuTri, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ThongBaoKetLuan_DonViChuTri attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting ThongBaoKetLuan_DonViChuTri to JSON", e);
        }
    }

    @Override
    public ThongBaoKetLuan_DonViChuTri convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ThongBaoKetLuan_DonViChuTri.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to ThongBaoKetLuan_DonViChuTri", e);
        }
    }

}
