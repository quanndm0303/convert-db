package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.LoaiVuViecDonThu;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSLoaiVuViecDonThu;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSLoaiVuViecDonThuMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSLoaiVuViecDonThu convert(LoaiVuViecDonThu data);

    LoaiVuViecDonThu convert(RDBMSLoaiVuViecDonThu data);

    List<LoaiVuViecDonThu> convert(List<RDBMSLoaiVuViecDonThu> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget LoaiVuViecDonThu target, RDBMSLoaiVuViecDonThu source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSLoaiVuViecDonThu target, LoaiVuViecDonThu source) {
        target.setId(source.getId());
    }
}
