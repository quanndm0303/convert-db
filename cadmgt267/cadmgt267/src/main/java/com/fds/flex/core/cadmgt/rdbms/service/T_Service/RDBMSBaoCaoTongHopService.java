package com.fds.flex.core.cadmgt.rdbms.service.T_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSBaoCaoTongHop;

import java.util.Map;
import java.util.Optional;

public interface RDBMSBaoCaoTongHopService {
    Optional<RDBMSBaoCaoTongHop> findById(String id);

    void deleteBaoCaoTongHop(RDBMSBaoCaoTongHop caNhanToChuc);

    void deleteBaoCaoTongHopByMaLopDuLieu_LopDuLieu(Long maLopDuLieu, String lopDuLieu);

    RDBMSBaoCaoTongHop updateBaoCaoTongHop(RDBMSBaoCaoTongHop caNhanToChuc);

    Map<String, RDBMSBaoCaoTongHop> update(Map<String, RDBMSBaoCaoTongHop> map);
}
