package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTrangThaiGiaiQuyetDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSTrangThaiGiaiQuyetDonService {

    Optional<RDBMSTrangThaiGiaiQuyetDon> findById(String id);

    void deleteTrangThaiGiaiQuyetDon(RDBMSTrangThaiGiaiQuyetDon rdbmsTrangThaiGiaiQuyetDon);

    RDBMSTrangThaiGiaiQuyetDon updateTrangThaiGiaiQuyetDon(RDBMSTrangThaiGiaiQuyetDon rdbmsTrangThaiGiaiQuyetDon);

    Map<String, RDBMSTrangThaiGiaiQuyetDon> update(Map<String, RDBMSTrangThaiGiaiQuyetDon> map);

    Page<RDBMSTrangThaiGiaiQuyetDon> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSTrangThaiGiaiQuyetDon> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSTrangThaiGiaiQuyetDon> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<RDBMSTrangThaiGiaiQuyetDon> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
