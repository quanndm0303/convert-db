package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.PhanTichKetQuaKNTC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PhanTichKetQuaKNTCService {


    Optional<PhanTichKetQuaKNTC> findById(String id);

    void deletePhanTichKetQuaKNTC(PhanTichKetQuaKNTC phanTichKetQuaKNTC);

    PhanTichKetQuaKNTC updatePhanTichKetQuaKNTC(PhanTichKetQuaKNTC phanTichKetQuaKNTC);

    Map<String, PhanTichKetQuaKNTC> update(Map<String, PhanTichKetQuaKNTC> map);

    Page<PhanTichKetQuaKNTC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<PhanTichKetQuaKNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<PhanTichKetQuaKNTC> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<PhanTichKetQuaKNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
