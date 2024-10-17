package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiDeXuatKienNghi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLoaiDeXuatKienNghiService {
    Optional<RDBMSLoaiDeXuatKienNghi> findById(String id);

    void deleteLoaiDeXuatKienNghi(RDBMSLoaiDeXuatKienNghi rdbmsLoaiDeXuatKienNghi);

    RDBMSLoaiDeXuatKienNghi updateLoaiDeXuatKienNghi(RDBMSLoaiDeXuatKienNghi rdbmsLoaiDeXuatKienNghi);

    Map<String, RDBMSLoaiDeXuatKienNghi> update(Map<String, RDBMSLoaiDeXuatKienNghi> map);

    Page<RDBMSLoaiDeXuatKienNghi> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLoaiDeXuatKienNghi> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSLoaiDeXuatKienNghi> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}