package com.fds.flex.core.cadmgt.rdbms.repository.C_Repository;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKetQuaTiepCongDan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RDBMSKetQuaTiepCongDanRepository extends JpaRepository<RDBMSKetQuaTiepCongDan, Long> {
    @Query("SELECT x FROM RDBMSKetQuaTiepCongDan x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSKetQuaTiepCongDan> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSKetQuaTiepCongDan x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSKetQuaTiepCongDan> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSKetQuaTiepCongDan x WHERE JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSKetQuaTiepCongDan> findByTrangThaiDuLieu(@Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

}
