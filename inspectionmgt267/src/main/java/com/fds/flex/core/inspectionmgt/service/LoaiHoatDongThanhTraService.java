package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiHoatDongThanhTra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoaiHoatDongThanhTraService {
    Optional<LoaiHoatDongThanhTra> findById(String id);

    void deleteLoaiHoatDongThanhTra(LoaiHoatDongThanhTra loaiHoatDongThanhTra);

    LoaiHoatDongThanhTra updateLoaiHoatDongThanhTra(LoaiHoatDongThanhTra loaiHoatDongThanhTra);

    Map<String, LoaiHoatDongThanhTra> update(Map<String, LoaiHoatDongThanhTra> map);

    Page<LoaiHoatDongThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LoaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<LoaiHoatDongThanhTra> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<LoaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}