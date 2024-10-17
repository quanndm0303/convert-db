package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.ChucDanhDoan;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSChucDanhDoan;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSChucDanhDoanMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSChucDanhDoan convert(ChucDanhDoan data);

    ChucDanhDoan convert(RDBMSChucDanhDoan data);

    List<ChucDanhDoan> convert(List<RDBMSChucDanhDoan> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget ChucDanhDoan target, RDBMSChucDanhDoan source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSChucDanhDoan target, ChucDanhDoan source) {
        target.setId(source.getId());
    }
}