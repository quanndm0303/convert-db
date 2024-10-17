package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.KhoiKienHanhChinh;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKhoiKienHanhChinh;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSKhoiKienHanhChinhMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSKhoiKienHanhChinh convert(KhoiKienHanhChinh data);

    KhoiKienHanhChinh convert(RDBMSKhoiKienHanhChinh data);

    List<KhoiKienHanhChinh> convert(List<RDBMSKhoiKienHanhChinh> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget KhoiKienHanhChinh target, RDBMSKhoiKienHanhChinh source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSKhoiKienHanhChinh target, KhoiKienHanhChinh source) {
        target.setId(source.getId());
    }
}
