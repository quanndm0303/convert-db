package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.TinhTrangXuLyVuViec;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTinhTrangXuLyVuViec;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSTinhTrangXuLyVuViecMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSTinhTrangXuLyVuViec convert(TinhTrangXuLyVuViec data);

    TinhTrangXuLyVuViec convert(RDBMSTinhTrangXuLyVuViec data);

    List<TinhTrangXuLyVuViec> convert(List<RDBMSTinhTrangXuLyVuViec> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget TinhTrangXuLyVuViec target, RDBMSTinhTrangXuLyVuViec source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSTinhTrangXuLyVuViec target, TinhTrangXuLyVuViec source) {
        target.setId(source.getId());
    }
}
