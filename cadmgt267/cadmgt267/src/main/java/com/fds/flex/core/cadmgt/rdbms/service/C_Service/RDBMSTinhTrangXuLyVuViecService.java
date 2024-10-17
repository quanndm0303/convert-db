package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTinhTrangXuLyVuViec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSTinhTrangXuLyVuViecService {
    Optional<RDBMSTinhTrangXuLyVuViec> findById(String id);

    void deleteTinhTrangXuLyVuViec(RDBMSTinhTrangXuLyVuViec rdbmsTinhTrangXuLyVuViec);

    RDBMSTinhTrangXuLyVuViec updateTinhTrangXuLyVuViec(RDBMSTinhTrangXuLyVuViec rdbmsTinhTrangXuLyVuViec);

    Map<String, RDBMSTinhTrangXuLyVuViec> update(Map<String, RDBMSTinhTrangXuLyVuViec> map);

    Page<RDBMSTinhTrangXuLyVuViec> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSTinhTrangXuLyVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSTinhTrangXuLyVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);

    List<RDBMSTinhTrangXuLyVuViec> thongKeTinhTrangXuLyVuViec();
}
