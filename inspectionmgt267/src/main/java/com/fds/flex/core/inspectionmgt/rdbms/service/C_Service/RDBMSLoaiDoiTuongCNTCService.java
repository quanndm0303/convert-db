package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiDoiTuongCNTC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLoaiDoiTuongCNTCService {
    Optional<RDBMSLoaiDoiTuongCNTC> findById(String id);

    void deleteLoaiDoiTuongCNTC(RDBMSLoaiDoiTuongCNTC rdbmsLoaiDoiTuongCNTC);

    RDBMSLoaiDoiTuongCNTC updateLoaiDoiTuongCNTC(RDBMSLoaiDoiTuongCNTC rdbmsLoaiDoiTuongCNTC);

    Map<String, RDBMSLoaiDoiTuongCNTC> update(Map<String, RDBMSLoaiDoiTuongCNTC> map);

    Page<RDBMSLoaiDoiTuongCNTC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLoaiDoiTuongCNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSLoaiDoiTuongCNTC> findByThamChieuMaMucAndTrangThaiDuLieu(String thamChieu_MaMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSLoaiDoiTuongCNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}