package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HieuLucThiHanh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HieuLucThiHanhService {
    Optional<HieuLucThiHanh> findById(String id);

    void deleteHieuLucThiHanh(HieuLucThiHanh hieuLucThiHanh);

    HieuLucThiHanh updateHieuLucThiHanh(HieuLucThiHanh hieuLucThiHanh);

    Map<String, HieuLucThiHanh> update(Map<String, HieuLucThiHanh> map);

    Page<HieuLucThiHanh> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<HieuLucThiHanh> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<HieuLucThiHanh> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}