package com.fds.flex.core.cadmgt.entity.S_Model;

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

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DoanToXacMinh {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_DOAN_TO_XAC_MINH;

    @JsonProperty("TenHoatDong")
    @Field(value = "TenHoatDong", order = 1)
    public String tenHoatDong = StringPool.BLANK;

    @JsonProperty("SoQuyetDinh")
    @Field(value = "SoQuyetDinh", order = 2)
    public String soQuyetDinh = StringPool.BLANK;

    @JsonProperty("NgayQuyetDinh")
    @Field(value = "NgayQuyetDinh", order = 3)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayQuyetDinh = null;

    @JsonProperty("NoiDungHoatDong")
    @Field(value = "NoiDungHoatDong", order = 4)
    public String noiDungHoatDong = null;

    @JsonProperty("TepDuLieu")
    @Field(value = "TepDuLieu", order = 5)
    public TepDuLieu tepDuLieu = null;

    @JsonProperty("ThanhVienDoan")
    @Field(value = "ThanhVienDoan", order = 6)
    public List<ThanhVienDoan> thanhVienDoan = new ArrayList<>();
}
