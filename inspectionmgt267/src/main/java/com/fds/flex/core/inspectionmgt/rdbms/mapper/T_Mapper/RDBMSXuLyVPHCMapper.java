package com.fds.flex.core.inspectionmgt.rdbms.mapper.T_Mapper;

import com.fds.flex.core.inspectionmgt.entity.T_Model.XuLyVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSXuLyVPHC;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSXuLyVPHCMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSXuLyVPHC convert(XuLyVPHC data);

    XuLyVPHC convert(RDBMSXuLyVPHC data);

    List<XuLyVPHC> convert(List<RDBMSXuLyVPHC> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget XuLyVPHC target, RDBMSXuLyVPHC source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSXuLyVPHC target, XuLyVPHC source) {
        target.setId(source.getId());
    }
}