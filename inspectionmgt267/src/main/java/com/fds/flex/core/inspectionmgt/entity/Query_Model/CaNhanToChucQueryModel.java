package com.fds.flex.core.inspectionmgt.entity.Query_Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CaNhanToChucQueryModel {
    String keyCache;
    String keyword;
    String loaiDanhSach;
    String loaiDoiTuongCNTC;
    String soGiay;
    String maDinhDanh;
    String tuCachPhapLy_MaMuc;
    String diaChi_TinhThanh_MaMuc;
    String diaChi_HuyenQuan_MaMuc;
    String diaChi_XaPhuong_MaMuc;
    String[] trangThaiDuLieu_MaMuc;
}
