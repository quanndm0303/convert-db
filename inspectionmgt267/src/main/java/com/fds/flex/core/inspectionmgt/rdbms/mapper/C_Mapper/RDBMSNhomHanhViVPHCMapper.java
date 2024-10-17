package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.NhomHanhViVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSNhomHanhViVPHC;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSNhomHanhViVPHCMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSNhomHanhViVPHC convert(NhomHanhViVPHC data);

    NhomHanhViVPHC convert(RDBMSNhomHanhViVPHC data);

    List<NhomHanhViVPHC> convert(List<RDBMSNhomHanhViVPHC> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget NhomHanhViVPHC target, RDBMSNhomHanhViVPHC source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSNhomHanhViVPHC target, NhomHanhViVPHC source) {
        target.setId(source.getId());
    }
}