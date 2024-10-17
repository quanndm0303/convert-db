package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.HinhThucNhanDonThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HinhThucNhanDonThuService {


    Optional<HinhThucNhanDonThu> findById(String id);

    void deleteHinhThucNhanDonThu(HinhThucNhanDonThu hinhThucNhanDonThu);

    HinhThucNhanDonThu updateHinhThucNhanDonThu(HinhThucNhanDonThu hinhThucNhanDonThu);

    Map<String, HinhThucNhanDonThu> update(Map<String, HinhThucNhanDonThu> map);

    Page<HinhThucNhanDonThu> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<HinhThucNhanDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<HinhThucNhanDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
