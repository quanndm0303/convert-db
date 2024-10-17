package com.fds.flex.core.cadmgt.rdbms.service.T_Service;

import com.fds.flex.core.cadmgt.entity.Query_Model.VuViecDonThuQueryModel;
import com.fds.flex.core.cadmgt.entity.T_Model.VuViecDonThu;
import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSVuViecDonThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSVuViecDonThuService {
    Optional<RDBMSVuViecDonThu> findById(String id);

    void deleteVuViecDonThu(RDBMSVuViecDonThu rdbmsVuViecDonThu);

    RDBMSVuViecDonThu updateVuViecDonThu(RDBMSVuViecDonThu rdbmsVuViecDonThu);

    Map<String, RDBMSVuViecDonThu> update(Map<String, RDBMSVuViecDonThu> map);

    Page<RDBMSVuViecDonThu> filter(VuViecDonThuQueryModel vuViecDonThuQueryModel, Pageable pageable);

    List<RDBMSVuViecDonThu> filterWithoutPageable(VuViecDonThuQueryModel vuViecDonThuQueryModel);

    List<RDBMSVuViecDonThu> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSVuViecDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<RDBMSVuViecDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSVuViecDonThu> findByHSNV_TTDL(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    Map<String, Long> thongKeTinhTrangXuLyVuViec(String loaiDanhSach, String[] tinhTrangXuLyDonThu, Integer namThongKe_Int, String loaiVuViec);

    Map<String, Long> thongKeXuLyTinBanDau(Integer namThongKe, String[] tinhTrangXuLyDonThu, String loaiVuViecDonThu);

    Map<String, Long> thongKeKetQuaKiemTraDieuKienThuLy(Integer namThongKe, String[] tinhTrangXuLyDonThu);

    Map<String, Long> thongKeLoaiVuViecDonThu(Integer namThongKe);

    Map<String, Long> thongKeQuyetDinhGiaiQuyetKhieuNai(Integer namThongKe, String loaiVuViecDonThu);

    Map<String, Long> thongKeTheoKienNghiXuLyKNTC(Integer namThongKe, String loaiVuViecDonThu);

    Map<String, Long> thongKeKienNghiXuLy(Integer namThongKe, String loaiVuViecDonThu);

    Map<String, Long> thongKeTheoPhanTichKetQuaKNTC(Integer namThongKe, String loaiVuViecDonThu);

}
