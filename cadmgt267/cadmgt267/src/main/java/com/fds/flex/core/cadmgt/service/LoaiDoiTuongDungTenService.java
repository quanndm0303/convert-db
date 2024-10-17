package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.LoaiDoiTuongDungTen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoaiDoiTuongDungTenService {


    Optional<LoaiDoiTuongDungTen> findById(String id);

    void deleteLoaiDoiTuongDungTen(LoaiDoiTuongDungTen loaiDoiTuongDungTen);

    LoaiDoiTuongDungTen updateLoaiDoiTuongDungTen(LoaiDoiTuongDungTen loaiDoiTuongDungTen);

    Map<String, LoaiDoiTuongDungTen> update(Map<String, LoaiDoiTuongDungTen> map);

    Page<LoaiDoiTuongDungTen> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LoaiDoiTuongDungTen> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<LoaiDoiTuongDungTen> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
