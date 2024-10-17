package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.LoaiDoiTuongDungTen;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSLoaiDoiTuongDungTen;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSLoaiDoiTuongDungTenMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSLoaiDoiTuongDungTen convert(LoaiDoiTuongDungTen data);

    LoaiDoiTuongDungTen convert(RDBMSLoaiDoiTuongDungTen data);

    List<LoaiDoiTuongDungTen> convert(List<RDBMSLoaiDoiTuongDungTen> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget LoaiDoiTuongDungTen target, RDBMSLoaiDoiTuongDungTen source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSLoaiDoiTuongDungTen target, LoaiDoiTuongDungTen source) {
        target.setId(source.getId());
    }
}
