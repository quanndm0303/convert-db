package com.fds.flex.core.cadmgt.rdbms.repository.T_Repository;

import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSXuLyDonThu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RDBMSXuLyDonThuRepository extends JpaRepository<RDBMSXuLyDonThu, Long> {
    @Query("SELECT x FROM RDBMSXuLyDonThu x WHERE x.uuid = :uuid AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSXuLyDonThu> findByUuidAndTrangThaiDuLieu(@Param("uuid") String maMuc, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSXuLyDonThu x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSXuLyDonThu> findByMaDinhDanhAndTrangThaiDuLieu(@Param("maDinhDanh") String maDinhDanh, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSXuLyDonThu x WHERE x.maDonThu = :maDonThu AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSXuLyDonThu> findByMaDonThuAndTrangThaiDuLieu(@Param("maDonThu") String maDonThu, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSXuLyDonThu x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSXuLyDonThu> findByMaDinhDanhAndTrangThaiDuLieu(@Param("maDinhDanh") String maDinhDanh, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSXuLyDonThu x WHERE JSON_EXTRACT(x.VuViecDonThu, '$.MaDinhDanh') = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    List<RDBMSXuLyDonThu> findByVuViecDonThu(@Param("maDinhDanh") String maDinhDanh, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT COUNT(x) FROM RDBMSXuLyDonThu x WHERE x.MaDonThu LIKE %:regex% AND JSON_EXTRACT(x.TrangThaiDuLieu, '$.MaMuc') = '02'")
    Long countMaDonThu(@Param("regex") String regex);

    @Query("SELECT x FROM RDBMSXuLyDonThu x WHERE JSON_EXTRACT(x.TiepCongDan, '$.MaDinhDanh') = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSXuLyDonThu> findByTiepCongDanAndTrangThaiDuLieu(@Param("maDinhDanh") String maDinhDanh, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSXuLyDonThu x WHERE JSON_EXTRACT(x.TiepCongDan, '$.MaDinhDanh') IN :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    List<RDBMSXuLyDonThu> findByTiepCongDanAndTrangThaiDuLieu(@Param("maDinhDanh") String[] maDinhDanh, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

}
