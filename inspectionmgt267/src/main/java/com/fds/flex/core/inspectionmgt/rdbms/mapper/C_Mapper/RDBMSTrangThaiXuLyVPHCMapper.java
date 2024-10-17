package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiXuLyVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiXuLyVPHC;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSTrangThaiXuLyVPHCMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSTrangThaiXuLyVPHC convert(TrangThaiXuLyVPHC data);

    TrangThaiXuLyVPHC convert(RDBMSTrangThaiXuLyVPHC data);

    List<TrangThaiXuLyVPHC> convert(List<RDBMSTrangThaiXuLyVPHC> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget TrangThaiXuLyVPHC target, RDBMSTrangThaiXuLyVPHC source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSTrangThaiXuLyVPHC target, TrangThaiXuLyVPHC source) {
        target.setId(source.getId());
    }
}