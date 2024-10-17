package com.fds.flex.core.cadmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.cadmgt.entity.C_Model.TuCachPhapLy;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTuCachPhapLy;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSTuCachPhapLyMapper {
    @Mapping(target = "id", ignore = true)
    RDBMSTuCachPhapLy convert(TuCachPhapLy data);

    TuCachPhapLy convert(RDBMSTuCachPhapLy data);

    List<TuCachPhapLy> convert(List<RDBMSTuCachPhapLy> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget TuCachPhapLy target, RDBMSTuCachPhapLy source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSTuCachPhapLy target, TuCachPhapLy source) {
        target.setId(source.getId());
    }
}
