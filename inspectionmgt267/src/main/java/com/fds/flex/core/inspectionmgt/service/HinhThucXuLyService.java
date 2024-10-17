package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucXuLy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HinhThucXuLyService {
    Optional<HinhThucXuLy> findById(String id);

    void deleteHinhThucXuLy(HinhThucXuLy hinhThucXuLy);

    HinhThucXuLy updateHinhThucXuLy(HinhThucXuLy hinhThucXuLy);

    Map<String, HinhThucXuLy> update(Map<String, HinhThucXuLy> map);

    Page<HinhThucXuLy> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<HinhThucXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<HinhThucXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}