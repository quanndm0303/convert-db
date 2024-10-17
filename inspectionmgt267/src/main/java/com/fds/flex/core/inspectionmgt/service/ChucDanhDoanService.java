package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.ChucDanhDoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChucDanhDoanService {
    Optional<ChucDanhDoan> findById(String id);

    void deleteChucDanhDoan(ChucDanhDoan chucDanhDoan);

    ChucDanhDoan updateChucDanhDoan(ChucDanhDoan chucDanhDoan);

    Map<String, ChucDanhDoan> update(Map<String, ChucDanhDoan> map);

    Page<ChucDanhDoan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<ChucDanhDoan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<ChucDanhDoan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}