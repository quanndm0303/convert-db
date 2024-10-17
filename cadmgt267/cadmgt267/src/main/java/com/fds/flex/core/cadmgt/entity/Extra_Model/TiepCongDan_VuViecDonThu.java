package com.fds.flex.core.cadmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.entity.S_Model.TepDuLieu;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TiepCongDan_VuViecDonThu {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_VU_VIEC_DON_THU;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = null;

    @JsonProperty("NgayVietDon")
    @Field(value = "NgayVietDon", order = 2)
    public String ngayVietDon = null;

    @JsonProperty("NoiDungVuViec")
    @Field(value = "NoiDungVuViec", order = 3)
    public String noiDungVuViec = StringPool.BLANK;

    @JsonProperty("KienNghiCongDan")
    @Field(value = "KienNghiCongDan", order = 3)
    public String kienNghiCongDan = null;

    @JsonProperty("KiemTraVuViec")
    @Field(value = "KiemTraVuViec", order = 3)
    public KiemTraVuViec kiemTraVuViec = null;

    @JsonProperty("LanTiepThu")
    @Field(value = "LanTiepThu", order = 4)
    public Integer lanTiepThu = 0;

    @JsonProperty("LoaiVuViecDonThu")
    @Field(value = "LoaiVuViecDonThu", order = 5)
    public LoaiVuViecDonThu loaiVuViecDonThu = null;

    @JsonProperty("PhanLoaiTrachNhiem")
    @Field(value = "PhanLoaiTrachNhiem", order = 6)
    public boolean phanLoaiTrachNhiem = false; //"Phân loại trách nhiệm: true: thuộc trách nhiệm false: không thuộc trách nhiệm"

    @JsonProperty("PhanLoaiThamQuyen")
    @Field(value = "PhanLoaiThamQuyen", order = 7)
    public boolean phanLoaiThamQuyen = false; //"Phân loại thẩm quyền: true: thuộc thẩm quyền false: không thuộc thẩm quyền"

    @JsonProperty("LinhVucChuyenNganh")
    @Field(value = "LinhVucChuyenNganh", order = 8)
    public LinhVucChuyenNganh linhVucChuyenNganh = null;

    @JsonProperty("DiaBanXuLy")
    @Field(value = "DiaBanXuLy", order = 9)
    public DiaBanXuLy diaBanXuLy = null;

    @JsonProperty("KetQuaGiaiQuyetTruoc")
    @Field(value = "KetQuaGiaiQuyetTruoc", order = 11)
    public String ketQuaGiaiQuyetTruoc = null;

    @JsonProperty("DonThuDinhKem")
    @Field(value = "DonThuDinhKem", order = 12)
    public List<TepDuLieu> donThuDinhKem = new ArrayList<>();

    @JsonProperty("GhiChuVuViec")
    @Field(value = "GhiChuVuViec", order = 13)
    public String ghiChuVuViec = null;

    @JsonProperty("HoSoNghiepVu")
    @Field(value = "HoSoNghiepVu", order = 13)
    public HoSoNghiepVu hoSoNghiepVu = null;

    @Getter
    @Setter
    public static class HoSoNghiepVu {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.T_HO_SO_NGHIEP_VU;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh", order = 1)
        public String maDinhDanh = StringPool.BLANK;

        @JsonProperty("SoDangKy")
        @Field(value = "SoDangKy", order = 2)
        public String soDangKy = StringPool.BLANK;
    }

    @Getter
    @Setter
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

    @Getter
    @Setter
    public static class KiemTraVuViec {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_KIEM_TRA_TRUNG_DON;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class DiaBanXuLy {
        @JsonProperty("@type")
        @Field(value = "@type")
        public String type = DBConstant.C_DIA_BAN_XU_LY;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class LinhVucChuyenNganh {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_LINH_VUC_CHUYEN_NGANH;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class CoQuanDaGiaiQuyet {
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
