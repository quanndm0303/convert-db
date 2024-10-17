package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiHinhThucGiamSat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoaiHinhThucGiamSatService {
    Optional<LoaiHinhThucGiamSat> findById(String id);

    void deleteLoaiHinhThucGiamSat(LoaiHinhThucGiamSat loaiHinhThucGiamSat);

    LoaiHinhThucGiamSat updateLoaiHinhThucGiamSat(LoaiHinhThucGiamSat loaiHinhThucGiamSat);

    Map<String, LoaiHinhThucGiamSat> update(Map<String, LoaiHinhThucGiamSat> map);

    Page<LoaiHinhThucGiamSat> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LoaiHinhThucGiamSat> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<LoaiHinhThucGiamSat> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}