package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.HinhThucNhanDonThu;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSHinhThucNhanDonThu;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSHinhThucNhanDonThuMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSHinhThucNhanDonThu convert(HinhThucNhanDonThu data);

    HinhThucNhanDonThu convert(RDBMSHinhThucNhanDonThu data);

    List<HinhThucNhanDonThu> convert(List<RDBMSHinhThucNhanDonThu> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget HinhThucNhanDonThu target, RDBMSHinhThucNhanDonThu source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSHinhThucNhanDonThu target, HinhThucNhanDonThu source) {
        target.setId(source.getId());
    }
}
