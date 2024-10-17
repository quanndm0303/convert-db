package com.fds.flex.core.inspectionmgt.rdbms.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.CaNhanToChuc_LoaiDoiTuongCNTC;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class CaNhanToChuc_LoaiDoiTuongCNTCConverter implements AttributeConverter<CaNhanToChuc_LoaiDoiTuongCNTC, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CaNhanToChuc_LoaiDoiTuongCNTC attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting CaNhanToChuc_LoaiDoiTuongCNTC to JSON", e);
        }
    }

    @Override
    public CaNhanToChuc_LoaiDoiTuongCNTC convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, CaNhanToChuc_LoaiDoiTuongCNTC.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to CaNhanToChuc_LoaiDoiTuongCNTC", e);
        }
    }

}
