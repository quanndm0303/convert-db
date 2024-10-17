package com.fds.flex.core.cadmgt.rdbms.repository.T_Repository;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTuCachPhapLy;
import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSVuViecDonThu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RDBMSVuViecDonThuRepository extends JpaRepository<RDBMSVuViecDonThu, Long> {
    @Query("SELECT x FROM RDBMSVuViecDonThu x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSVuViecDonThu> findByMaDinhDanhAndTrangThaiDuLieu(@Param("maDinhDanh") String maDinhDanh, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSVuViecDonThu x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSVuViecDonThu> findByMaDinhDanhAndTrangThaiDuLieu(@Param("maDinhDanh") String maDinhDanh, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSVuViecDonThu x WHERE x.uuid = :uuid AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSVuViecDonThu> findByUuidAndTrangThaiDuLieu(@Param("uuid") String maMuc, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSVuViecDonThu x WHERE JSON_EXTRACT(x.HoSoNghiepVu, '$.MaDinhDanh') = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSVuViecDonThu> findByHSNV_TTDL(@Param("maDinhDanh") String maDinhDanh, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

}
