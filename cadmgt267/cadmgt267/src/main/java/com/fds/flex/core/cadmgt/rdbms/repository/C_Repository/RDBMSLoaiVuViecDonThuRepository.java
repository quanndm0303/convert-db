package com.fds.flex.core.cadmgt.rdbms.repository.C_Repository;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSLoaiVuViecDonThu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RDBMSLoaiVuViecDonThuRepository extends JpaRepository<RDBMSLoaiVuViecDonThu, Long> {
    @Query("SELECT x FROM RDBMSLoaiVuViecDonThu x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSLoaiVuViecDonThu> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSLoaiVuViecDonThu x WHERE JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSLoaiVuViecDonThu> findByTrangThaiDuLieu(@Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSLoaiVuViecDonThu x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSLoaiVuViecDonThu> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

}
