package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.DiaBanXuLy;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSDiaBanXuLy;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSDiaBanXuLyMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSDiaBanXuLy convert(DiaBanXuLy data);

    DiaBanXuLy convert(RDBMSDiaBanXuLy data);

    List<DiaBanXuLy> convert(List<RDBMSDiaBanXuLy> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget DiaBanXuLy target, RDBMSDiaBanXuLy source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSDiaBanXuLy target, DiaBanXuLy source) {
        target.setId(source.getId());
    }
}
