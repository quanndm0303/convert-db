package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiHoatDongThanhTra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLoaiHoatDongThanhTraService {
    Optional<RDBMSLoaiHoatDongThanhTra> findById(String id);

    void deleteLoaiHoatDongThanhTra(RDBMSLoaiHoatDongThanhTra rdbmsLoaiHoatDongThanhTra);

    RDBMSLoaiHoatDongThanhTra updateLoaiHoatDongThanhTra(RDBMSLoaiHoatDongThanhTra rdbmsLoaiHoatDongThanhTra);

    Map<String, RDBMSLoaiHoatDongThanhTra> update(Map<String, RDBMSLoaiHoatDongThanhTra> map);

    Page<RDBMSLoaiHoatDongThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLoaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSLoaiHoatDongThanhTra> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<RDBMSLoaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}