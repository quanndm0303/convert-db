package com.fds.flex.core.inspectionmgt.entity.Data_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TongHopKetQuaThanhTraHanhChinhModel {
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

        @JsonProperty("CuocKyTruocChuyenSang")
        public long cuocKyTruocChuyenSang = 0;

        @JsonProperty("CuocTrongKy")
        public long cuocTrongKy = 0;

        @JsonProperty("CuocKeHoach")
        public long cuocKeHoach = 0;

        @JsonProperty("CuocDotXuat")
        public long cuocDotXuat = 0;

        @JsonProperty("TongSoCuoc")
        public long tongSoCuoc = 0;

        @JsonProperty("CuocThanhTraBHKL")
        public long cuocThanhTraBHKL = 0;

        @JsonProperty("DonViDuocThanhTra")
        public long donViDuocThanhTra = 0;

        @JsonProperty("NSNNSoTienThuHoi")
        public double nSNNSoTienThuHoi = 0;

        @JsonProperty("XLVPKTSoTienThuHoi")
        public double xLVPKTSoTienThuHoi = 0;

        @JsonProperty("TongSoTienThuHoi")
        public double tongSoTienThuHoi = 0;

        @JsonProperty("NSNNDienTichThuHoi")
        public long nSNNDienTichThuHoi = 0;

        @JsonProperty("XLVPKTDienTichThuHoi")
        public long xLVPKTDienTichThuHoi = 0;

        @JsonProperty("TongDienTichThuHoi")
        public long tongDienTichThuHoi = 0;

        @JsonProperty("TongSoCuocThanhTraThucHienTrongKy")
        public long tongSoCuocThanhTraThucHienTrongKy = 0;

        @JsonProperty("TongTienVaTaiSanQuyThanhTien")
        public double tongTienVaTaiSanQuyThanhTien = 0;

        @JsonProperty("TongViPhamVeKinhTeDat")
        public double tongViPhamVeKinhTeDat = 0;

        @JsonProperty("XLHCToChuc")
        public long xLHCToChuc = 0;

        @JsonProperty("XLHCCaNhan")
        public long xLHCCaNhan = 0;

        @JsonProperty("CQDTVuViec")
        public long cQDTVuViec = 0;

        @JsonProperty("CQDTDoiTuong")
        public long cQDTDoiTuong = 0;

        @JsonProperty("VanBan")
        public long vanBan = 0;


    }
}
