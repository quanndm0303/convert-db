package com.fds.flex.core.inspectionmgt.rdbms.repository.T_Repository;

import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSCaNhanToChuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RDBMSCaNhanToChucRepository extends JpaRepository<RDBMSCaNhanToChuc, Long> {

    @Query("SELECT x FROM RDBMSCaNhanToChuc x WHERE x.uuid = :uuid AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSCaNhanToChuc> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSCaNhanToChuc x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSCaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSCaNhanToChuc x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSCaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieuMaMuc);
}
