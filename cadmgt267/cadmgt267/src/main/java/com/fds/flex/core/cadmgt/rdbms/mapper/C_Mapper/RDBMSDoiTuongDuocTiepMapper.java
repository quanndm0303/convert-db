package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.DoiTuongDuocTiep;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSDoiTuongDuocTiep;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSDoiTuongDuocTiepMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSDoiTuongDuocTiep convert(DoiTuongDuocTiep data);

    DoiTuongDuocTiep convert(RDBMSDoiTuongDuocTiep data);

    List<DoiTuongDuocTiep> convert(List<RDBMSDoiTuongDuocTiep> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget DoiTuongDuocTiep target, RDBMSDoiTuongDuocTiep source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSDoiTuongDuocTiep target, DoiTuongDuocTiep source) {
        target.setId(source.getId());
    }
}
