package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiHoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiHoatDongThanhTra;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSTrangThaiHoatDongThanhTraMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSTrangThaiHoatDongThanhTra convert(TrangThaiHoatDongThanhTra data);

    TrangThaiHoatDongThanhTra convert(RDBMSTrangThaiHoatDongThanhTra data);

    List<TrangThaiHoatDongThanhTra> convert(List<RDBMSTrangThaiHoatDongThanhTra> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget TrangThaiHoatDongThanhTra target, RDBMSTrangThaiHoatDongThanhTra source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSTrangThaiHoatDongThanhTra target, TrangThaiHoatDongThanhTra source) {
        target.setId(source.getId());
    }
}