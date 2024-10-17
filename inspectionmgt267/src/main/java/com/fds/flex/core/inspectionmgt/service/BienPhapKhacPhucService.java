package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.BienPhapKhacPhuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BienPhapKhacPhucService {
    Optional<BienPhapKhacPhuc> findById(String id);

    void deleteBienPhapKhacPhuc(BienPhapKhacPhuc bienPhapKhacPhuc);

    BienPhapKhacPhuc updateBienPhapKhacPhuc(BienPhapKhacPhuc bienPhapKhacPhuc);

    Map<String, BienPhapKhacPhuc> update(Map<String, BienPhapKhacPhuc> map);

    Page<BienPhapKhacPhuc> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<BienPhapKhacPhuc> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<BienPhapKhacPhuc> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}