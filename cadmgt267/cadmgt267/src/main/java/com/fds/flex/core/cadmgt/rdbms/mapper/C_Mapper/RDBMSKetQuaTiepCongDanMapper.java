package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.KetQuaTiepCongDan;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKetQuaTiepCongDan;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSKetQuaTiepCongDanMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSKetQuaTiepCongDan convert(KetQuaTiepCongDan data);

    KetQuaTiepCongDan convert(RDBMSKetQuaTiepCongDan data);

    List<KetQuaTiepCongDan> convert(List<RDBMSKetQuaTiepCongDan> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget KetQuaTiepCongDan target, RDBMSKetQuaTiepCongDan source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSKetQuaTiepCongDan target, KetQuaTiepCongDan source) {
        target.setId(source.getId());
    }
}
