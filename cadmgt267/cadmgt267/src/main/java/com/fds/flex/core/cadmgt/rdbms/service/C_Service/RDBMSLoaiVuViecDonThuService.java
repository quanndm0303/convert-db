package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSLoaiVuViecDonThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLoaiVuViecDonThuService {

    Optional<RDBMSLoaiVuViecDonThu> findById(String id);

    void deleteLoaiVuViecDonThu(RDBMSLoaiVuViecDonThu rdbmsLoaiVuViecDonThu);

    RDBMSLoaiVuViecDonThu updateLoaiVuViecDonThu(RDBMSLoaiVuViecDonThu rdbmsLoaiVuViecDonThu);

    Map<String, RDBMSLoaiVuViecDonThu> update(Map<String, RDBMSLoaiVuViecDonThu> map);

    Page<RDBMSLoaiVuViecDonThu> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLoaiVuViecDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSLoaiVuViecDonThu> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<RDBMSLoaiVuViecDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
