package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.CanCuPhapLy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CanCuPhapLyService {


    Optional<CanCuPhapLy> findById(String id);

    void deleteCanCuPhapLy(CanCuPhapLy canCuPhapLy);

    CanCuPhapLy updateCanCuPhapLy(CanCuPhapLy canCuPhapLy);

    Map<String, CanCuPhapLy> update(Map<String, CanCuPhapLy> map);

    Page<CanCuPhapLy> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<CanCuPhapLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<CanCuPhapLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
