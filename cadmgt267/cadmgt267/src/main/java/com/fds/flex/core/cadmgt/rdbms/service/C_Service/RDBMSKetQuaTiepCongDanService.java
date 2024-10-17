package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKetQuaTiepCongDan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSKetQuaTiepCongDanService {
    Optional<RDBMSKetQuaTiepCongDan> findById(String id);

    void deleteKetQuaTiepCongDan(RDBMSKetQuaTiepCongDan rdbmsKetQuaTiepCongDan);

    RDBMSKetQuaTiepCongDan updateKetQuaTiepCongDan(RDBMSKetQuaTiepCongDan rdbmsKetQuaTiepCongDan);

    Map<String, RDBMSKetQuaTiepCongDan> update(Map<String, RDBMSKetQuaTiepCongDan> map);

    Page<RDBMSKetQuaTiepCongDan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSKetQuaTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSKetQuaTiepCongDan> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<RDBMSKetQuaTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
