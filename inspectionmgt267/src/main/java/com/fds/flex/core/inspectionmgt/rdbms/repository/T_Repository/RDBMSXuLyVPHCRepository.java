package com.fds.flex.core.inspectionmgt.rdbms.repository.T_Repository;

import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSXuLyVPHC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RDBMSXuLyVPHCRepository extends JpaRepository<RDBMSXuLyVPHC, Long> {


    @Query("SELECT x FROM RDBMSXuLyVPHC x WHERE x.uuid = :uuid AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSXuLyVPHC> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSXuLyVPHC x WHERE x.soBienBanVPHC = :soBienBanVPHC AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    List<RDBMSXuLyVPHC> findBySoBienBanVPHCAndTrangThaiDuLieu(String soBienBanVPHC, String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSXuLyVPHC x WHERE x.soQuyetDinh = :soQuyetDinh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    List<RDBMSXuLyVPHC> findBySoQuyetDinhAndTrangThaiDuLieu(String soQuyetDinh, String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSXuLyVPHC x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSXuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSXuLyVPHC x WHERE x.maDinhDanh = :maDinhDanh AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSXuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieuMaMuc);

}
