package com.fds.flex.core.inspectionmgt.rdbms.repository.T_Repository;

import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSBaoCaoTongHop;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSCaNhanToChuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RDBMSBaoCaoTongHopRepository extends JpaRepository<RDBMSBaoCaoTongHop, Long> {
    @Query("SELECT x FROM RDBMSBaoCaoTongHop x WHERE x.maLopDuLieu = :maLopDuLieu AND x.lopDuLieu = :lopDuLieu")
    List<RDBMSBaoCaoTongHop> findByMaLopDuLieu_LopDuLieu(Long maLopDuLieu, String lopDuLieu);
}
