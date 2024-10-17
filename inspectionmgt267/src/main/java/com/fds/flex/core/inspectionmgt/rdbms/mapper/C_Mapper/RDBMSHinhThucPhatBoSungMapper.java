package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucPhatBoSung;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHinhThucPhatBoSung;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSHinhThucPhatBoSungMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSHinhThucPhatBoSung convert(HinhThucPhatBoSung data);

    HinhThucPhatBoSung convert(RDBMSHinhThucPhatBoSung data);

    List<HinhThucPhatBoSung> convert(List<RDBMSHinhThucPhatBoSung> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget HinhThucPhatBoSung target, RDBMSHinhThucPhatBoSung source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSHinhThucPhatBoSung target, HinhThucPhatBoSung source) {
        target.setId(source.getId());
    }
}