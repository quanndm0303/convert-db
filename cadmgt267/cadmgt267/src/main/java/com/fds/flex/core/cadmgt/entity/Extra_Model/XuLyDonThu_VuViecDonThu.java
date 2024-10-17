package com.fds.flex.core.cadmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.DateTimeUtils;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.entity.S_Model.TepDuLieu;
import com.fds.flex.core.cadmgt.util.CADMgtUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class XuLyDonThu_VuViecDonThu {
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
    @Field(value = "KienNghiCongDan", order = 4)
    public String kienNghiCongDan = null;

    @JsonProperty("KiemTraVuViec")
    @Field(value = "KiemTraVuViec", order = 5)
    public KiemTraVuViec kiemTraVuViec = null;

    @JsonProperty("LoaiPhucTapKeoDai")
    @Field(value = "LoaiPhucTapKeoDai", order = 6)
    public LoaiPhucTapKeoDai loaiPhucTapKeoDai = null;

    @JsonProperty("LoaiVuViecDonThu")
    @Field(value = "LoaiVuViecDonThu", order = 7)
    public LoaiVuViecDonThu loaiVuViecDonThu = null;

    @JsonProperty("LoaiChiTietVuViec")
    @Field(value = "LoaiChiTietVuViec", order = 8)
    public LoaiChiTietVuViec loaiChiTietVuViec = null;

    @JsonProperty("DoiTuongVuViec")
    @Field(value = "DoiTuongVuViec", order = 9)
    public List<DoiTuongVuViec> doiTuongVuViec = new ArrayList<>();

    @JsonProperty("PhanLoaiTrachNhiem")
    @Field(value = "PhanLoaiTrachNhiem", order = 10)
    public boolean phanLoaiTrachNhiem = false; //"Phân loại trách nhiệm: true: thuộc trách nhiệm false: không thuộc trách nhiệm"

    @JsonProperty("PhanLoaiThamQuyen")
    @Field(value = "PhanLoaiThamQuyen", order = 11)
    public boolean phanLoaiThamQuyen = false; //"Phân loại thẩm quyền: true: thuộc thẩm quyền false: không thuộc thẩm quyền"

    @JsonProperty("LinhVucChuyenNganh")
    @Field(value = "LinhVucChuyenNganh", order = 12)
    public LinhVucChuyenNganh linhVucChuyenNganh = null;

    @JsonProperty("KetQuaGiaiQuyetTruoc")
    @Field(value = "KetQuaGiaiQuyetTruoc", order = 13)
    public String ketQuaGiaiQuyetTruoc = null;

    @JsonProperty("DonThuDinhKem")
    @Field(value = "DonThuDinhKem", order = 14)
    public List<TepDuLieu> donThuDinhKem = new ArrayList<>();

    @JsonProperty("TinhTrangXuLyVuViec")
    @Field(value = "TinhTrangXuLyVuViec", order = 15)
    public TinhTrangXuLyVuViec tinhTrangXuLyVuViec = null;

    @JsonProperty("ThoiHanThucHien")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    @Field(value = "ThoiHanThucHien", order = 16)
    public Long thoiHanThucHien = null;

    @JsonProperty("CoQuanThamQuyen")
    @Field(value = "CoQuanThamQuyen", order = 17)
    public CoQuanThamQuyen coQuanThamQuyen = null;

    @JsonProperty("LanGiaiQuyet")
    @Field(value = "LanGiaiQuyet", order = 18)
    public Integer lanGiaiQuyet = null;

    @JsonProperty("PhanTichKetQuaKNTC")
    @Field(value = "PhanTichKetQuaKNTC", order = 19)
    public PhanTichKetQuaKNTC phanTichKetQuaKNTC = null;

    @JsonProperty("KhoiKienHanhChinh")
    @Field(value = "KhoiKienHanhChinh", order = 20)
    public KhoiKienHanhChinh khoiKienHanhChinh = null;

    @JsonProperty("GhiChuVuViec")
    @Field(value = "GhiChuVuViec", order = 21)
    public String ghiChuVuViec = null;

    @JsonProperty("DiaBanXuLy")
    @Field(value = "DiaBanXuLy", order = 23)
    public DiaBanXuLy diaBanXuLy = null;

    @Transient
    @JsonProperty("ThoiHanXuLy")
    public Long thoiHanXuLy = 0L;

    public Long getThoiHanXuLy() {
        if (Validator.isNull(this.getThoiHanThucHien())) {
            return thoiHanXuLy;
        }
        Date now = new Date();
        Date thoiHanThucHien = new Date(this.getThoiHanThucHien());
        long soNgayConLaiCuaBuoc = DateTimeUtils.numberOfDayFrom2Date(now, thoiHanThucHien, false);
        long soNgayNghiLe = 0;
        List<String> rangeDate = DateTimeUtils.generateDateRange(now, thoiHanThucHien, DateTimeUtils._VN_DATE_TIME);
        for (String date : rangeDate) {
            if (CADMgtUtil.ngayNghiLe.containsKey(date)) {
                soNgayNghiLe++;
            }
        }
        return soNgayConLaiCuaBuoc - soNgayNghiLe + 1;
    }

    @Getter
    @Setter
    public static class DoiTuongVuViec {
        @JsonProperty("@type")
        @Field(value = "@type")
        public String type = DBConstant.T_CA_NHAN_TO_CHUC;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh")
        public String maDinhDanh = null;

        @JsonProperty("TenGoi")
        @Field(value = "TenGoi")
        public String tenGoi = StringPool.BLANK;

        @JsonProperty("LoaiDoiTuongCNTC")
        @Field(value = "LoaiDoiTuongCNTC")
        public LoaiDoiTuongCNTC loaiDoiTuongCNTC = new LoaiDoiTuongCNTC();

        @JsonProperty("NoiBoTrongNganh")
        @Field(value = "NoiBoTrongNganh")
        public boolean noiBoTrongNganh = false;

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

    @Getter
    @Setter
    public static class TinhTrangXuLyVuViec {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_TINH_TRANG_XU_LY_VU_VIEC;

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
        @Field(value = DBConstant.TYPE, order = 0)
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
    public static class LoaiPhucTapKeoDai {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_LOAI_PHUC_TAP_KEO_DAI;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

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
    @Setter
    public static class KhoiKienHanhChinh {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_KHOI_KIEN_HANH_CHINH;

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

    @Getter
    @Setter
    public static class CoQuanThamQuyen {
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
    public static class LoaiChiTietVuViec {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_LOAI_CHI_TIET_VU_VIEC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Setter
    @Getter
    public static class DoiTuongBiKNTC {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_DOI_TUONG_BI_KNTC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

}
