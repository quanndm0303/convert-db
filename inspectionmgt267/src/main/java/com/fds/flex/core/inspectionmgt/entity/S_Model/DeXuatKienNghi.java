package com.fds.flex.core.inspectionmgt.entity.S_Model;

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
public class DeXuatKienNghi {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_DE_XUAT_HOAN_THIEN;

    @JsonProperty("IDKienNghi")
    @Field(value = "IDKienNghi", order = 1)
    public String idKienNghi = String.valueOf(System.currentTimeMillis());

    @JsonProperty("LoaiDeXuatKienNghi")
    @Field(value = "LoaiDeXuatKienNghi", order = 2)
    public LoaiDeXuatKienNghi loaiDeXuatKienNghi = new LoaiDeXuatKienNghi();

    @JsonProperty("DonViThamQuyen")
    @Field(value = "DonViThamQuyen", order = 3)
    public DonViThamQuyen donViThamQuyen = null;

    @JsonProperty("DoiTuongXuLy")
    @Field(value = "DoiTuongXuLy", order = 4)
    public List<DoiTuongXuLy> doiTuongXuLy = new ArrayList<>();

    @JsonProperty("LoiViPham")
    @Field(value = "LoiViPham", order = 5)
    public String loiViPham = null;

    @JsonProperty("NoiDungKienNghi")
    @Field(value = "NoiDungKienNghi", order = 6)
    public String noiDungKienNghi = null;

    @JsonProperty("SoTienThuHoi")
    @Field(value = "SoTienThuHoi", order = 7)
    public Long soTienThuHoi = null;

    @JsonProperty("DienTichThuHoi")
    @Field(value = "DienTichThuHoi", order = 8)
    public Double dienTichThuHoi = null;

    @JsonProperty("VanBanHoanThien")
    @Field(value = "VanBanHoanThien", order = 9)
    public String vanBanHoanThien = null;

    @JsonProperty("ThoiHanThucHien")
    @Field(value = "ThoiHanThucHien", order = 10)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanThucHien = null;

    @JsonProperty("HieuLucThiHanh")
    @Field(value = "HieuLucThiHanh", order = 11)
    public HieuLucThiHanh hieuLucThiHanh = new HieuLucThiHanh();

    @JsonProperty("NgayHoanThanh")
    @Field(value = "NgayHoanThanh", order = 12)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayHoanThanh = null;

    @JsonProperty("SoTienDaThuHoi")
    @Field(value = "SoTienDaThuHoi", order = 13)
    public Long soTienDaThuHoi = null;

    @JsonProperty("DienTichDaThuHoi")
    @Field(value = "DienTichDaThuHoi", order = 14)
    public Double dienTichDaThuHoi = null;

    @JsonProperty("SoTienDaThuHoiSauKiemTra")
    @Field(value = "SoTienDaThuHoiSauKiemTra", order = 14)
    public Long soTienDaThuHoiSauKiemTra = null;

    @JsonProperty("DienTichDaThuHoiSauKiemTra")
    @Field(value = "DienTichDaThuHoiSauKiemTra", order = 14)
    public Double dienTichDaThuHoiSauKiemTra = null;

    @JsonProperty("HinhThucXuLy")
    @Field(value = "HinhThucXuLy", order = 14)
    public HinhThucXuLy hinhThucXuLy = null;

    @JsonProperty("KhoiToDieuTra")
    @Field(value = "KhoiToDieuTra", order = 15)
    public Boolean khoiToDieuTra = null;

    @JsonProperty("LyDoKhongThucHien")
    @Field(value = "LyDoKhongThucHien", order = 16)
    public String lyDoKhongThucHien = null;

    @JsonProperty("GhiChuTheoDoi")
    @Field(value = "GhiChuTheoDoi", order = 17)
    public String ghiChuTheoDoi = null;

    @Getter
    @Setter
    public static class LoaiDeXuatKienNghi {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_LOAI_DE_XUAT_KIEN_NGHI;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class HinhThucXuLy {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_HINH_THUC_XU_LY;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class DonViThamQuyen {

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
    public static class DoiTuongXuLy {

        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.T_CO_QUAN_DON_VI;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh", order = 1)
        public String maDinhDanh = StringPool.BLANK;

        @JsonProperty("TenGoi")
        @Field(value = "TenGoi", order = 2)
        public String tenGoi = StringPool.BLANK;

        @JsonProperty("LoaiDoiTuongCNTC")
        @Field(value = "LoaiDoiTuongCNTC", order = 3)
        public LoaiDoiTuongCNTC loaiDoiTuongCNTC = new LoaiDoiTuongCNTC();
    }

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
    public static class HieuLucThiHanh {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_HIEU_LUC_THI_HANH;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }
}
