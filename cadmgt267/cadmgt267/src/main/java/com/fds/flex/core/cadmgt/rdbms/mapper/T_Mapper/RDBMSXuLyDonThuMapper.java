package com.fds.flex.core.cadmgt.rdbms.mapper.T_Mapper;

import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSXuLyDonThu;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSXuLyDonThuMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSXuLyDonThu convert(XuLyDonThu data);

    XuLyDonThu convert(RDBMSXuLyDonThu data);

    List<XuLyDonThu> convert(List<RDBMSXuLyDonThu> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget XuLyDonThu target, RDBMSXuLyDonThu source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSXuLyDonThu target, XuLyDonThu source) {
        target.setId(source.getId());
    }
}
