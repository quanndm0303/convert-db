package com.fds.flex.core.inspectionmgt.rdbms.repository.C_Repository;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiTheoDoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RDBMSTrangThaiTheoDoiRepository extends JpaRepository<RDBMSTrangThaiTheoDoi, Long> {
    @Query("SELECT x FROM RDBMSTrangThaiTheoDoi x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSTrangThaiTheoDoi> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSTrangThaiTheoDoi x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSTrangThaiTheoDoi> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSTrangThaiTheoDoi x WHERE JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    List<RDBMSTrangThaiTheoDoi> findByTrangThaiDuLieu(String trangThaiDuLieuMaMuc);
}