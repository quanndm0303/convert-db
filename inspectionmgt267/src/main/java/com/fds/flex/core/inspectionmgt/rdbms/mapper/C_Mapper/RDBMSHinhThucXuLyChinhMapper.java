package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucXuLyChinh;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHinhThucXuLyChinh;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSHinhThucXuLyChinhMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSHinhThucXuLyChinh convert(HinhThucXuLyChinh data);

    HinhThucXuLyChinh convert(RDBMSHinhThucXuLyChinh data);

    List<HinhThucXuLyChinh> convert(List<RDBMSHinhThucXuLyChinh> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget HinhThucXuLyChinh target, RDBMSHinhThucXuLyChinh source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSHinhThucXuLyChinh target, HinhThucXuLyChinh source) {
        target.setId(source.getId());
    }
}