package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiCheDoThanhTra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLoaiCheDoThanhTraService {
    Optional<RDBMSLoaiCheDoThanhTra> findById(String id);

    void deleteLoaiCheDoThanhTra(RDBMSLoaiCheDoThanhTra rdbmsLoaiCheDoThanhTra);

    RDBMSLoaiCheDoThanhTra updateLoaiCheDoThanhTra(RDBMSLoaiCheDoThanhTra rdbmsLoaiCheDoThanhTra);

    Map<String, RDBMSLoaiCheDoThanhTra> update(Map<String, RDBMSLoaiCheDoThanhTra> map);

    Page<RDBMSLoaiCheDoThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLoaiCheDoThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSLoaiCheDoThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}