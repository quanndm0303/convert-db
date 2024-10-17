package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class CuongCheThiHanh {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_CUONG_CHE_THI_HANH;

    @JsonProperty("NgayQuyetDinh")
    @Field(value = "NgayQuyetDinh", order = 1)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayQuyetDinh = null;

    @JsonProperty("SoQuyetDinh")
    @Field(value = "SoQuyetDinh", order = 2)
    public String soQuyetDinh = StringPool.BLANK;

    @JsonProperty("NoiDungCuongChe")
    @Field(value = "NoiDungCuongChe", order = 3)
    public String noiDungCuongChe = StringPool.BLANK;

    @JsonProperty("ThoiGianThucHien")
    @Field(value = "ThoiGianThucHien", order = 4)
    public Integer thoiGianThucHien = 0;
}
