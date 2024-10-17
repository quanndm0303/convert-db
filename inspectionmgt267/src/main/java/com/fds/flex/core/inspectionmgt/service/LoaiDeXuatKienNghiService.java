package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiDeXuatKienNghi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoaiDeXuatKienNghiService {
    Optional<LoaiDeXuatKienNghi> findById(String id);

    void deleteLoaiDeXuatKienNghi(LoaiDeXuatKienNghi loaiDeXuatKienNghi);

    LoaiDeXuatKienNghi updateLoaiDeXuatKienNghi(LoaiDeXuatKienNghi loaiDeXuatKienNghi);

    Map<String, LoaiDeXuatKienNghi> update(Map<String, LoaiDeXuatKienNghi> map);

    Page<LoaiDeXuatKienNghi> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LoaiDeXuatKienNghi> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<LoaiDeXuatKienNghi> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}