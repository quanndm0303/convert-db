package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.KiemTraTrungDon;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKiemTraTrungDon;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSKiemTraTrungDonMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSKiemTraTrungDon convert(KiemTraTrungDon data);

    KiemTraTrungDon convert(RDBMSKiemTraTrungDon data);

    List<KiemTraTrungDon> convert(List<RDBMSKiemTraTrungDon> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget KiemTraTrungDon target, RDBMSKiemTraTrungDon source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSKiemTraTrungDon target, KiemTraTrungDon source) {
        target.setId(source.getId());
    }

}
