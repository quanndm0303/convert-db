package com.fds.flex.core.cadmgt.rdbms.repository.C_Repository;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTinhTrangXuLyDonThu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RDBMSTinhTrangXuLyDonThuRepository extends JpaRepository<RDBMSTinhTrangXuLyDonThu, Long> {
    @Query("SELECT x FROM RDBMSTinhTrangXuLyDonThu x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') IN :trangThaiDuLieuMaMuc")
    List<RDBMSTinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String[] trangThaiDuLieuMaMuc);

    @Query("SELECT x FROM RDBMSTinhTrangXuLyDonThu x WHERE x.SuDungChoXuLyDonThu = true AND JSON_EXTRACT(x.TrangThaiDuLieu, '$.MaMuc') = '02'")
    List<RDBMSTinhTrangXuLyDonThu> findBySuDungChoXuLyDonThu_TTDL();

    @Query("SELECT x FROM RDBMSTinhTrangXuLyDonThu x WHERE x.SuDungChoVuViecDonThu = true AND JSON_EXTRACT(x.TrangThaiDuLieu, '$.MaMuc') = '02'")
    List<RDBMSTinhTrangXuLyDonThu> findBySuDungChoVuViecDonThu_TTDL();

    @Query("SELECT x FROM RDBMSTinhTrangXuLyDonThu x WHERE x.maMuc = :maMuc AND JSON_EXTRACT(x.trangThaiDuLieu, '$.MaMuc') = :trangThaiDuLieuMaMuc")
    Optional<RDBMSTinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(@Param("maMuc") String maMuc, @Param("trangThaiDuLieuMaMuc") String trangThaiDuLieuMaMuc);

}
