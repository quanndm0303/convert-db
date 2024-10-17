package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThanhPhanGiayTo {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_THANH_PHAN_GIAY_TO;

    @JsonProperty("IDGiayTo")
    @Field(value = "IDGiayTo", order = 1)
    public String idGiayTo = String.valueOf(System.currentTimeMillis());

    @JsonProperty("TenGiayTo")
    @Field(value = "TenGiayTo", order = 2)
    public String tenGiayTo = StringPool.BLANK;

    @JsonProperty("LoaiGiayToNghiepVu")
    @Field(value = "LoaiGiayToNghiepVu", order = 4)
    public LoaiGiayToNghiepVu loaiGiayToVPHC = null;

    @JsonProperty("SoHieuVanBan")
    @Field(value = "SoHieuVanBan", order = 5)
    public String soHieuVanBan = null;

    @JsonProperty("NgayBanHanh")
    @Field(value = "NgayBanHanh", order = 6)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayBanHanh = null;

    @JsonProperty("CoQuanBanHanh")
    @Field(value = "CoQuanBanHanh", order = 7)
    public CoQuanBanHanh coQuanBanHanh = null;

    @JsonProperty("GhiChuVanBan")
    @Field(value = "GhiChuVanBan", order = 8)
    public String ghiChuVanBan = null;

    @JsonProperty("TepDuLieu")
    @Field(value = "TepDuLieu", order = 9)
    public TepDuLieu tepDuLieu = null;

    @JsonProperty("GiayToLuuTruSo")
    @Field(value = "GiayToLuuTruSo", order = 10)
    public GiayToLuuTruSo giayToLuuTruSo = null;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CoQuanBanHanh {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.T_CO_QUAN_DON_VI;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh", order = 1)
        public String maDinhDanh = StringPool.BLANK;

        @JsonProperty("TenGoi")
        @Field(value = "TenGoi", order = 2)
        public String tenGoi = StringPool.BLANK;
    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoaiGiayToNghiepVu {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_LOAI_GIAY_TO_VPHC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GiayToLuuTruSo {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.T_GIAY_TO_LUU_TRU_SO;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh", order = 1)
        public String maDinhDanh = StringPool.BLANK;

        @JsonProperty("TenGiayTo")
        @Field(value = "TenGiayTo", order = 2)
        public String tenGiayTo = StringPool.BLANK;
    }
}
