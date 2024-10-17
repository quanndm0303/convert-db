package com.fds.flex.core.cadmgt.rdbms.service.T_Service;

import com.fds.flex.core.cadmgt.entity.Query_Model.TiepCongDanQueryModel;
import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSTiepCongDan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSTiepCongDanService {


    Optional<RDBMSTiepCongDan> findById(String id);

    void deleteTiepCongDan(RDBMSTiepCongDan rdbmsTiepCongDan);

    RDBMSTiepCongDan updateTiepCongDan(RDBMSTiepCongDan rdbmsTiepCongDan);

    Map<String, RDBMSTiepCongDan> update(Map<String, RDBMSTiepCongDan> map);

    Page<RDBMSTiepCongDan> filter(TiepCongDanQueryModel tiepCongDanQueryModel, Pageable pageable);

    List<RDBMSTiepCongDan> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSTiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<RDBMSTiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSTiepCongDan> findByVuViecDonThuMaDinhDanhAndTrangThaiDuLieu(String vuViecDonThu_MaDinhDanh, String trangThaiDuLieu_MaMuc);

    Map<String, Long> thongKeTinhTrangTiepCongDan(String cheDoTiepCongDan_MaMuc);

    Map<String, Long> thongKeKetQuaTiepCongDan(String cheDoTiepCongDan_MaMuc);

    Long countSoThuTuTiep(String regex);

    Optional<RDBMSTiepCongDan> findBySoThuTuTiepAndTrangThaiDuLieu(String soThuTuTiep, String trangThaiDuLieu_MaMuc);
}
