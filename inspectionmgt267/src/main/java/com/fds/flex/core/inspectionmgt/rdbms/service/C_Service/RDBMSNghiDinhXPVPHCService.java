package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSNghiDinhXPVPHC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSNghiDinhXPVPHCService {
    Optional<RDBMSNghiDinhXPVPHC> findById(String id);

    void deleteNghiDinhXPVPHC(RDBMSNghiDinhXPVPHC rdbmsNghiDinhXPVPHC);

    RDBMSNghiDinhXPVPHC updateNghiDinhXPVPHC(RDBMSNghiDinhXPVPHC rdbmsNghiDinhXPVPHC);

    Map<String, RDBMSNghiDinhXPVPHC> update(Map<String, RDBMSNghiDinhXPVPHC> map);

    Page<RDBMSNghiDinhXPVPHC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSNghiDinhXPVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSNghiDinhXPVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}