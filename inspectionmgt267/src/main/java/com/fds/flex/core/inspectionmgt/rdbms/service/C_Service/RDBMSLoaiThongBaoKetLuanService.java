package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiThongBaoKetLuan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLoaiThongBaoKetLuanService {
    Optional<RDBMSLoaiThongBaoKetLuan> findById(String id);

    void deleteLoaiThongBaoKetLuan(RDBMSLoaiThongBaoKetLuan rdbmsLoaiThongBaoKetLuan);

    RDBMSLoaiThongBaoKetLuan updateLoaiThongBaoKetLuan(RDBMSLoaiThongBaoKetLuan rdbmsLoaiThongBaoKetLuan);

    Map<String, RDBMSLoaiThongBaoKetLuan> update(Map<String, RDBMSLoaiThongBaoKetLuan> map);

    Page<RDBMSLoaiThongBaoKetLuan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLoaiThongBaoKetLuan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSLoaiThongBaoKetLuan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}