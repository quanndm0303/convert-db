package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSCheDoTiepCongDan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSCheDoTiepCongDanService {

    Optional<RDBMSCheDoTiepCongDan> findById(String id);

    void deleteCheDoTiepCongDan(RDBMSCheDoTiepCongDan rdbmsCheDoTiepCongDan);

    RDBMSCheDoTiepCongDan updateCheDoTiepCongDan(RDBMSCheDoTiepCongDan rdbmsCheDoTiepCongDan);

    Map<String, RDBMSCheDoTiepCongDan> update(Map<String, RDBMSCheDoTiepCongDan> map);

    Page<RDBMSCheDoTiepCongDan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSCheDoTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSCheDoTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
