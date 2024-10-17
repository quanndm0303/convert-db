package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiTheoDoi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSTrangThaiTheoDoiService {
    Optional<RDBMSTrangThaiTheoDoi> findById(String id);

    void deleteTrangThaiTheoDoi(RDBMSTrangThaiTheoDoi rdbmsTrangThaiTheoDoi);

    RDBMSTrangThaiTheoDoi updateTrangThaiTheoDoi(RDBMSTrangThaiTheoDoi rdbmsTrangThaiTheoDoi);

    Map<String, RDBMSTrangThaiTheoDoi> update(Map<String, RDBMSTrangThaiTheoDoi> map);

    Page<RDBMSTrangThaiTheoDoi> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSTrangThaiTheoDoi> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSTrangThaiTheoDoi> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);

    List<RDBMSTrangThaiTheoDoi> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);
}