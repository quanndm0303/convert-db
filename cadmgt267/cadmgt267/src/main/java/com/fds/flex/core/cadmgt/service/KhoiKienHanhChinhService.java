package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.KhoiKienHanhChinh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface KhoiKienHanhChinhService {


    Optional<KhoiKienHanhChinh> findById(String id);

    void deleteKhoiKienHanhChinh(KhoiKienHanhChinh khoiKienHanhChinh);

    KhoiKienHanhChinh updateKhoiKienHanhChinh(KhoiKienHanhChinh khoiKienHanhChinh);

    Map<String, KhoiKienHanhChinh> update(Map<String, KhoiKienHanhChinh> map);

    Page<KhoiKienHanhChinh> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<KhoiKienHanhChinh> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<KhoiKienHanhChinh> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
