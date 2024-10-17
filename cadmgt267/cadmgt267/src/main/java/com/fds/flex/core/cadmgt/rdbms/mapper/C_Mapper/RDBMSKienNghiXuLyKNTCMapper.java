package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.KienNghiXuLyKNTC;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKienNghiXuLyKNTC;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSKienNghiXuLyKNTCMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSKienNghiXuLyKNTC convert(KienNghiXuLyKNTC data);

    KienNghiXuLyKNTC convert(RDBMSKienNghiXuLyKNTC data);

    List<KienNghiXuLyKNTC> convert(List<RDBMSKienNghiXuLyKNTC> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget KienNghiXuLyKNTC target, RDBMSKienNghiXuLyKNTC source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSKienNghiXuLyKNTC target, KienNghiXuLyKNTC source) {
        target.setId(source.getId());
    }
}
