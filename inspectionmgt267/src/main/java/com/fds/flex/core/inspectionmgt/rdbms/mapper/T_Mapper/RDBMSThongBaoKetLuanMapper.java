package com.fds.flex.core.inspectionmgt.rdbms.mapper.T_Mapper;

import com.fds.flex.core.inspectionmgt.entity.T_Model.ThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSThongBaoKetLuan;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSThongBaoKetLuanMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSThongBaoKetLuan convert(ThongBaoKetLuan data);

    ThongBaoKetLuan convert(RDBMSThongBaoKetLuan data);

    List<ThongBaoKetLuan> convert(List<RDBMSThongBaoKetLuan> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget ThongBaoKetLuan target, RDBMSThongBaoKetLuan source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSThongBaoKetLuan target, ThongBaoKetLuan source) {
        target.setId(source.getId());
    }
}