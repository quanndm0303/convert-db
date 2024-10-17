package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.PhanTichKetQuaKNTC;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSPhanTichKetQuaKNTC;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSPhanTichKetQuaKNTCMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSPhanTichKetQuaKNTC convert(PhanTichKetQuaKNTC data);

    PhanTichKetQuaKNTC convert(RDBMSPhanTichKetQuaKNTC data);

    List<PhanTichKetQuaKNTC> convert(List<RDBMSPhanTichKetQuaKNTC> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget PhanTichKetQuaKNTC target, RDBMSPhanTichKetQuaKNTC source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSPhanTichKetQuaKNTC target, PhanTichKetQuaKNTC source) {
        target.setId(source.getId());
    }
}
