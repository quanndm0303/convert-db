package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSChucDanhDoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSChucDanhDoanService {
    Optional<RDBMSChucDanhDoan> findById(String id);

    void deleteChucDanhDoan(RDBMSChucDanhDoan rdbmsChucDanhDoan);

    RDBMSChucDanhDoan updateChucDanhDoan(RDBMSChucDanhDoan rdbmsChucDanhDoan);

    Map<String, RDBMSChucDanhDoan> update(Map<String, RDBMSChucDanhDoan> map);

    Page<RDBMSChucDanhDoan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSChucDanhDoan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSChucDanhDoan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}