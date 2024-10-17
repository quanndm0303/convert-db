package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.DoiTuongDuocTiep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoiTuongDuocTiepService {


    Optional<DoiTuongDuocTiep> findById(String id);

    void deleteDoiTuongDuocTiep(DoiTuongDuocTiep doiTuongDuocTiep);

    DoiTuongDuocTiep updateDoiTuongDuocTiep(DoiTuongDuocTiep doiTuongDuocTiep);

    Map<String, DoiTuongDuocTiep> update(Map<String, DoiTuongDuocTiep> map);

    Page<DoiTuongDuocTiep> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<DoiTuongDuocTiep> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<DoiTuongDuocTiep> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
