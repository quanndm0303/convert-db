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
public class TongHopKetQuaXuLyDonKhieuNaiModel {
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

        @JsonProperty("DonKNKyTruocChuyenSang")
        public long donKNKyTruocChuyenSang = 0;

        @JsonProperty("DonKNTiepNhanTrongKy")
        public long donKNTiepNhanTrongKy = 0;

        @JsonProperty("XuLyDonKN")
        public long xuLyDonKN = 0;

        @JsonProperty("DonKNDaXuLy")
        public long donKNDaXuLy = 0;

        @JsonProperty("DonKNXuLyTrongKy")
        public long donKNXuLyTrongKy = 0;

        @JsonProperty("DonKNKyTruocDaXuLy")
        public long donKNKyTruocDaXuLy = 0;

        @JsonProperty("DonKNDuDieuKienThuLy")
        public long donKNDuDieuKienThuLy = 0;

        @JsonProperty("VuViecKNDuDieuKienThuLy")
        public long vuViecKNDuDieuKienThuLy = 0;

        @JsonProperty("VuViecKNGiaiQuyetLanDau")
        public long vuViecKNGiaiQuyetLanDau = 0;

        @JsonProperty("VuViecKNGiaiQuyetLan2")
        public long vuViecKNGiaiQuyetLan2 = 0;

        @JsonProperty("VuViecKNBanAnTAND")
        public long vuViecKNBanAnTAND = 0;

        @JsonProperty("VuViecKNChuaGiaiQuyet")
        public long vuViecKNChuaGiaiQuyet = 0;

        @JsonProperty("VuViecKNTheoThamQuyen")
        public long vuViecKNTheoThamQuyen = 0;

        @JsonProperty("VuViecKNLan2TheoThamQuyen")
        public long vuViecKNLan2TheoThamQuyen = 0;

        @JsonProperty("VuViecKNLanDauTheoThamQuyen")
        public long vuViecKNLanDauTheoThamQuyen = 0;

        @JsonProperty("VuViecKNKhongThuocThamQuyenDaHuongDan")
        public long vuViecKNKhongThuocThamQuyenDaHuongDan = 0;

        @JsonProperty("VuViecKNKhongThuocThamQuyenDaDonDoc")
        public long vuViecKNKhongThuocThamQuyenDaDonDoc = 0;

        @JsonProperty("VuViecKNKhongThuocThamQuyen")
        public long vuViecKNKhongThuocThamQuyen = 0;

        @JsonProperty("VanBanPhucDap")
        public long vanBanPhucDap = 0;

        @JsonProperty("VuViecKNHanhChinh")
        public long vuViecKNHanhChinh = 0;

        @JsonProperty("VuViecKNHanhChinhCDCS")
        public long vuViecKNHanhChinhCDCS = 0;

        @JsonProperty("VuViecKNHanhChinhDDNC")
        public long vuViecKNHanhChinhDDNC = 0;

        @JsonProperty("VuViecKNHanhChinhKHAC")
        public long vuViecKNHanhChinhKHAC = 0;

        @JsonProperty("VuViecKNTuPhap")
        public long vuViecKNTuPhap = 0;

        @JsonProperty("VuViecKNDangDoan")
        public long vuViecKNDangDoan = 0;

        @JsonProperty("VuViecKNKhac")
        public long vuViecKNKhac = 0;


    }
}
