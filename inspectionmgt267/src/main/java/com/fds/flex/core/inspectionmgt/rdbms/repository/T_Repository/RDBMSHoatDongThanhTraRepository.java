package com.fds.flex.core.inspectionmgt.rdbms.repository.T_Repository;

import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSHoatDongThanhTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RDBMSHoatDongThanhTraRepository extends JpaRepository<RDBMSHoatDongThanhTra, Long> {

    @Query("SELECT x FROM RDBMSHoatDongThanhTra x WHERE x.uuid = :uuid AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSHoatDongThanhTra> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSHoatDongThanhTra x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSHoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSHoatDongThanhTra x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSHoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSHoatDongThanhTra x WHERE JSON_EXTRACT(x.hoSoNghiepVu, '$.MaDinhDanh') = :hsnv_MaDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSHoatDongThanhTra> findByHSNVMaDinhDanhAndTrangThaiDuLieu(String hsnv_MaDinhDanh, String[] trangThaiDuLieuMaMuc);
}
