package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.NghiDinhXPVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSNghiDinhXPVPHC;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSNghiDinhXPVPHCMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSNghiDinhXPVPHC convert(NghiDinhXPVPHC data);

    NghiDinhXPVPHC convert(RDBMSNghiDinhXPVPHC data);

    List<NghiDinhXPVPHC> convert(List<RDBMSNghiDinhXPVPHC> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget NghiDinhXPVPHC target, RDBMSNghiDinhXPVPHC source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSNghiDinhXPVPHC target, NghiDinhXPVPHC source) {
        target.setId(source.getId());
    }
}