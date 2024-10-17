package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSHinhThucNhanDonThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSHinhThucNhanDonThuService {

    Optional<RDBMSHinhThucNhanDonThu> findById(String id);

    void deleteHinhThucNhanDonThu(RDBMSHinhThucNhanDonThu rdbmsHinhThucNhanDonThu);

    RDBMSHinhThucNhanDonThu updateHinhThucNhanDonThu(RDBMSHinhThucNhanDonThu rdbmsHinhThucNhanDonThu);

    Map<String, RDBMSHinhThucNhanDonThu> update(Map<String, RDBMSHinhThucNhanDonThu> map);

    Page<RDBMSHinhThucNhanDonThu> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSHinhThucNhanDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSHinhThucNhanDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
