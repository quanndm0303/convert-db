package com.fds.flex.core.cadmgt.entity.Query_Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VuViecDonThuQueryModel {
    String keyword;
    String loaiDanhSach;
    String loaiVuViecDonThu_MaMuc;
    String ngayTiepNhan_TuNgay;
    String ngayTiepNhan_DenNgay;
    String tinhTrangXuLyVuViec_MaMuc;
    String doiTuongVuViec_LoaiDoiTuongCNTC_MaMuc;
    String phanTichKetQuaKNTC_MaMuc;
    String kienNghiXuLyKNTC_MaMuc;
    String hoSoNghiepVu_SoDangKy;
    String hoSoNghiepVu_TrangThaiHoSoNghiepVu_MaMuc;
    String doiTuongVuViec_MaDinhDanh;
    String linhVucChuyenNganh_MaMuc;
    String coQuanQuanLy_MaDinhDanh;
    String donViTheoDoi_MaDinhDanh;
    String canBoXuLy_MaDinhDanh;
    String xuLyDonThu_MaDinhDanh;
    String[] trangThaiDuLieu_MaMuc;
    String keyCache;

}
