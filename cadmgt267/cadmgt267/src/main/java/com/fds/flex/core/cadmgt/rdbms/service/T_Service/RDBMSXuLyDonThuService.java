package com.fds.flex.core.cadmgt.rdbms.service.T_Service;

import com.fds.flex.core.cadmgt.entity.Data_Model.KetQuaXuLyDonModel;
import com.fds.flex.core.cadmgt.entity.Query_Model.XuLyDonThuQueryModel;
import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSXuLyDonThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSXuLyDonThuService {

    Optional<RDBMSXuLyDonThu> findById(String id);

    void deleteXuLyDonThu(RDBMSXuLyDonThu rdbmsXuLyDonThu);

    RDBMSXuLyDonThu updateXuLyDonThu(RDBMSXuLyDonThu rdbmsXuLyDonThu);

    Map<String, RDBMSXuLyDonThu> update(Map<String, RDBMSXuLyDonThu> map);

    Page<RDBMSXuLyDonThu> filter(XuLyDonThuQueryModel xuLyDonThuQueryModel, Pageable pageable);

    List<RDBMSXuLyDonThu> filterWithoutPageable(XuLyDonThuQueryModel xuLyDonThuQueryModel);

    List<RDBMSXuLyDonThu> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSXuLyDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    Optional<RDBMSXuLyDonThu> findByMaDonThuAndTrangThaiDuLieu(String maDonThu, String trangThaiDuLieu_MaMuc);

    List<RDBMSXuLyDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

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

    List<RDBMSXuLyDonThu> findByVuViecDonThu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    Long countMaDonThu(String regex);

    Optional<RDBMSXuLyDonThu> findByTiepCongDanAndTrangThaiDuLieu(String maDinhDanhTiepCongDan, String trangThaiDuLieu_MaMuc);
    List<RDBMSXuLyDonThu> findByTiepCongDanAndTrangThaiDuLieu(String[] maDinhDanhTiepCongDan, String trangThaiDuLieu_MaMuc);
}
