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
public class PhieuDeXuat {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_PHIEU_DE_XUAT;

    @JsonProperty("IDPhieu")
    @Field(value = "IDPhieu", order = 1)
    public String idPhieu = String.valueOf(System.currentTimeMillis());

    @JsonProperty("SoPhieu")
    @Field(value = "SoPhieu", order = 2)
    public String soPhieu = StringPool.BLANK;

    @JsonProperty("NgayDeXuat")
    @Field(value = "NgayDeXuat", order = 3)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayDeXuat = null;

    @JsonProperty("NhanXetXuLy")
    @Field(value = "NhanXetXuLy", order = 4)
    public String nhanXetXuLy = null;

    @JsonProperty("CanCuPhapLy")
    @Field(value = "CanCuPhapLy", order = 5)
    public List<CanCuPhapLy> canCuPhapLy = new ArrayList<>();

    @JsonProperty("NoiDungDeXuat")
    @Field(value = "NoiDungDeXuat", order = 6)
    public String noiDungDeXuat = null;

    @JsonProperty("PhanTichKetQuaKNTC")
    @Field(value = "PhanTichKetQuaKNTC", order = 7)
    public PhanTichKetQuaKNTC phanTichKetQuaKNTC = null;

    @JsonProperty("TheoDoiKetQuaGiaiQuyet")
    @Field(value = "TheoDoiKetQuaGiaiQuyet", order = 8)
    public String theoDoiKetQuaGiaiQuyet = null;

    @JsonProperty("TepDuLieu")
    @Field(value = "TepDuLieu", order = 9)
    public TepDuLieu tepDuLieu = null;

    @Getter
    @Setter
    public static class PhanTichKetQuaKNTC {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_PHAN_TICH_KET_QUA_KNTC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class CanCuPhapLy {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_CAN_CU_PHAP_LY;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }
}
