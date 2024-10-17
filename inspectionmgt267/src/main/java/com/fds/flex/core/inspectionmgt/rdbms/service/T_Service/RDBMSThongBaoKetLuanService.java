package com.fds.flex.core.inspectionmgt.rdbms.service.T_Service;

import com.fds.flex.core.inspectionmgt.entity.Query_Model.ThongBaoKetLuanQueryModel;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSThongBaoKetLuan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSThongBaoKetLuanService {

    Optional<RDBMSThongBaoKetLuan> findById(String id);

    void deleteThongBaoKetLuan(RDBMSThongBaoKetLuan thongBaoKetLuan);

    Map<String, Long> thongKeTrangThaiKetLuanTKT(String loaiThongBaoKetLuan_MaMuc, Integer namThongKe);

    Map<String, Long> thongKeLoaiDeXuatKienNghi(String loaiThongBaoKetLuan_MaMuc, Integer namThongKe);

    RDBMSThongBaoKetLuan updateThongBaoKetLuan(RDBMSThongBaoKetLuan thongBaoKetLuan);

    Map<String, RDBMSThongBaoKetLuan> update(Map<String, RDBMSThongBaoKetLuan> map);

    Page<RDBMSThongBaoKetLuan> filter(ThongBaoKetLuanQueryModel thongBaoKetLuanQueryModel, Pageable pageable);

    List<RDBMSThongBaoKetLuan> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSThongBaoKetLuan> findByDTKT_TTDL(String hoatDongThanhTra, String trangThaiDuLieu_MaMuc);

    Optional<RDBMSThongBaoKetLuan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<RDBMSThongBaoKetLuan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

}
