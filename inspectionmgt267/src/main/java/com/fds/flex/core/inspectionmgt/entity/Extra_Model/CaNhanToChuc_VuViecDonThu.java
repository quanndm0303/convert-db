package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter@JsonIgnoreProperties(ignoreUnknown = true)
public class CaNhanToChuc_VuViecDonThu {

    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_VU_VIEC_DON_THU;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("NgayTiepNhan")
    @Field(value = "NgayTiepNhan", order = 2)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayTiepNhan = null;

    @JsonInclude
    @JsonProperty("LoaiVuViecDonThu")
    @Field(value = "LoaiVuViecDonThu", order = 3)
    public LoaiVuViecDonThu loaiVuViecDonThu = new LoaiVuViecDonThu();

    @JsonInclude
    @JsonProperty("NoiDungVuViec")
    @Field(value = "NoiDungVuViec", order = 4)
    public String noiDungVuViec = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("KienNghiCongDan")
    @Field(value = "KienNghiCongDan", order = 5)
    public String kienNghiCongDan = null;

    @JsonInclude
    @JsonProperty("PhanTichKetQuaKNTC")
    @Field(value = "PhanTichKetQuaKNTC", order = 6)
    public PhanTichKetQuaKNTC phanTichKetQuaKNTC = null;

    @JsonInclude
    @JsonProperty("KienNghiXuLyKNTC")
    @Field(value = "KienNghiXuLyKNTC", order = 7)
    public KienNghiXuLyKNTC kienNghiXuLyKNTC = null;

    @Transient
    @JsonProperty("DoiTuongVuViec")
    public List<DoiTuongVuViec> doiTuongVuViec = new ArrayList<>();

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DoiTuongVuViec {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.T_CA_NHAN_TO_CHUC;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh", order = 1)
        public String maDinhDanh = null;

        @JsonProperty("TenGoi")
        @Field(value = "TenGoi", order = 2)
        public String tenGoi = StringPool.BLANK;
    }

    @Getter
    @Setter@JsonIgnoreProperties(ignoreUnknown = true)
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
    @Setter@JsonIgnoreProperties(ignoreUnknown = true)
    public static class KienNghiXuLyKNTC {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_KIEN_NGHI_XU_LY_KNTC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter@JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoaiVuViecDonThu {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_LOAI_VU_VIEC_DON_THU;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }
}

