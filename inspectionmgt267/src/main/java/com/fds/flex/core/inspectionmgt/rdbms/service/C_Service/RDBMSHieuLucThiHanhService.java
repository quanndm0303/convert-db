package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHieuLucThiHanh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSHieuLucThiHanhService {
    Optional<RDBMSHieuLucThiHanh> findById(String id);

    void deleteHieuLucThiHanh(RDBMSHieuLucThiHanh rdbmsHieuLucThiHanh);

    RDBMSHieuLucThiHanh updateHieuLucThiHanh(RDBMSHieuLucThiHanh rdbmsHieuLucThiHanh);

    Map<String, RDBMSHieuLucThiHanh> update(Map<String, RDBMSHieuLucThiHanh> map);

    Page<RDBMSHieuLucThiHanh> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSHieuLucThiHanh> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSHieuLucThiHanh> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}