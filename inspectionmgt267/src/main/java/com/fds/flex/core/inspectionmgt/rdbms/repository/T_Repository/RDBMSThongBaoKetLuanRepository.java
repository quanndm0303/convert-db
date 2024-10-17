package com.fds.flex.core.inspectionmgt.rdbms.repository.T_Repository;

import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSThongBaoKetLuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RDBMSThongBaoKetLuanRepository extends JpaRepository<RDBMSThongBaoKetLuan, Long> {

    @Query("SELECT x FROM RDBMSThongBaoKetLuan x WHERE x.uuid = :uuid AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSThongBaoKetLuan> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSThongBaoKetLuan x WHERE JSON_EXTRACT(x.doiTuongKetLuan, '$.MaDinhDanh') = :doanThanhKiemTra AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    List<RDBMSThongBaoKetLuan> findByDTKT_TTDL(String doanThanhKiemTra, String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSThongBaoKetLuan x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSThongBaoKetLuan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSThongBaoKetLuan x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSThongBaoKetLuan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieuMaMuc);
}
