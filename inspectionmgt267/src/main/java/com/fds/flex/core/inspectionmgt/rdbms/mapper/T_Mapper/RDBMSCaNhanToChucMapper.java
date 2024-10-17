package com.fds.flex.core.inspectionmgt.rdbms.mapper.T_Mapper;

import com.fds.flex.core.inspectionmgt.entity.T_Model.CaNhanToChuc;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSCaNhanToChuc;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSCaNhanToChucMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSCaNhanToChuc convert(CaNhanToChuc data);

    CaNhanToChuc convert(RDBMSCaNhanToChuc data);

    List<CaNhanToChuc> convert(List<RDBMSCaNhanToChuc> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget CaNhanToChuc target, RDBMSCaNhanToChuc source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSCaNhanToChuc target, CaNhanToChuc source) {
        target.setId(source.getId());
    }
}