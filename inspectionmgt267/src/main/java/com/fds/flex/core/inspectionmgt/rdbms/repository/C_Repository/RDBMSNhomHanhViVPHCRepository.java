package com.fds.flex.core.inspectionmgt.rdbms.repository.C_Repository;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSNhomHanhViVPHC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RDBMSNhomHanhViVPHCRepository extends JpaRepository<RDBMSNhomHanhViVPHC, Long> {
    @Query("SELECT x FROM RDBMSNhomHanhViVPHC x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSNhomHanhViVPHC> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSNhomHanhViVPHC x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSNhomHanhViVPHC> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);
}