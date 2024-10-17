package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiThongBaoKetLuan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoaiThongBaoKetLuanService {
    Optional<LoaiThongBaoKetLuan> findById(String id);

    void deleteLoaiThongBaoKetLuan(LoaiThongBaoKetLuan loaiThongBaoKetLuan);

    LoaiThongBaoKetLuan updateLoaiThongBaoKetLuan(LoaiThongBaoKetLuan loaiThongBaoKetLuan);

    Map<String, LoaiThongBaoKetLuan> update(Map<String, LoaiThongBaoKetLuan> map);

    Page<LoaiThongBaoKetLuan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LoaiThongBaoKetLuan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<LoaiThongBaoKetLuan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}