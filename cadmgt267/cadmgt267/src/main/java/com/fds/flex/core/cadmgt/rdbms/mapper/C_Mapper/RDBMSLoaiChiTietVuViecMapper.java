package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.LoaiChiTietVuViec;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSLoaiChiTietVuViec;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSLoaiChiTietVuViecMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSLoaiChiTietVuViec convert(LoaiChiTietVuViec data);

    LoaiChiTietVuViec convert(RDBMSLoaiChiTietVuViec data);

    List<LoaiChiTietVuViec> convert(List<RDBMSLoaiChiTietVuViec> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget LoaiChiTietVuViec target, RDBMSLoaiChiTietVuViec source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSLoaiChiTietVuViec target, LoaiChiTietVuViec source) {
        target.setId(source.getId());
    }
}
