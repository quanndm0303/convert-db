package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HanhViVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHanhViVPHC;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSHanhViVPHCMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSHanhViVPHC convert(HanhViVPHC data);

    HanhViVPHC convert(RDBMSHanhViVPHC data);

    List<HanhViVPHC> convert(List<RDBMSHanhViVPHC> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget HanhViVPHC target, RDBMSHanhViVPHC source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSHanhViVPHC target, HanhViVPHC source) {
        target.setId(source.getId());
    }
}