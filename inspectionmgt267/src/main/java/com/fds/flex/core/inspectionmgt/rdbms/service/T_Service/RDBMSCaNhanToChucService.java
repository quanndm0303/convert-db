package com.fds.flex.core.inspectionmgt.rdbms.service.T_Service;

import com.fds.flex.core.inspectionmgt.entity.Query_Model.CaNhanToChucQueryModel;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSCaNhanToChuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSCaNhanToChucService {

    Optional<RDBMSCaNhanToChuc> findById(String id);

    void deleteCaNhanToChuc(RDBMSCaNhanToChuc caNhanToChuc);

    RDBMSCaNhanToChuc updateCaNhanToChuc(RDBMSCaNhanToChuc caNhanToChuc);

    Map<String, RDBMSCaNhanToChuc> update(Map<String, RDBMSCaNhanToChuc> map);

    Page<RDBMSCaNhanToChuc> filter(CaNhanToChucQueryModel caNhanToChucQueryModel, Pageable pageable);

    List<RDBMSCaNhanToChuc> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSCaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<RDBMSCaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);
}
