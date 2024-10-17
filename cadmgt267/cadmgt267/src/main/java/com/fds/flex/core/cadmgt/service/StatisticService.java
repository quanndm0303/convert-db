package com.fds.flex.core.cadmgt.service;

public interface StatisticService {
    //Đang đề xuất lãnh đạo Bộ giao kiểm tra điều kiện thụ lý:  C_TinhTrangXuLyDonThu = 07 và KiemTraThuLy = NULL
    Long deXuatLanhBaoBoGiaoKiemTraThuLy(Integer namThongKe);

    //Đang chờ để kiểm tra điều kiện: KiemTraThuLy có C_TrangThaiHoatDongThanhTra = Chuẩn bị chương trình
    Long dangChoKiemTraDieuKien(Integer namThongKe);

    //Đang kiểm tra điều kiện thụ lý:  KiemTraThuLy có C_TrangThaiHoatDongThanhTra = 02, 04 (Đang thực hiện, Báo cáo kết quả)
    Long dangKiemTraDieuKienThuLy(Integer namThongKe);

    //Tố cáo sai, không xác định được hành vi vi phạm --->  T_XuLyDonThu.TinhTrangXuLyDonThu = 09 (tình trạng xử lý của đơn thư là không đủ điều kiện thụ lý) và T_VuViecDonThu.PhanTichKetQuaKNTC = 06 (kết quả phân tích vụ việc là tố cáo sai)
    Long toCaoSaiKhongXacDinhDuocHanhViViPham(Integer namThongKe);

    //Tố cáo nặc danh ---> T_XuLyDonThu.TinhTrangXuLyDonThu = 09 (tình trạng xử lý của đơn thư là không đủ điều kiện thụ lý) và T_XuLyDonThu.TenNguoiDaiDien = NULL
    Long toCaoNacDanh(Integer namThongKe);

    //Không xác định được người viết đơn tố cáo ---> Bằng Tổng những T_XuLyDonThu có C_TinhTrangXuLyDonThu=09 trừ đi số  đơn thư "Tố cáo sai, không xác định được hành vi vi phạm" và "Tố cáo nặc danh"
    Long khongXacDinhDuocNguoiVietDonToCao(Integer namThongKe);
}
