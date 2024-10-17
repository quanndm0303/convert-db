package com.fds.flex.core.cadmgt.entity.Data_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TongHopKetQuaXuLyDonKienNghiPhanAnhModel {
    @JsonProperty("MaDonVi")
    public String maDonVi = StringPool.BLANK;

    @JsonProperty("TenDonVi")
    public String tenDonVi = StringPool.BLANK;

    @JsonProperty("GhiChu")
    public String ghiChu = StringPool.BLANK;

    @JsonProperty("TuNgay")
    public Long tuNgay = 0L;

    @JsonProperty("DenNgay")
    public Long denNgay = 0L;

    @JsonProperty("Data")
    public List<TongHopKetQua> tongHopKetQua = new ArrayList<>();

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TongHopKetQua {

        @JsonProperty("MaDonVi")
        public String maDonVi = StringPool.BLANK;

        @JsonProperty("TenDonVi")
        public String tenDonVi = StringPool.BLANK;

        @JsonProperty("DonKNPAKyTruocChuyenSang")
        public long donKNPAKyTruocChuyenSang = 0;

        @JsonProperty("DonKNPATiepNhanTrongKy")
        public long donKNPATiepNhanTrongKy = 0;

        @JsonProperty("XuLyDonKNPA")
        public long xuLyDonKNPA = 0;

        @JsonProperty("DonPAKNKyTruocDaKiemTraDKXL")
        public long donPAKNKyTruocDaKiemTraDKXL = 0;

        @JsonProperty("DonPAKNTrongKyDaKiemTraDKXL")
        public long donPAKNTrongKyDaKiemTraDKXL = 0;

        @JsonProperty("DonPAKNDaKiemTraDieuKienXuLy")
        public long donPAKNDaKiemTraDieuKienXuLy = 0;

        @JsonProperty("DonKNPADuDieuKienThuLy")
        public long donKNPADuDieuKienThuLy = 0;

        @JsonProperty("VuViecKNPADuDieuKienThuLy")
        public long vuViecKNPADuDieuKienThuLy = 0;

        @JsonProperty("VuViecKNPAHanhChinhCDCS")
        public long vuViecKNPAHanhChinhCDCS = 0;

        @JsonProperty("VuViecKNPAHanhChinhDDNC")
        public long vuViecKNPAHanhChinhDDNC = 0;

        @JsonProperty("VuViecKNPATuPhap")
        public long vuViecKNPATuPhap = 0;

        @JsonProperty("VuViecKNPAKHAC")
        public long vuViecKNPAKHAC = 0;

        @JsonProperty("VuViecKNPAGiaiQuyet")
        public long vuViecKNPAGiaiQuyet = 0;

        @JsonProperty("VuViecKNPAChuaGiaiQuyet")
        public long vuViecKNPAChuaGiaiQuyet = 0;

        @JsonProperty("VuViecKNPAThuocThamQuyenGiaiQuyet")
        public long vuViecKNPAThuocThamQuyenGiaiQuyet = 0;

        @JsonProperty("VuViecKNPAKhongThuocThamQuyen")
        public long vuViecKNPAKhongThuocThamQuyen = 0;

        @JsonProperty("VuViecKNPAKhongThuocThamQuyenDaChuyenDon")
        public long vuViecKNPAKhongThuocThamQuyenDaChuyenDon = 0;

        @JsonProperty("VuViecKNPAKhongThuocThamQuyenDaDonDoc")
        public long vuViecKNPAKhongThuocThamQuyenDaDonDoc = 0;

        @JsonProperty("VuViecKNPABanHanhKQGiaiQuyet")
        public long vuViecKNPABanHanhKQGiaiQuyet = 0;

        @JsonProperty("VuViecKNPAChuaGiaiQuyetXong")
        public long vuViecKNPAChuaGiaiQuyetXong = 0;
    }
}
