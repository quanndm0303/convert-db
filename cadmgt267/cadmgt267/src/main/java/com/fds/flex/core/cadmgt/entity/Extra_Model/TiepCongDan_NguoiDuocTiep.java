package com.fds.flex.core.cadmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.entity.S_Model.DanhBaLienLac;
import com.fds.flex.core.cadmgt.entity.S_Model.DiaChi;
import com.fds.flex.core.cadmgt.entity.S_Model.GiayToChungNhan;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class TiepCongDan_NguoiDuocTiep {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_CA_NHAN_TO_CHUC;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("TenGoi")
    @Field(value = "TenGoi", order = 2)
    public String tenGoi = StringPool.BLANK;

    @JsonProperty("NgaySinh")
    @Field(value = "NgaySinh", order = 3)
    public String ngaySinh = null;

    @JsonProperty("GioiTinh")
    @Field(value = "GioiTinh", order = 4)
    public GioiTinh gioiTinh = null;

    @JsonProperty("GiayToChungNhan")
    @Field(value = "GiayToChungNhan", order = 5)
    public GiayToChungNhan giayToChungNhan = null;

    @JsonProperty("DiaChi")
    @Field(value = "DiaChi", order = 6)
    public DiaChi diaChi = null;

    @JsonProperty("DiaChiLienHe")
    @Field(value = "DiaChiLienHe", order = 7)
    public DiaChi diaChiLienHe = null;

    @JsonProperty("DanhBaLienLac")
    @Field(value = "DanhBaLienLac", order = 8)
    public DanhBaLienLac danhBaLienLac = null;

    @JsonProperty("TuCachPhapLy")
    @Field(value = "TuCachPhapLy", order = 9)
    public TuCachPhapLy tuCachPhapLy = new TuCachPhapLy();

    @JsonProperty("NguoiDaiDien")
    @Field(value = "NguoiDaiDien", order = 10)
    public Boolean nguoiDaiDien = null;

    @Getter
    @Setter
    public static class TuCachPhapLy {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_TU_CACH_PHAP_LY;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class GioiTinh {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_GIOI_TINH;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }
}
