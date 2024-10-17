package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.TinhTrangXuLyVuViec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TinhTrangXuLyVuViecService {
    Optional<TinhTrangXuLyVuViec> findById(String id);

    void deleteTinhTrangXuLyVuViec(TinhTrangXuLyVuViec tinhTrangXuLyVuViec);

    TinhTrangXuLyVuViec updateTinhTrangXuLyVuViec(TinhTrangXuLyVuViec tinhTrangXuLyVuViec);

    Map<String, TinhTrangXuLyVuViec> update(Map<String, TinhTrangXuLyVuViec> map);

    Page<TinhTrangXuLyVuViec> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<TinhTrangXuLyVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<TinhTrangXuLyVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);

    List<TinhTrangXuLyVuViec> thongKeTinhTrangXuLyVuViec();
}
