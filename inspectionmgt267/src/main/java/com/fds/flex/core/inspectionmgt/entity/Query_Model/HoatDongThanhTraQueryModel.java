package com.fds.flex.core.inspectionmgt.entity.Query_Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HoatDongThanhTraQueryModel {

    String keyword;
    String loaiCheDoThanhTra_MaMuc;
    String nhiemVuCongViec_NamKeHoach;
    String coQuanBanHanh;
    String truongDoan;
    String trangThaiHoatDongThanhTra_MaMuc;
    String hoSoNghiepVu_SoDangKy;
    String hoSoNghiepVu_TrangThaiHoSoNghiepVu_MaMuc;
    String[] loaiHoSoNghiepVu;
    String[] loaiHoatDongThanhTra_MaMuc;
    String[] trangThaiDuLieu_MaMuc;
    String keyCache;
}
