package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTinhTrangXuLyDonThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSTinhTrangXuLyDonThuService {

    Optional<RDBMSTinhTrangXuLyDonThu> findById(String id);

    void deleteTinhTrangXuLyDonThu(RDBMSTinhTrangXuLyDonThu rdbmsTinhTrangXuLyDonThu);

    RDBMSTinhTrangXuLyDonThu updateTinhTrangXuLyDonThu(RDBMSTinhTrangXuLyDonThu rdbmsTinhTrangXuLyDonThu);

    Map<String, RDBMSTinhTrangXuLyDonThu> update(Map<String, RDBMSTinhTrangXuLyDonThu> map);

    Page<RDBMSTinhTrangXuLyDonThu> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSTinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSTinhTrangXuLyDonThu> findBySuDungChoXuLyDonThu_TTDL();

    List<RDBMSTinhTrangXuLyDonThu> findBySuDungChoVuViecDonThu_TTDL();

    Optional<RDBMSTinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
