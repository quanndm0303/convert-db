package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSNhomHanhViVPHC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSNhomHanhViVPHCService {
    Optional<RDBMSNhomHanhViVPHC> findById(String id);

    void deleteNhomHanhViVPHC(RDBMSNhomHanhViVPHC rdbmsNhomHanhViVPHC);

    RDBMSNhomHanhViVPHC updateNhomHanhViVPHC(RDBMSNhomHanhViVPHC rdbmsNhomHanhViVPHC);

    Map<String, RDBMSNhomHanhViVPHC> update(Map<String, RDBMSNhomHanhViVPHC> map);

    Page<RDBMSNhomHanhViVPHC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSNhomHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSNhomHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}