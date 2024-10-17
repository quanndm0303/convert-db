package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.DiaBanXuLy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DiaBanXuLyService {


    Optional<DiaBanXuLy> findById(String id);

    void deleteDiaBanXuLy(DiaBanXuLy diaBanXuLy);

    DiaBanXuLy updateDiaBanXuLy(DiaBanXuLy diaBanXuLy);

    Map<String, DiaBanXuLy> update(Map<String, DiaBanXuLy> map);

    Page<DiaBanXuLy> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<DiaBanXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<DiaBanXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
