package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.LoaiDoiTuongCNTC_ThamChieu;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class LoaiDoiTuongCNTC_ThamChieuConverter implements AttributeConverter<LoaiDoiTuongCNTC_ThamChieu, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(LoaiDoiTuongCNTC_ThamChieu attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting LoaiDoiTuongCNTC_ThamChieu to JSON", e);
        }
    }

    @Override
    public LoaiDoiTuongCNTC_ThamChieu convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, LoaiDoiTuongCNTC_ThamChieu.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to LoaiDoiTuongCNTC_ThamChieu", e);
        }
    }

}
