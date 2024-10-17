package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.CheDoTiepCongDan;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSCheDoTiepCongDan;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSCheDoTiepCongDanMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSCheDoTiepCongDan convert(CheDoTiepCongDan data);

    CheDoTiepCongDan convert(RDBMSCheDoTiepCongDan data);

    List<CheDoTiepCongDan> convert(List<RDBMSCheDoTiepCongDan> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget CheDoTiepCongDan target, RDBMSCheDoTiepCongDan source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSCheDoTiepCongDan target, CheDoTiepCongDan source) {
        target.setId(source.getId());
    }

}
