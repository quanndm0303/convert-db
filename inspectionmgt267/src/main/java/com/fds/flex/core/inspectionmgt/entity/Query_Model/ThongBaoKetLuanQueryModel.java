package com.fds.flex.core.inspectionmgt.entity.Query_Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThongBaoKetLuanQueryModel {
    String keyCache;
    String keyword;
    String doiTuongKetLuan_MaDinhDanh;
    String donViChuTri_MaDinhDanh;
    String canBoTheoDoi_MaDinhDanh;
    String ngayVanBan_TuNgay;
    String ngayVanBan_DenNgay;
    String ngayVanBan;
    String coQuanBanHanh_MaDinhDanh;
    String[] loaiThongBaoKetLuan_MaMuc;
    String[] trangThaiTheoDoi_MaMuc;
    String[] trangThaiDuLieu_MaMuc;
}
