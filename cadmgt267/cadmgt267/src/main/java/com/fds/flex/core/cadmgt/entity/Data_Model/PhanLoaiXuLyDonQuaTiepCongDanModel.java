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
public class PhanLoaiXuLyDonQuaTiepCongDanModel {
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

        @JsonProperty("DonKhieuNai")
        public long donKhieuNai = 0;

        @JsonProperty("DonToCao")
        public long donToCao = 0;

        @JsonProperty("DonPAKN")
        public long donPAKN = 0;

        @JsonProperty("DonThuocThamQuyen")
        public long donThuocThamQuyen = 0;

        @JsonProperty("DonNgoaiThamQuyen")
        public long donNgoaiThamQuyen = 0;

        @JsonProperty("VuViecKhieuNai")
        public long vuViecKhieuNai = 0;

        @JsonProperty("VuViecToCao")
        public long vuViecToCao = 0;

        @JsonProperty("VuViecPAKN")
        public long vuViecPAKN = 0;

        @JsonProperty("VuViecThuocThamQuyen")
        public long vuViecThuocThamQuyen = 0;

        @JsonProperty("VuViecNgoaiThamQuyen")
        public long vuViecNgoaiThamQuyen = 0;

        @JsonProperty("VuViecHuongDan")
        public long vuViecHuongDan = 0;

        @JsonProperty("VuViecChuyenDon")
        public long vuViecChuyenDon = 0;

        @JsonProperty("VuViecDonDocNgoaiThamQuyen")
        public long vuViecDonDocNgoaiThamQuyen = 0;

        @JsonProperty("VanBanPhucDap")
        public long vanBanPhucDap = 0;

        @JsonProperty("TongSoDonTCD")
        public long tongSoDonTCD = 0;

        @JsonProperty("TongSoVuViecTCD")
        public long tongSoVuViecTCD = 0;

    }
}
