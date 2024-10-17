package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.LoaiChiTietVuViec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoaiChiTietVuViecService {

    Optional<LoaiChiTietVuViec> findById(String id);

    void deleteLoaiChiTietVuViec(LoaiChiTietVuViec loaiChiTietVuViec);

    LoaiChiTietVuViec updateLoaiChiTietVuViec(LoaiChiTietVuViec loaiChiTietVuViec);

    Map<String, LoaiChiTietVuViec> update(Map<String, LoaiChiTietVuViec> map);

    Page<LoaiChiTietVuViec> filter(String keyword, String loaiVuViecDonThu_MaMuc, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LoaiChiTietVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<LoaiChiTietVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
