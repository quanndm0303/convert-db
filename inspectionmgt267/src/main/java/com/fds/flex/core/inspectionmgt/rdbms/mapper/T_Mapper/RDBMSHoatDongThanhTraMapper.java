package com.fds.flex.core.inspectionmgt.rdbms.mapper.T_Mapper;

import com.fds.flex.core.inspectionmgt.entity.T_Model.HoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSHoatDongThanhTra;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSHoatDongThanhTraMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSHoatDongThanhTra convert(HoatDongThanhTra data);

    HoatDongThanhTra convert(RDBMSHoatDongThanhTra data);

    List<HoatDongThanhTra> convert(List<RDBMSHoatDongThanhTra> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget HoatDongThanhTra target, RDBMSHoatDongThanhTra source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSHoatDongThanhTra target, HoatDongThanhTra source) {
        target.setId(source.getId());
    }
}