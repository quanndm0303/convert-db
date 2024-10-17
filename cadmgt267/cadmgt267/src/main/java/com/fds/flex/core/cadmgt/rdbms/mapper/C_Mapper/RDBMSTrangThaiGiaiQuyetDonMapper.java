package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.TrangThaiGiaiQuyetDon;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTrangThaiGiaiQuyetDon;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSTrangThaiGiaiQuyetDonMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSTrangThaiGiaiQuyetDon convert(TrangThaiGiaiQuyetDon data);

    TrangThaiGiaiQuyetDon convert(RDBMSTrangThaiGiaiQuyetDon data);

    List<TrangThaiGiaiQuyetDon> convert(List<RDBMSTrangThaiGiaiQuyetDon> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget TrangThaiGiaiQuyetDon target, RDBMSTrangThaiGiaiQuyetDon source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSTrangThaiGiaiQuyetDon target, TrangThaiGiaiQuyetDon source) {
        target.setId(source.getId());
    }
}
