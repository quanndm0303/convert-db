package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_CanBoGiaiQuyet;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Converter(autoApply = true)
public class XuLyDonThu_CanBoGiaiQuyetConverter implements AttributeConverter<List<XuLyDonThu_CanBoGiaiQuyet>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(List<XuLyDonThu_CanBoGiaiQuyet> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting List<XuLyDonThu_CanBoGiaiQuyet> to JSON", e);
        }
    }

    @Override
    public List<XuLyDonThu_CanBoGiaiQuyet> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<XuLyDonThu_CanBoGiaiQuyet>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to List<XuLyDonThu_CanBoGiaiQuyet>", e);
        }
    }
}
