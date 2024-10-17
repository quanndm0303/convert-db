package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.CanCuPhapLy;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSCanCuPhapLy;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSCanCuPhapLyMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSCanCuPhapLy convert(CanCuPhapLy data);

    CanCuPhapLy convert(RDBMSCanCuPhapLy data);

    List<CanCuPhapLy> convert(List<RDBMSCanCuPhapLy> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget CanCuPhapLy target, RDBMSCanCuPhapLy source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSCanCuPhapLy target, CanCuPhapLy source) {
        target.setId(source.getId());
    }
}
