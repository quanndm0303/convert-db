package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiThongBaoKetLuan;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSLoaiThongBaoKetLuanMapper {


    @Mapping(target = "id", ignore = true)
    RDBMSLoaiThongBaoKetLuan convert(LoaiThongBaoKetLuan data);

    LoaiThongBaoKetLuan convert(RDBMSLoaiThongBaoKetLuan data);

    List<LoaiThongBaoKetLuan> convert(List<RDBMSLoaiThongBaoKetLuan> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget LoaiThongBaoKetLuan target, RDBMSLoaiThongBaoKetLuan source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSLoaiThongBaoKetLuan target, LoaiThongBaoKetLuan source) {
        target.setId(source.getId());
    }
}