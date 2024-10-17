package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKienNghiXuLyKNTC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSKienNghiXuLyKNTCService {
    Optional<RDBMSKienNghiXuLyKNTC> findById(String id);

    void deleteKienNghiXuLyKNTC(RDBMSKienNghiXuLyKNTC rdbmsKienNghiXuLyKNTC);

    RDBMSKienNghiXuLyKNTC updateKienNghiXuLyKNTC(RDBMSKienNghiXuLyKNTC rdbmsKienNghiXuLyKNTC);

    Map<String, RDBMSKienNghiXuLyKNTC> update(Map<String, RDBMSKienNghiXuLyKNTC> map);

    Page<RDBMSKienNghiXuLyKNTC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSKienNghiXuLyKNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSKienNghiXuLyKNTC> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<RDBMSKienNghiXuLyKNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
