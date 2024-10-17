package com.fds.flex.core.cadmgt.rdbms.mapper.T_Mapper;

import com.fds.flex.core.cadmgt.entity.T_Model.TiepCongDan;
import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSTiepCongDan;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSTiepCongDanMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSTiepCongDan convert(TiepCongDan data);

    TiepCongDan convert(RDBMSTiepCongDan data);

    List<TiepCongDan> convert(List<RDBMSTiepCongDan> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget TiepCongDan target, RDBMSTiepCongDan source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSTiepCongDan target, TiepCongDan source) {
        target.setId(source.getId());
    }
}
