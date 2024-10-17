package com.fds.flex.core.inspectionmgt.entity.Query_Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KhaoSatNamTinhHinhQueryModel {
    String keyword;
    String nhiemVuCongViec_NamVanBan;
    String nhiemVuCongViec_NamKeHoach;
    String loaiCheDoThanhTra_MaMuc;
    String coQuanThucHien_MaDinhDanh;
    String donViThucHien_MaDinhDanh;
    String trangThaiKhaoSat_MaMuc;
    String[] trangThaiDuLieu_MaMuc;
    String keyCache;
}
