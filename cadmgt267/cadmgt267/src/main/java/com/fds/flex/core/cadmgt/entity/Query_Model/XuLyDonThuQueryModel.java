package com.fds.flex.core.cadmgt.entity.Query_Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class XuLyDonThuQueryModel {
    String keyword;
    String tieuDeDonThu;
    String[] tinhTrangXuLyDonThu_MaMuc;
    String ngayTiepNhan_TuNgay;
    String ngayTiepNhan_DenNgay;
    String hinhThucNhanDonThu_MaMuc;
    String loaiDoiTuongDungTen_MaMuc;
    String loaiVuViecDonThu_MaMuc;
    String linhVucChuyenNganh_MaMuc;
    String coQuanQuanLy_MaDinhDanh;
    String donViTheoDoi_MaDinhDanh;
    String canBoXuLy_MaDinhDanh;
    String tiepCongDan_MaDinhDanh;
    String vuViecDonThu_MaDinhDanh;
    String vuViecDonThu_NoiDungVuViec;
    String trangThaiGiaiQuyetDon_MaMuc;
    String[] trangThaiDuLieu_MaMuc;
    String keyCache;
}
