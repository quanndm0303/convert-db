package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.KienNghiXuLyKNTC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface KienNghiXuLyKNTCService {


    Optional<KienNghiXuLyKNTC> findById(String id);

    void deleteKienNghiXuLyKNTC(KienNghiXuLyKNTC kienNghiXuLyKNTC);

    KienNghiXuLyKNTC updateKienNghiXuLyKNTC(KienNghiXuLyKNTC kienNghiXuLyKNTC);

    Map<String, KienNghiXuLyKNTC> update(Map<String, KienNghiXuLyKNTC> map);

    Page<KienNghiXuLyKNTC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<KienNghiXuLyKNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<KienNghiXuLyKNTC> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<KienNghiXuLyKNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
