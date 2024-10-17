package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.BienPhapKhacPhuc;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSBienPhapKhacPhuc;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSBienPhapKhacPhucMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSBienPhapKhacPhuc convert(BienPhapKhacPhuc data);

    BienPhapKhacPhuc convert(RDBMSBienPhapKhacPhuc data);

    List<BienPhapKhacPhuc> convert(List<RDBMSBienPhapKhacPhuc> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget BienPhapKhacPhuc target, RDBMSBienPhapKhacPhuc source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSBienPhapKhacPhuc target, BienPhapKhacPhuc source) {
        target.setId(source.getId());
    }
}