package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiHoatDongThanhTra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSTrangThaiHoatDongThanhTraService {
    Optional<RDBMSTrangThaiHoatDongThanhTra> findById(String id);

    void deleteTrangThaiHoatDongThanhTra(RDBMSTrangThaiHoatDongThanhTra rdbmsTrangThaiHoatDongThanhTra);

    RDBMSTrangThaiHoatDongThanhTra updateTrangThaiHoatDongThanhTra(RDBMSTrangThaiHoatDongThanhTra rdbmsTrangThaiHoatDongThanhTra);

    Map<String, RDBMSTrangThaiHoatDongThanhTra> update(Map<String, RDBMSTrangThaiHoatDongThanhTra> map);

    Page<RDBMSTrangThaiHoatDongThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSTrangThaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSTrangThaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);

    List<RDBMSTrangThaiHoatDongThanhTra> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);
}