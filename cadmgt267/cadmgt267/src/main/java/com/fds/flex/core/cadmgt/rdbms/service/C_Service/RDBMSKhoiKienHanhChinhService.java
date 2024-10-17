package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKhoiKienHanhChinh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSKhoiKienHanhChinhService {

    Optional<RDBMSKhoiKienHanhChinh> findById(String id);

    void deleteKhoiKienHanhChinh(RDBMSKhoiKienHanhChinh rdbmsKhoiKienHanhChinh);

    RDBMSKhoiKienHanhChinh updateKhoiKienHanhChinh(RDBMSKhoiKienHanhChinh rdbmsKhoiKienHanhChinh);

    Map<String, RDBMSKhoiKienHanhChinh> update(Map<String, RDBMSKhoiKienHanhChinh> map);

    Page<RDBMSKhoiKienHanhChinh> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSKhoiKienHanhChinh> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSKhoiKienHanhChinh> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
