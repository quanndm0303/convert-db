package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.Data_Model.KetQuaXuLyDonModel;
import com.fds.flex.core.cadmgt.entity.Query_Model.XuLyDonThuQueryModel;
import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface XuLyDonThuService {

    Optional<XuLyDonThu> findById(String id);

    void deleteXuLyDonThu(XuLyDonThu xuLyDonThu);

    XuLyDonThu updateXuLyDonThu(XuLyDonThu xuLyDonThu);

    Map<String, XuLyDonThu> update(Map<String, XuLyDonThu> map);

    Page<XuLyDonThu> filter(XuLyDonThuQueryModel xuLyDonThuQueryModel, Pageable pageable);

    List<XuLyDonThu> filterWithoutPageable(XuLyDonThuQueryModel xuLyDonThuQueryModel);

    List<XuLyDonThu> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<XuLyDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    Optional<XuLyDonThu> findByMaDonThuAndTrangThaiDuLieu(String maDonThu, String trangThaiDuLieu_MaMuc);

    List<XuLyDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    Map<String, Long> thongKeTinhTrangXuLyDonThu(Integer namThongKe);

    Map<String, Long> thongKeTrangThaiGiaiQuyetDon();

    Map<String, Long> thongKeLoaiVuViecDonThu(Integer namThongKe);

    Map<String, Long> thongKePhamViThamQuyen(Integer namThongKe);

    Map<String, Long> thongKeDonTrungNoiDung(Integer namThongKe);

    Map<String, Long> thongKeCoQuanCoThamQuyen(Integer namThongKe, String coQuanThamQuyen, String tinhTrangXuLyDonThu);

    Map<String, Long> thongKeCoQuanKhongCoThamQuyen(Integer namThongKe, String coQuanThamQuyen);

    List<KetQuaXuLyDonModel> thongKeKetQuaXuLyDon(Integer namThongKe, String tinhTrangXuLyDonThu);

    Map<String, Long> thongKeKetQuaKiemTraDieuKienThuLy(Integer namThongKe, String[] tinhTrangXuLyDonThu);

    Long countDonThuNhacDanh(Integer namThongKe, boolean isDanh);

    List<XuLyDonThu> findByVuViecDonThu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    Long countMaDonThu(String regex);

    Optional<XuLyDonThu> findByTiepCongDanAndTrangThaiDuLieu(String maDinhDanhTiepCongDan, String trangThaiDuLieu_MaMuc);
    List<XuLyDonThu> findByTiepCongDanAndTrangThaiDuLieu(String[] maDinhDanhTiepCongDan, String trangThaiDuLieu_MaMuc);
}
