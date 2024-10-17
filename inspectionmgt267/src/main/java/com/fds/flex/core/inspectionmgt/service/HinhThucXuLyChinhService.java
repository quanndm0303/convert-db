package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucXuLyChinh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HinhThucXuLyChinhService {
    Optional<HinhThucXuLyChinh> findById(String id);

    void deleteHinhThucXuLyChinh(HinhThucXuLyChinh hinhThucXuLyChinh);

    HinhThucXuLyChinh updateHinhThucXuLyChinh(HinhThucXuLyChinh hinhThucXuLyChinh);

    Map<String, HinhThucXuLyChinh> update(Map<String, HinhThucXuLyChinh> map);

    Page<HinhThucXuLyChinh> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<HinhThucXuLyChinh> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<HinhThucXuLyChinh> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}