package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLinhVucChuyenNganh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLinhVucChuyenNganhService {
    Optional<RDBMSLinhVucChuyenNganh> findById(String id);

    void deleteLinhVucChuyenNganh(RDBMSLinhVucChuyenNganh rdbmsLinhVucChuyenNganh);

    RDBMSLinhVucChuyenNganh updateLinhVucChuyenNganh(RDBMSLinhVucChuyenNganh rdbmsLinhVucChuyenNganh);

    Map<String, RDBMSLinhVucChuyenNganh> update(Map<String, RDBMSLinhVucChuyenNganh> map);

    Page<RDBMSLinhVucChuyenNganh> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLinhVucChuyenNganh> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSLinhVucChuyenNganh> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}