package com.fds.flex.core.inspectionmgt.rdbms.repository.C_Repository;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiHoatDongThanhTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RDBMSTrangThaiHoatDongThanhTraRepository extends JpaRepository<RDBMSTrangThaiHoatDongThanhTra, Long> {
    @Query("SELECT x FROM RDBMSTrangThaiHoatDongThanhTra x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSTrangThaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSTrangThaiHoatDongThanhTra x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSTrangThaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSTrangThaiHoatDongThanhTra x WHERE JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    List<RDBMSTrangThaiHoatDongThanhTra> findByTrangThaiDuLieu(String trangThaiDuLieuMaMuc);
}