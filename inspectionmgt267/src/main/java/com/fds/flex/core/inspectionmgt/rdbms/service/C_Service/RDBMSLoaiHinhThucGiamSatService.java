package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiHinhThucGiamSat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLoaiHinhThucGiamSatService {
    Optional<RDBMSLoaiHinhThucGiamSat> findById(String id);

    void deleteLoaiHinhThucGiamSat(RDBMSLoaiHinhThucGiamSat rdbmsLoaiHinhThucGiamSat);

    RDBMSLoaiHinhThucGiamSat updateLoaiHinhThucGiamSat(RDBMSLoaiHinhThucGiamSat rdbmsLoaiHinhThucGiamSat);

    Map<String, RDBMSLoaiHinhThucGiamSat> update(Map<String, RDBMSLoaiHinhThucGiamSat> map);

    Page<RDBMSLoaiHinhThucGiamSat> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLoaiHinhThucGiamSat> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSLoaiHinhThucGiamSat> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}