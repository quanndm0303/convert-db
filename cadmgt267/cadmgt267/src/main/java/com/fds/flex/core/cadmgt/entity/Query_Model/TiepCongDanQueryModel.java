package com.fds.flex.core.cadmgt.entity.Query_Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TiepCongDanQueryModel {
    String keyCache;
    String keyword;
    String ngayTiepCongDan_TuNgay;
    String ngayTiepCongDan_DenNgay;
    String nguoiTiepCongDan_MaDinhDanh;
    String[] cheDoTiepCongDan_MaMuc;
    String tinhTrangTiepCongDan_MaMuc;
    String loaiVuViecDonThu_MaMuc;
    String doiTuongVuViec_TenGoi;
    String nguoiDuocTiep_TenGoi;
    String vuViecDonThu_DiaBanXuLy_MaMuc;
    String[] ketQuaTiepCongDan_MaMuc;
    String[] trangThaiDuLieu_MaMuc;

}
