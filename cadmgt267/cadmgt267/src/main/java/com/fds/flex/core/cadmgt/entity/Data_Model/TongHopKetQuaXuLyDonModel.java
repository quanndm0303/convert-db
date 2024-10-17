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
public class TongHopKetQuaXuLyDonModel {
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

        @JsonProperty("DonKyTruocChuyenSang")
        public long donKyTruocChuyenSang = 0;

        @JsonProperty("DonTiepNhanTrongKy")
        public long donTiepNhanTrongKy = 0;

        @JsonProperty("DonXuLyTrongKy")
        public long donXuLyTrongKy = 0;

        @JsonProperty("TongSoDonPhaiXuLy")
        public long tongSoDonPhaiXuLy = 0;

        @JsonProperty("SoDonChuyenKySau")
        public long soDonChuyenKySau = 0;

        @JsonProperty("DonDuDieuKienThuLy")
        public long donDuDieuKienThuLy = 0;

        @JsonProperty("DonKNDuDieuKienThuLy")
        public long donKNDuDieuKienThuLy = 0;

        @JsonProperty("DonTCDuDieuKienThuLy")
        public long donTCDuDieuKienThuLy = 0;

        @JsonProperty("DonPAKNDuDieuKienThuLy")
        public long donPAKNDuDieuKienThuLy = 0;

        @JsonProperty("DonGiaiQuyetLanDau")
        public long donGiaiQuyetLanDau = 0;

        @JsonProperty("DonGiaiQuyetNhieuLan")
        public long donGiaiQuyetNhieuLan = 0;

        @JsonProperty("DonChuaGiaiQuyetXong")
        public long donChuaGiaiQuyetXong = 0;

        @JsonProperty("DonGiaiQuyetKNTheoThamQuyen")
        public long donGiaiQuyetKNTheoThamQuyen = 0;

        @JsonProperty("DonGiaiQuyetTCTheoThamQuyen")
        public long donGiaiQuyetTCTheoThamQuyen = 0;

        @JsonProperty("DonGiaiQuyetPAKNTheoThamQuyen")
        public long donGiaiQuyetPAKNTheoThamQuyen = 0;

        @JsonProperty("XuLyDonThuocThamQuyen")
        public long xuLyDonThuocThamQuyen = 0;

        @JsonProperty("DonKhongThuocThamQuyen")
        public long donKhongThuocThamQuyen = 0;

        @JsonProperty("DonKhongThuocThamQuyenDaHuongDan")
        public long donKhongThuocThamQuyenDaHuongDan = 0;

        @JsonProperty("DonKhongThuocThamQuyenDaChuyenDon")
        public long donKhongThuocThamQuyenDaChuyenDon = 0;

        @JsonProperty("DonKhongThuocThamQuyenDaDonDoc")
        public long donKhongThuocThamQuyenDaDonDoc = 0;

        @JsonProperty("VanBanPhucDap")
        public long vanBanPhucDap = 0;

        @JsonProperty("VuViecDuDieuKienThuLy")
        public long vuViecDuDieuKienThuLy = 0;


    }
}
