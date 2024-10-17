package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiCheDoThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiCheDoThanhTra;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RDBMSLoaiCheDoThanhTraMapper {

    @Mapping(target = "id", ignore = true)
    RDBMSLoaiCheDoThanhTra convert(LoaiCheDoThanhTra data);

    LoaiCheDoThanhTra convert(RDBMSLoaiCheDoThanhTra data);

    List<LoaiCheDoThanhTra> convert(List<RDBMSLoaiCheDoThanhTra> data);

    @AfterMapping
    default void handleTransientField(@MappingTarget LoaiCheDoThanhTra target, RDBMSLoaiCheDoThanhTra source) {
        target.setId(source.getId());
    }

    @AfterMapping
    default void handleTransientField(@MappingTarget RDBMSLoaiCheDoThanhTra target, LoaiCheDoThanhTra source) {
        target.setId(source.getId());
    }
}