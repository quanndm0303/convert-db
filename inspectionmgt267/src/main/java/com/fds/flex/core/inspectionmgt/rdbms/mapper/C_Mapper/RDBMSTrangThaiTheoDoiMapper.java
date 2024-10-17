package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiTheoDoi;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiTheoDoi;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSTrangThaiTheoDoiMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSTrangThaiTheoDoi convert(TrangThaiTheoDoi data);

    TrangThaiTheoDoi convert(RDBMSTrangThaiTheoDoi data);

    List<TrangThaiTheoDoi> convert(List<RDBMSTrangThaiTheoDoi> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget TrangThaiTheoDoi target, RDBMSTrangThaiTheoDoi source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSTrangThaiTheoDoi target, TrangThaiTheoDoi source) {
        target.setId(source.getId());
    }
}