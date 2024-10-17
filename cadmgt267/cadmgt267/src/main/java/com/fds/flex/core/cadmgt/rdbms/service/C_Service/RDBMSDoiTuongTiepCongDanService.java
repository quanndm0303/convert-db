package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSDoiTuongTiepCongDan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSDoiTuongTiepCongDanService {
    Optional<RDBMSDoiTuongTiepCongDan> findById(String id);

    void deleteDoiTuongTiepCongDan(RDBMSDoiTuongTiepCongDan rdbmsDoiTuongTiepCongDan);

    RDBMSDoiTuongTiepCongDan updateDoiTuongTiepCongDan(RDBMSDoiTuongTiepCongDan rdbmsDoiTuongTiepCongDan);

    Map<String, RDBMSDoiTuongTiepCongDan> update(Map<String, RDBMSDoiTuongTiepCongDan> map);

    Page<RDBMSDoiTuongTiepCongDan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSDoiTuongTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSDoiTuongTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
