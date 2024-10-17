package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiHoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiHoatDongThanhTra;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSLoaiHoatDongThanhTraMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSLoaiHoatDongThanhTra convert(LoaiHoatDongThanhTra data);

    LoaiHoatDongThanhTra convert(RDBMSLoaiHoatDongThanhTra data);

    List<LoaiHoatDongThanhTra> convert(List<RDBMSLoaiHoatDongThanhTra> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget LoaiHoatDongThanhTra target, RDBMSLoaiHoatDongThanhTra source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSLoaiHoatDongThanhTra target, LoaiHoatDongThanhTra source) {
        target.setId(source.getId());
    }
}