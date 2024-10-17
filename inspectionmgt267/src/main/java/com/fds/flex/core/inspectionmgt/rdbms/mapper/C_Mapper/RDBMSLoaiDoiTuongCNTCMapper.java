package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiDoiTuongCNTC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiDoiTuongCNTC;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSLoaiDoiTuongCNTCMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSLoaiDoiTuongCNTC convert(LoaiDoiTuongCNTC data);

    LoaiDoiTuongCNTC convert(RDBMSLoaiDoiTuongCNTC data);

    List<LoaiDoiTuongCNTC> convert(List<RDBMSLoaiDoiTuongCNTC> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget LoaiDoiTuongCNTC target, RDBMSLoaiDoiTuongCNTC source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSLoaiDoiTuongCNTC target, LoaiDoiTuongCNTC source) {
        target.setId(source.getId());
    }
}