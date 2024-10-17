package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiTheoDoi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TrangThaiTheoDoiService {
    Optional<TrangThaiTheoDoi> findById(String id);

    void deleteTrangThaiTheoDoi(TrangThaiTheoDoi trangThaiTheoDoi);

    TrangThaiTheoDoi updateTrangThaiTheoDoi(TrangThaiTheoDoi trangThaiTheoDoi);

    Map<String, TrangThaiTheoDoi> update(Map<String, TrangThaiTheoDoi> map);

    Page<TrangThaiTheoDoi> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<TrangThaiTheoDoi> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<TrangThaiTheoDoi> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);

    List<TrangThaiTheoDoi> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);
}