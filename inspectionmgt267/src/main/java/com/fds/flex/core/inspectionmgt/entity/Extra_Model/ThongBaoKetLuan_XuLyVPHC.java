package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucXuLyChinh;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class ThongBaoKetLuan_XuLyVPHC {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_XU_LY_VPHC;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("SoQuyetDinh")
    @Field(value = "SoQuyetDinh", order = 2)
    public String soQuyetDinh = StringPool.BLANK;

    @JsonProperty("NgayQuyetDinh")
    @Field(value = "NgayQuyetDinh", order = 3)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayQuyetDinh = null;

    @JsonProperty("CoQuanQuyetDinh")
    @Field(value = "CoQuanQuyetDinh", order = 4)
    public CoQuanQuyetDinh coQuanQuyetDinh = new CoQuanQuyetDinh();

    @JsonProperty("DoiTuongVPHC")
    @Field(value = "DoiTuongVPHC", order = 5)
    public DoiTuongVPHC doiTuongVPHC = new DoiTuongVPHC();

    @JsonProperty("HinhThucXuLyChinh")
    @Field(value = "HinhThucXuLyChinh", order = 6)
    public HinhThucXuLyChinh hinhThucXuLyChinh = new HinhThucXuLyChinh();

    @Getter
    @Setter
    public static class CoQuanQuyetDinh {
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

    @Getter
    @Setter
    public static class HinhThucXuLyChinh {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_HINH_THUC_XU_LY_CHINH;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class DoiTuongVPHC {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.T_CA_NHAN_TO_CHUC;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh", order = 1)
        public String maDinhDanh = StringPool.BLANK;

        @JsonProperty("TenGoi")
        @Field(value = "TenGoi", order = 2)
        public String tenGoi = StringPool.BLANK;

        @JsonProperty("LoaiDoiTuongCNTC")
        @Field(value = "LoaiDoiTuongCNTC", order = 3)
        public LoaiDoiTuongCNTC loaiDoiTuongCNTC = new LoaiDoiTuongCNTC();

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
    }
}
