package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSDoiTuongDuocTiep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSDoiTuongDuocTiepService {
    Optional<RDBMSDoiTuongDuocTiep> findById(String id);

    void deleteDoiTuongDuocTiep(RDBMSDoiTuongDuocTiep rdbmsDoiTuongDuocTiep);

    RDBMSDoiTuongDuocTiep updateDoiTuongDuocTiep(RDBMSDoiTuongDuocTiep rdbmsDoiTuongDuocTiep);

    Map<String, RDBMSDoiTuongDuocTiep> update(Map<String, RDBMSDoiTuongDuocTiep> map);

    Page<RDBMSDoiTuongDuocTiep> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSDoiTuongDuocTiep> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSDoiTuongDuocTiep> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
