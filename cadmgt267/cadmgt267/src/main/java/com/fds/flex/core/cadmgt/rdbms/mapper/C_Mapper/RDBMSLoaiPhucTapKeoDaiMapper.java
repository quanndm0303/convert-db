package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.LoaiPhucTapKeoDai;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSLoaiPhucTapKeoDai;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSLoaiPhucTapKeoDaiMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSLoaiPhucTapKeoDai convert(LoaiPhucTapKeoDai data);

    LoaiPhucTapKeoDai convert(RDBMSLoaiPhucTapKeoDai data);

    List<LoaiPhucTapKeoDai> convert(List<RDBMSLoaiPhucTapKeoDai> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget LoaiPhucTapKeoDai target, RDBMSLoaiPhucTapKeoDai source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSLoaiPhucTapKeoDai target, LoaiPhucTapKeoDai source) {
        target.setId(source.getId());
    }
}
