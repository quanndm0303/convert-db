package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.LoaiVuViecDonThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoaiVuViecDonThuService {


    Optional<LoaiVuViecDonThu> findById(String id);

    void deleteLoaiVuViecDonThu(LoaiVuViecDonThu loaiVuViecDonThu);

    LoaiVuViecDonThu updateLoaiVuViecDonThu(LoaiVuViecDonThu loaiVuViecDonThu);

    Map<String, LoaiVuViecDonThu> update(Map<String, LoaiVuViecDonThu> map);

    Page<LoaiVuViecDonThu> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<LoaiVuViecDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<LoaiVuViecDonThu> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<LoaiVuViecDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
