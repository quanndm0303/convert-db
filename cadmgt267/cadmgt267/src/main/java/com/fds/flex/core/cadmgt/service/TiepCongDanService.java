package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.Query_Model.TiepCongDanQueryModel;
import com.fds.flex.core.cadmgt.entity.T_Model.TiepCongDan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TiepCongDanService {


    Optional<TiepCongDan> findById(String id);

    void deleteTiepCongDan(TiepCongDan tiepCongDan);

    TiepCongDan updateTiepCongDan(TiepCongDan tiepCongDan);

    Map<String, TiepCongDan> update(Map<String, TiepCongDan> map);

    Page<TiepCongDan> filter(TiepCongDanQueryModel tiepCongDanQueryModel, Pageable pageable);

    List<TiepCongDan> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<TiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<TiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    List<TiepCongDan> findByVuViecDonThuMaDinhDanhAndTrangThaiDuLieu(String vuViecDonThu_MaDinhDanh, String trangThaiDuLieu_MaMuc);

    Map<String, Long> thongKeTinhTrangTiepCongDan(String cheDoTiepCongDan_MaMuc);

    Map<String, Long> thongKeKetQuaTiepCongDan(String cheDoTiepCongDan_MaMuc);

    Long countSoThuTuTiep(String regex);

    Optional<TiepCongDan> findBySoThuTuTiepAndTrangThaiDuLieu(String soThuTuTiep, String trangThaiDuLieu_MaMuc);
}
