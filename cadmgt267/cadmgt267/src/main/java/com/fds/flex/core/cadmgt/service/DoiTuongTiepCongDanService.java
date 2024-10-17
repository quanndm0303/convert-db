package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.DoiTuongTiepCongDan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoiTuongTiepCongDanService {
    Optional<DoiTuongTiepCongDan> findById(String id);

    void deleteDoiTuongTiepCongDan(DoiTuongTiepCongDan doiTuongTiepCongDan);

    DoiTuongTiepCongDan updateDoiTuongTiepCongDan(DoiTuongTiepCongDan doiTuongTiepCongDan);

    Map<String, DoiTuongTiepCongDan> update(Map<String, DoiTuongTiepCongDan> map);

    Page<DoiTuongTiepCongDan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<DoiTuongTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<DoiTuongTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
