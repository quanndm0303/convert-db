package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiKhaoSat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TrangThaiKhaoSatService {
    Optional<TrangThaiKhaoSat> findById(String id);

    void deleteTrangThaiKhaoSat(TrangThaiKhaoSat trangThaiKhaoSat);

    TrangThaiKhaoSat updateTrangThaiKhaoSat(TrangThaiKhaoSat trangThaiKhaoSat);

    Map<String, TrangThaiKhaoSat> update(Map<String, TrangThaiKhaoSat> map);

    Page<TrangThaiKhaoSat> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<TrangThaiKhaoSat> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<TrangThaiKhaoSat> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<TrangThaiKhaoSat> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}