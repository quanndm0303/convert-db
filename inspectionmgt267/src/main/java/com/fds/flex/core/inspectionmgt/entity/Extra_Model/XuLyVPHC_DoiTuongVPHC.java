package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.inspectionmgt.entity.S_Model.DiaChi;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class XuLyVPHC_DoiTuongVPHC {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_CA_NHAN_TO_CHUC;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("TenGoi")
    @Field(value = "TenGoi", order = 2)
    public String tenGoi = StringPool.BLANK;

    @JsonProperty("DiaChi")
    @Field(value = "DiaChi", order = 3)
    public DiaChi diaChi = new DiaChi();

    @JsonProperty("LoaiDoiTuongCNTC")
    @Field(value = "LoaiDoiTuongCNTC", order = 4)
    public LoaiDoiTuongCNTC loaiDoiTuongCNTC = new LoaiDoiTuongCNTC();

    @JsonProperty("ViThanhNien")
    @Field(value = "ViThanhNien", order = 3)
    public Boolean viThanhNien = false;

    @JsonProperty("GiayToChungNhan")
    @Field(value = "GiayToChungNhan", order = 3)
    public GiayToChungNhan giayToChungNhan = new GiayToChungNhan();

    @Getter
    @Setter
    public static class LoaiDoiTuongCNTC {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_LOAI_DOI_TUONG_CNTC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class GiayToChungNhan {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.S_GIAY_TO_CHUNG_NHAN;

        @JsonProperty("SoGiay")
        @Field(value = "SoGiay", order = 1)
        public String soGiay = StringPool.BLANK;

        @JsonProperty("NoiCap")
        @Field(value = "NoiCap", order = 2)
        public String noiCap = null;

        @JsonProperty("NgayCap")
        @Field(value = "NgayCap", order = 3)
        @JsonSerialize(using = CustomUTCDateSerializer.class)
        @JsonDeserialize(using = CustomUTCDateDeserializer.class)
        @Schema(type = "string", format = "date-time")
        public Long ngayCap = null;
    }
}
