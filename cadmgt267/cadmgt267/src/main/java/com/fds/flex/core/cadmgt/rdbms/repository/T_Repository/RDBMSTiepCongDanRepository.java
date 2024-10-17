package com.fds.flex.core.cadmgt.rdbms.repository.T_Repository;

import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSTiepCongDan;
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
public interface RDBMSTiepCongDanRepository extends JpaRepository<RDBMSTiepCongDan, Long> {
    @Query("SELECT x FROM RDBMSTiepCongDan x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSTiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(@Param("maDinhDanh") String maDinhDanh, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSTiepCongDan x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSTiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(@Param("maDinhDanh") String maDinhDanh, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSTiepCongDan x WHERE x.uuid = :uuid AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSTiepCongDan> findByUuidAndTrangThaiDuLieu(@Param("uuid") String maMuc, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSTiepCongDan x WHERE JSON_EXTRACT(x.VuViecDonThu, '$.MaDinhDanh') = :vuViecDonThu_maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    List<RDBMSTiepCongDan> findByVuViecDonThuMaDinhDanhAndTrangThaiDuLieu(@Param("vuViecDonThu_maDinhDanh") String vuViecDonThu_maDinhDanh, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

    @Query("SELECT COUNT(x) FROM RDBMSTiepCongDan x WHERE x.SoThuTuTiep LIKE %:regex% AND JSON_EXTRACT(x.TrangThaiDuLieu, '$.MaMuc') = '02'")
    Long countSoThuTuTiep(@Param("regex") String regex);

    @Query("SELECT x FROM RDBMSTiepCongDan x WHERE x.SoThuTuTiep= :soHieuDonThu AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSTiepCongDan> findBySoThuTuTiepAndTrangThaiDuLieu(@Param("soHieuDonThu") String soHieuDonThu, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

}
