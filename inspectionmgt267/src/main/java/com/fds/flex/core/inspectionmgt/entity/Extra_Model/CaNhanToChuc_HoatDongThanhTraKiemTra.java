package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class CaNhanToChuc_HoatDongThanhTraKiemTra {

    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_HOAT_DONG_THANH_TRA;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("TenHoatDong")
    @Field(value = "TenHoatDong", order = 2)
    public String tenHoatDong = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("LoaiHoatDongThanhTra")
    @Field(value = "LoaiHoatDongThanhTra", order = 3)
    public LoaiHoatDongThanhTra loaiHoatDongThanhTra = new LoaiHoatDongThanhTra();

    @JsonInclude
    @JsonProperty("SoQuyetDinh")
    @Field(value = "SoQuyetDinh", order = 4)
    public String soQuyetDinh = null;

    @JsonInclude
    @JsonProperty("NgayQuyetDinh")
    @Field(value = "NgayQuyetDinh", order = 5)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayQuyetDinh = null;

    @JsonInclude
    @JsonProperty("CoQuanBanHanh")
    @Field(value = "CoQuanBanHanh", order = 6)
    public CoQuanBanHanh coQuanBanHanh = null;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoaiHoatDongThanhTra {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_LOAI_HOAT_DONG_THANH_TRA;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

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
}

