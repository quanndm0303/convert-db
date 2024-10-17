package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.DoiTuongTiepCongDan;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSDoiTuongTiepCongDan;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSDoiTuongTiepCongDanMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSDoiTuongTiepCongDan convert(DoiTuongTiepCongDan data);

    DoiTuongTiepCongDan convert(RDBMSDoiTuongTiepCongDan data);

    List<DoiTuongTiepCongDan> convert(List<RDBMSDoiTuongTiepCongDan> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget DoiTuongTiepCongDan target, RDBMSDoiTuongTiepCongDan source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSDoiTuongTiepCongDan target, DoiTuongTiepCongDan source) {
        target.setId(source.getId());
    }
}
