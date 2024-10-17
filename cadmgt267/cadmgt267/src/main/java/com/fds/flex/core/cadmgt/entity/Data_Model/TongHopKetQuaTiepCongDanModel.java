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
public class TongHopKetQuaTiepCongDanModel {
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

        @JsonProperty("ThXSoLuotTiep")
        public long thXSoLuotTiep = 0;

        @JsonProperty("ThTgSoLuotTiep")
        public long thTgSoLuotTiep = 0;

        @JsonProperty("UQSoLuotTiep")
        public long uQSoLuotTiep = 0;

        @JsonProperty("TongSoLuotTiep")
        public long tongSoLuotTiep = 0;

        @JsonProperty("ThXSoNguoiTiep")
        public long thXSoNguoiTiep = 0;

        @JsonProperty("ThTgSoNguoiTiep")
        public long thTgSoNguoiTiep = 0;

        @JsonProperty("UQSoNguoiTiep")
        public long uQSoNguoiTiep = 0;

        @JsonProperty("TongSoNguoiTiep")
        public long tongSoNguoiTiep = 0;

        @JsonProperty("ThXVuViecLanDau")
        public long thXVuViecLanDau = 0;

        @JsonProperty("ThXVuViecNhieuLan")
        public long thXVuViecNhieuLan = 0;

        @JsonProperty("ThTgVuViecLanDau")
        public long thTgVuViecLanDau = 0;

        @JsonProperty("ThTgVuViecNhieuLan")
        public long thTgVuViecNhieuLan = 0;

        @JsonProperty("TUQVuViecLanDau")
        public long tUQVuViecLanDau = 0;

        @JsonProperty("TUQVuViecNhieuLan")
        public long tUQVuViecNhieuLan = 0;

        @JsonProperty("TongSoVuViec")
        public long tongSoVuViec = 0;

        @JsonProperty("ThXDoanDongSoLuotTiep")
        public long thXDoanDongSoLuotTiep = 0;

        @JsonProperty("ThXDoanDongSoNguoiTiep")
        public long thXDoanDongSoNguoiTiep = 0;

        @JsonProperty("ThXDoanDongVuViecLanDau")
        public long thXDoanDongVuViecLanDau = 0;

        @JsonProperty("ThXDoanDongVuViecNhieuLan")
        public long thXDoanDongVuViecNhieuLan = 0;

        @JsonProperty("ThTgSoKyTiep")
        public long thTgSoKyTiep = 0;

        @JsonProperty("TUQSoKyTiep")
        public long tUQSoKyTiep = 0;

        @JsonProperty("ThTgDoanDongSoLuotTiep")
        public long thTgDoanDongSoLuotTiep = 0;

        @JsonProperty("ThTgDoanDongSoNguoiTiep")
        public long thTgDoanDongSoNguoiTiep = 0;

        @JsonProperty("ThTgDoanDongVuViecLanDau")
        public long thTgDoanDongVuViecLanDau = 0;

        @JsonProperty("ThTgDoanDongVuViecNhieuLan")
        public long thTgDoanDongVuViecNhieuLan = 0;

        @JsonProperty("TUQDoanDongSoLuotTiep")
        public long tUQDoanDongSoLuotTiep = 0;

        @JsonProperty("TUQDoanDongSoNguoiTiep")
        public long tUQDoanDongSoNguoiTiep = 0;

        @JsonProperty("TUQDoanDongVuViecLanDau")
        public long tUQDoanDongVuViecLanDau = 0;

        @JsonProperty("TUQDoanDongVuViecNhieuLan")
        public long tUQDoanDongVuViecNhieuLan = 0;


    }
}
