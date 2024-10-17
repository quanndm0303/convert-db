package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSLoaiDoiTuongDungTen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLoaiDoiTuongDungTenService {

    Optional<RDBMSLoaiDoiTuongDungTen> findById(String id);

    void deleteLoaiDoiTuongDungTen(RDBMSLoaiDoiTuongDungTen rdbmsLoaiDoiTuongDungTen);

    RDBMSLoaiDoiTuongDungTen updateLoaiDoiTuongDungTen(RDBMSLoaiDoiTuongDungTen rdbmsLoaiDoiTuongDungTen);

    Map<String, RDBMSLoaiDoiTuongDungTen> update(Map<String, RDBMSLoaiDoiTuongDungTen> map);

    Page<RDBMSLoaiDoiTuongDungTen> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLoaiDoiTuongDungTen> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSLoaiDoiTuongDungTen> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
