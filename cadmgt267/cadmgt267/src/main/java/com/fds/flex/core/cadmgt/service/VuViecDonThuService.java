package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.Query_Model.VuViecDonThuQueryModel;
import com.fds.flex.core.cadmgt.entity.T_Model.VuViecDonThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VuViecDonThuService {


    Optional<VuViecDonThu> findById(String id);

    void deleteVuViecDonThu(VuViecDonThu vuViecDonThu);

    VuViecDonThu updateVuViecDonThu(VuViecDonThu vuViecDonThu);

    Map<String, VuViecDonThu> update(Map<String, VuViecDonThu> map);

    Page<VuViecDonThu> filter(VuViecDonThuQueryModel vuViecDonThuQueryModel, Pageable pageable);

    List<VuViecDonThu> filterWithoutPageable(VuViecDonThuQueryModel vuViecDonThuQueryModel);

    List<VuViecDonThu> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<VuViecDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<VuViecDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    List<VuViecDonThu> findByHSNV_TTDL(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    Map<String, Long> thongKeTinhTrangXuLyVuViec(String loaiDanhSach, String[] tinhTrangXuLyDonThu, Integer namThongKe_Int, String loaiVuViec);

    Map<String, Long> thongKeXuLyTinBanDau(Integer namThongKe, String[] tinhTrangXuLyDonThu, String loaiVuViecDonThu);

    Map<String, Long> thongKeKetQuaKiemTraDieuKienThuLy(Integer namThongKe, String[] tinhTrangXuLyDonThu);

    Map<String, Long> thongKeLoaiVuViecDonThu(Integer namThongKe);

    Map<String, Long> thongKeQuyetDinhGiaiQuyetKhieuNai(Integer namThongKe, String loaiVuViecDonThu);

    Map<String, Long> thongKeTheoKienNghiXuLyKNTC(Integer namThongKe, String loaiVuViecDonThu);

    Map<String, Long> thongKeKienNghiXuLy(Integer namThongKe, String loaiVuViecDonThu);

    Map<String, Long> thongKeTheoPhanTichKetQuaKNTC(Integer namThongKe, String loaiVuViecDonThu);


}
