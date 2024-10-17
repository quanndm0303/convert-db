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
public class TongHopKetQuaXuLyDonToCaoModel {
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

        @JsonProperty("DonTCKyTruocChuyenSang")
        public long donTCKyTruocChuyenSang = 0;

        @JsonProperty("DonTCTiepNhanTrongKy")
        public long donTCTiepNhanTrongKy = 0;

        @JsonProperty("XuLyDonTC")
        public long xuLyDonTC = 0;

        @JsonProperty("DonTCDaXuLy")
        public long donTCDaXuLy = 0;

        @JsonProperty("DonTCXuLyTrongKy")
        public long donTCXuLyTrongKy = 0;

        @JsonProperty("DonTCKyTruocDaXuLy")
        public long donTCKyTruocDaXuLy = 0;

        @JsonProperty("DonTCDuDieuKienThuLy")
        public long donTCDuDieuKienThuLy = 0;

        @JsonProperty("VuViecTCDuDieuKienThuLy")
        public long vuViecTCDuDieuKienThuLy = 0;

        @JsonProperty("VuViecTCHanhChinh")
        public long vuViecTCHanhChinh = 0;

        @JsonProperty("VuViecTCHanhChinhCDCS")
        public long vuViecTCHanhChinhCDCS = 0;

        @JsonProperty("VuViecTCHanhChinhDDNC")
        public long vuViecTCHanhChinhDDNC = 0;

        @JsonProperty("VuViecTCHanhChinhCCCV")
        public long vuViecTCHanhChinhCCCV = 0;

        @JsonProperty("VuViecTCHanhChinhKHAC")
        public long vuViecTCHanhChinhKHAC = 0;

        @JsonProperty("VuViecTCThamNhung")
        public long vuViecTCThamNhung = 0;

        @JsonProperty("VuViecTCTuPhap")
        public long vuViecTCTuPhap = 0;

        @JsonProperty("VuViecTCDangDoan")
        public long vuViecTCDangDoan = 0;

        @JsonProperty("VuViecTCKhac")
        public long vuViecTCKhac = 0;

        @JsonProperty("VuViecTCGiaiQuyetTBKL")
        public long vuViecTCGiaiQuyetTBKL = 0;

        @JsonProperty("VuViecTCChuaGiaiQuyet")
        public long vuViecTCChuaGiaiQuyet = 0;

        @JsonProperty("VuViecTCQuaHanChuaGiaiQuyet")
        public long vuViecTCQuaHanChuaGiaiQuyet = 0;

        @JsonProperty("VuViecTCGiaiQuyetLanDau")
        public long vuViecTCGiaiQuyetLanDau = 0;

        @JsonProperty("VuViecTCtiep")
        public long vuViecTCtiep = 0;

        @JsonProperty("VuViecTCThuocThamQuyen")
        public long vuViecTCThuocThamQuyen = 0;

        @JsonProperty("VuViecTCKhongThuocThamQuyenDaChuyenDon")
        public long vuViecTCKhongThuocThamQuyenDaChuyenDon = 0;

        @JsonProperty("VuViecTCKhongThuocThamQuyenDaDonDoc")
        public long vuViecTCKhongThuocThamQuyenDaDonDoc = 0;

        @JsonProperty("VuViecTCKhongThuocThamQuyen")
        public long vuViecTCKhongThuocThamQuyen = 0;

        @JsonProperty("VanBanPhucDap")
        public long vanBanPhucDap = 0;


    }
}
