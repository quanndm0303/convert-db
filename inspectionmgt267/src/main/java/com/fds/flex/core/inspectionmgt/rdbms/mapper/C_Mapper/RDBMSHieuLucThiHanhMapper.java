package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HieuLucThiHanh;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHieuLucThiHanh;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSHieuLucThiHanhMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSHieuLucThiHanh convert(HieuLucThiHanh data);

    HieuLucThiHanh convert(RDBMSHieuLucThiHanh data);

    List<HieuLucThiHanh> convert(List<RDBMSHieuLucThiHanh> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget HieuLucThiHanh target, RDBMSHieuLucThiHanh source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSHieuLucThiHanh target, HieuLucThiHanh source) {
        target.setId(source.getId());
    }
}