package com.fds.flex.core.cadmgt.rdbms.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.core.cadmgt.entity.S_Model.PhieuDeXuatXuLyVuViec;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class PhieuDeXuatXuLyVuViecConverter implements AttributeConverter<PhieuDeXuatXuLyVuViec, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(PhieuDeXuatXuLyVuViec attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting PhieuDeXuatXuLyVuViec to JSON", e);
        }    }

    @Override
    public PhieuDeXuatXuLyVuViec convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, PhieuDeXuatXuLyVuViec.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to PhieuDeXuatXuLyVuViec", e);
        }
    }
}
