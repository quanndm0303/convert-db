package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSLoaiChiTietVuViec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLoaiChiTietVuViecService {
    Optional<RDBMSLoaiChiTietVuViec> findById(String id);

    void deleteLoaiChiTietVuViec(RDBMSLoaiChiTietVuViec rdbmsLoaiChiTietVuViec);

    RDBMSLoaiChiTietVuViec updateLoaiChiTietVuViec(RDBMSLoaiChiTietVuViec rdbmsLoaiChiTietVuViec);

    Map<String, RDBMSLoaiChiTietVuViec> update(Map<String, RDBMSLoaiChiTietVuViec> map);

    Page<RDBMSLoaiChiTietVuViec> filter(String keyword, String loaiVuViecDonThu_MaMuc, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLoaiChiTietVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSLoaiChiTietVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
