package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.LinhVucChuyenNganh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LinhVucChuyenNganhService {


    Optional<LinhVucChuyenNganh> findById(String id);

    void deleteLinhVucChuyenNganh(LinhVucChuyenNganh linhVucChuyenNganh);

    LinhVucChuyenNganh updateLinhVucChuyenNganh(LinhVucChuyenNganh linhVucChuyenNganh);

    Map<String, LinhVucChuyenNganh> update(Map<String, LinhVucChuyenNganh> map);

    Page<LinhVucChuyenNganh> filter(String keyword, String loaiVuViecDonThu_MaMuc, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LinhVucChuyenNganh> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<LinhVucChuyenNganh> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
