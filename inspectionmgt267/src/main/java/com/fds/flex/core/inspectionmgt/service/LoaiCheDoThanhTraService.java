package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiCheDoThanhTra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoaiCheDoThanhTraService {
    Optional<LoaiCheDoThanhTra> findById(String id);

    void deleteLoaiCheDoThanhTra(LoaiCheDoThanhTra loaiCheDoThanhTra);

    LoaiCheDoThanhTra updateLoaiCheDoThanhTra(LoaiCheDoThanhTra loaiCheDoThanhTra);

    Map<String, LoaiCheDoThanhTra> update(Map<String, LoaiCheDoThanhTra> map);

    Page<LoaiCheDoThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LoaiCheDoThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<LoaiCheDoThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}