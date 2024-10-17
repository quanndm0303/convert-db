package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiHoatDongThanhTra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TrangThaiHoatDongThanhTraService {
    Optional<TrangThaiHoatDongThanhTra> findById(String id);

    void deleteTrangThaiHoatDongThanhTra(TrangThaiHoatDongThanhTra trangThaiHoatDongThanhTra);

    TrangThaiHoatDongThanhTra updateTrangThaiHoatDongThanhTra(TrangThaiHoatDongThanhTra trangThaiHoatDongThanhTra);

    Map<String, TrangThaiHoatDongThanhTra> update(Map<String, TrangThaiHoatDongThanhTra> map);

    Page<TrangThaiHoatDongThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<TrangThaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<TrangThaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);

    List<TrangThaiHoatDongThanhTra> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);
}