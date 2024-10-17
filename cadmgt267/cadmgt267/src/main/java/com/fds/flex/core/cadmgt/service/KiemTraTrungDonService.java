package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.KiemTraTrungDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface KiemTraTrungDonService {
    Optional<KiemTraTrungDon> findById(String id);

    void deleteKiemTraTrungDon(KiemTraTrungDon kiemTraTrungDon);

    KiemTraTrungDon updateKiemTraTrungDon(KiemTraTrungDon kiemTraTrungDon);

    Map<String, KiemTraTrungDon> update(Map<String, KiemTraTrungDon> map);

    Page<KiemTraTrungDon> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<KiemTraTrungDon> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<KiemTraTrungDon> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
