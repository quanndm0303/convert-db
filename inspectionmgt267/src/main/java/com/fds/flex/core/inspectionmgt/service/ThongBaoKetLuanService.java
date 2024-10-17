package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.Query_Model.ThongBaoKetLuanQueryModel;
import com.fds.flex.core.inspectionmgt.entity.T_Model.ThongBaoKetLuan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ThongBaoKetLuanService {
    Optional<ThongBaoKetLuan> findById(String id);

    void deleteThongBaoKetLuan(ThongBaoKetLuan thongBaoKetLuan);

    Map<String, Long> thongKeTrangThaiKetLuanTKT(String[] loaiThongBaoKetLuan_MaMuc, Integer namThongKe, String[] trangThaiTheoDoiArr);

    Map<String, Long> thongKeLoaiDeXuatKienNghi(String loaiThongBaoKetLuan_MaMuc, Integer namThongKe);

    ThongBaoKetLuan updateThongBaoKetLuan(ThongBaoKetLuan thongBaoKetLuan);

    Map<String, ThongBaoKetLuan> update(Map<String, ThongBaoKetLuan> map);

    Page<ThongBaoKetLuan> filter(ThongBaoKetLuanQueryModel thongBaoKetLuanQueryModel, Pageable pageable);

    List<ThongBaoKetLuan> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    List<ThongBaoKetLuan> findByDTKT_TTDL(String hoatDongThanhTra, String trangThaiDuLieu_MaMuc);

    Optional<ThongBaoKetLuan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<ThongBaoKetLuan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);
}
