package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.TinhTrangXuLyDonThu;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTinhTrangXuLyDonThu;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSTinhTrangXuLyDonThuMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSTinhTrangXuLyDonThu convert(TinhTrangXuLyDonThu data);

    TinhTrangXuLyDonThu convert(RDBMSTinhTrangXuLyDonThu data);

    List<TinhTrangXuLyDonThu> convert(List<RDBMSTinhTrangXuLyDonThu> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget TinhTrangXuLyDonThu target, RDBMSTinhTrangXuLyDonThu source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSTinhTrangXuLyDonThu target, TinhTrangXuLyDonThu source) {
        target.setId(source.getId());
    }
}
