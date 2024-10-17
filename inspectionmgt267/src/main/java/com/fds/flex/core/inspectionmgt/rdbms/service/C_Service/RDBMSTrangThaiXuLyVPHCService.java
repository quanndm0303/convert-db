package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiXuLyVPHC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSTrangThaiXuLyVPHCService {
    Optional<RDBMSTrangThaiXuLyVPHC> findById(String id);

    List<RDBMSTrangThaiXuLyVPHC> findByTrangThaiDuLieu(String[] trangThaiDulieu);

    void deleteTrangThaiXuLyVPHC(RDBMSTrangThaiXuLyVPHC rdbmsTrangThaiXuLyVPHC);

    RDBMSTrangThaiXuLyVPHC updateTrangThaiXuLyVPHC(RDBMSTrangThaiXuLyVPHC rdbmsTrangThaiXuLyVPHC);

    Map<String, RDBMSTrangThaiXuLyVPHC> update(Map<String, RDBMSTrangThaiXuLyVPHC> map);

    Page<RDBMSTrangThaiXuLyVPHC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSTrangThaiXuLyVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSTrangThaiXuLyVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}