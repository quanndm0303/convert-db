package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHanhViVPHC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSHanhViVPHCService {
    Optional<RDBMSHanhViVPHC> findById(String id);

    void deleteHanhViVPHC(RDBMSHanhViVPHC rdbmsHanhViVPHC);

    RDBMSHanhViVPHC updateHanhViVPHC(RDBMSHanhViVPHC rdbmsHanhViVPHC);

    Map<String, RDBMSHanhViVPHC> update(Map<String, RDBMSHanhViVPHC> map);

    Page<RDBMSHanhViVPHC> filter(String keyword, String nhomRDBMSHanhViVPHC, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}