package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiDeXuatKienNghi;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiDeXuatKienNghi;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSLoaiDeXuatKienNghiMapper {


    @Mapping(target = "id", ignore = true)
    RDBMSLoaiDeXuatKienNghi convert(LoaiDeXuatKienNghi data);

    LoaiDeXuatKienNghi convert(RDBMSLoaiDeXuatKienNghi data);

    List<LoaiDeXuatKienNghi> convert(List<RDBMSLoaiDeXuatKienNghi> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget LoaiDeXuatKienNghi target, RDBMSLoaiDeXuatKienNghi source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSLoaiDeXuatKienNghi target, LoaiDeXuatKienNghi source) {
        target.setId(source.getId());
    }
}