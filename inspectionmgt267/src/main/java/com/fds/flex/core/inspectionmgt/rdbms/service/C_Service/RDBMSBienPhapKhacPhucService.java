package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSBienPhapKhacPhuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSBienPhapKhacPhucService {
    Optional<RDBMSBienPhapKhacPhuc> findById(String id);

    void deleteBienPhapKhacPhuc(RDBMSBienPhapKhacPhuc rdbmsBienPhapKhacPhuc);

    RDBMSBienPhapKhacPhuc updateBienPhapKhacPhuc(RDBMSBienPhapKhacPhuc rdbmsBienPhapKhacPhuc);

    Map<String, RDBMSBienPhapKhacPhuc> update(Map<String, RDBMSBienPhapKhacPhuc> map);

    Page<RDBMSBienPhapKhacPhuc> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSBienPhapKhacPhuc> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSBienPhapKhacPhuc> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}