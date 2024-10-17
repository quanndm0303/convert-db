package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSPhanTichKetQuaKNTC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSPhanTichKetQuaKNTCService {

    Optional<RDBMSPhanTichKetQuaKNTC> findById(String id);

    void deletePhanTichKetQuaKNTC(RDBMSPhanTichKetQuaKNTC rdbmsPhanTichKetQuaKNTC);

    RDBMSPhanTichKetQuaKNTC updatePhanTichKetQuaKNTC(RDBMSPhanTichKetQuaKNTC rdbmsPhanTichKetQuaKNTC);

    Map<String, RDBMSPhanTichKetQuaKNTC> update(Map<String, RDBMSPhanTichKetQuaKNTC> map);

    Page<RDBMSPhanTichKetQuaKNTC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSPhanTichKetQuaKNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSPhanTichKetQuaKNTC> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<RDBMSPhanTichKetQuaKNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
