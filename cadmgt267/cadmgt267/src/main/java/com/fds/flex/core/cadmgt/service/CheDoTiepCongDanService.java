package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.CheDoTiepCongDan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CheDoTiepCongDanService {


    Optional<CheDoTiepCongDan> findById(String id);

    void deleteCheDoTiepCongDan(CheDoTiepCongDan cheDoTiepCongDan);

    CheDoTiepCongDan updateCheDoTiepCongDan(CheDoTiepCongDan cheDoTiepCongDan);

    Map<String, CheDoTiepCongDan> update(Map<String, CheDoTiepCongDan> map);

    Page<CheDoTiepCongDan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<CheDoTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<CheDoTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
