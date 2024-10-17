package com.fds.flex.core.cadmgt.rdbms.mapper.T_Mapper;


import com.fds.flex.core.cadmgt.entity.T_Model.VuViecDonThu;
import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSVuViecDonThu;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSVuViecDonThuMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSVuViecDonThu convert(VuViecDonThu data);

    VuViecDonThu convert(RDBMSVuViecDonThu data);

    List<VuViecDonThu> convert(List<RDBMSVuViecDonThu> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget VuViecDonThu target, RDBMSVuViecDonThu source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSVuViecDonThu target, VuViecDonThu source) {
        target.setId(source.getId());
    }
}
