package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.LoaiPhucTapKeoDai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoaiPhucTapKeoDaiService {


    Optional<LoaiPhucTapKeoDai> findById(String id);

    void deleteLoaiPhucTapKeoDai(LoaiPhucTapKeoDai loaiPhucTapKeoDai);

    LoaiPhucTapKeoDai updateLoaiPhucTapKeoDai(LoaiPhucTapKeoDai loaiPhucTapKeoDai);

    Map<String, LoaiPhucTapKeoDai> update(Map<String, LoaiPhucTapKeoDai> map);

    Page<LoaiPhucTapKeoDai> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LoaiPhucTapKeoDai> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<LoaiPhucTapKeoDai> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
