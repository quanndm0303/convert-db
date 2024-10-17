package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucPhatBoSung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HinhThucPhatBoSungService {
    Optional<HinhThucPhatBoSung> findById(String id);

    void deleteHinhThucPhatBoSung(HinhThucPhatBoSung hinhThucPhatBoSung);

    HinhThucPhatBoSung updateHinhThucPhatBoSung(HinhThucPhatBoSung hinhThucPhatBoSung);

    Map<String, HinhThucPhatBoSung> update(Map<String, HinhThucPhatBoSung> map);

    Page<HinhThucPhatBoSung> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<HinhThucPhatBoSung> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<HinhThucPhatBoSung> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}