package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.KetQuaTiepCongDan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface KetQuaTiepCongDanService {
    Optional<KetQuaTiepCongDan> findById(String id);

    void deleteKetQuaTiepCongDan(KetQuaTiepCongDan ketQuaTiepCongDan);

    KetQuaTiepCongDan updateKetQuaTiepCongDan(KetQuaTiepCongDan ketQuaTiepCongDan);

    Map<String, KetQuaTiepCongDan> update(Map<String, KetQuaTiepCongDan> map);

    Page<KetQuaTiepCongDan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<KetQuaTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<KetQuaTiepCongDan> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<KetQuaTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
