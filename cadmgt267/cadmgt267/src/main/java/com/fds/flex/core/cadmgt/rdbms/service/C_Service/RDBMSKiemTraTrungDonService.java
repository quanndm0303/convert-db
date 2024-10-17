package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKiemTraTrungDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSKiemTraTrungDonService {
    Optional<RDBMSKiemTraTrungDon> findById(String id);

    void deleteKiemTraTrungDon(RDBMSKiemTraTrungDon rdbmsKiemTraTrungDon);

    RDBMSKiemTraTrungDon updateKiemTraTrungDon(RDBMSKiemTraTrungDon rdbmsKiemTraTrungDon);

    Map<String, RDBMSKiemTraTrungDon> update(Map<String, RDBMSKiemTraTrungDon> map);

    Page<RDBMSKiemTraTrungDon> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSKiemTraTrungDon> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSKiemTraTrungDon> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
