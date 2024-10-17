package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LinhVucChuyenNganh;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLinhVucChuyenNganh;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSLinhVucChuyenNganhMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSLinhVucChuyenNganh convert(LinhVucChuyenNganh data);

    LinhVucChuyenNganh convert(RDBMSLinhVucChuyenNganh data);

    List<LinhVucChuyenNganh> convert(List<RDBMSLinhVucChuyenNganh> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget LinhVucChuyenNganh target, RDBMSLinhVucChuyenNganh source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSLinhVucChuyenNganh target, LinhVucChuyenNganh source) {
        target.setId(source.getId());
    }
}