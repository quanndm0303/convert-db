package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiDoiTuongCNTC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoaiDoiTuongCNTCService {
    Optional<LoaiDoiTuongCNTC> findById(String id);

    void deleteLoaiDoiTuongCNTC(LoaiDoiTuongCNTC loaiDoiTuongCNTC);

    LoaiDoiTuongCNTC updateLoaiDoiTuongCNTC(LoaiDoiTuongCNTC loaiDoiTuongCNTC);

    Map<String, LoaiDoiTuongCNTC> update(Map<String, LoaiDoiTuongCNTC> map);

    Page<LoaiDoiTuongCNTC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LoaiDoiTuongCNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<LoaiDoiTuongCNTC> findByThamChieuMaMucAndTrangThaiDuLieu(String thamChieu_MaMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<LoaiDoiTuongCNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}