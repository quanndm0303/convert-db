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
public class TongHopKetQuaThanhTraChuyenNganhModel {
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

        @JsonProperty("CuocKeHoach")
        public long cuocKeHoach = 0;

        @JsonProperty("CuocDotXuat")
        public long cuocDotXuat = 0;

        @JsonProperty("TongSoCuoc")
        public long tongSoCuoc = 0;

        @JsonProperty("CuocThanhTraBHKL")
        public long cuocThanhTraBHKL = 0;

        @JsonProperty("ToChucDuocThanhTra")
        public long toChucDuocThanhTra = 0;

        @JsonProperty("CaNhanDuocThanhTra")
        public long caNhanDuocThanhTra = 0;

        @JsonProperty("ToChucViPham")
        public long toChucViPham = 0;

        @JsonProperty("CaNhanViPham")
        public long caNhanViPham = 0;

        @JsonProperty("SoDonViViPham")
        public long soDonViViPham = 0;

        @JsonProperty("SoTienToChucVPKT")
        public double soTienToChucVPKT = 0;

        @JsonProperty("SoTienCaNhanVPKT")
        public double soTienCaNhanVPKT = 0;

        @JsonProperty("TongTienViPham")
        public double tongTienViPham = 0;

        @JsonProperty("NSNNSoTienThuHoi")
        public double nSNNSoTienThuHoi = 0;

        @JsonProperty("ToChucSoTienThuHoi")
        public double toChucSoTienThuHoi = 0;

        @JsonProperty("TongSoTienThuHoi")
        public double tongSoTienThuHoi = 0;

        @JsonProperty("SoTienKNXLKhac")
        public double soTienKNXLKhac = 0;

        @JsonProperty("QDXPHCToChuc")
        public long qDXPHCToChuc = 0;

        @JsonProperty("QDXPHCCaNhan")
        public long qDXPHCCaNhan = 0;

        @JsonProperty("SoQuyetDinhXPHC")
        public long soQuyetDinhXPHC = 0;

        @JsonProperty("QDPhatTienToChuc")
        public long qDPhatTienToChuc = 0;

        @JsonProperty("QDPhatTienCaNhan")
        public long qDPhatTienCaNhan = 0;

        @JsonProperty("QuyetDinhPhatTien")
        public long quyetDinhPhatTien = 0;

        @JsonProperty("QDKhongPhatTienToChuc")
        public long qDKhongPhatTienToChuc = 0;

        @JsonProperty("QDKhongPhatTienCaNhan")
        public long qDKhongPhatTienCaNhan = 0;

        @JsonProperty("QuyetDinhKhongPhatTien")
        public long quyetDinhKhongPhatTien = 0;

        @JsonProperty("CQDTVuViec")
        public long cQDTVuViec = 0;

        @JsonProperty("CQDTDoiTuong")
        public long cQDTDoiTuong = 0;

    }
}
