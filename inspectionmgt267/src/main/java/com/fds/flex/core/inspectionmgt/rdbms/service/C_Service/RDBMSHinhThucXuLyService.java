package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHinhThucXuLy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSHinhThucXuLyService {
    Optional<RDBMSHinhThucXuLy> findById(String id);

    void deleteHinhThucXuLy(RDBMSHinhThucXuLy rdbmsHinhThucXuLy);

    RDBMSHinhThucXuLy updateHinhThucXuLy(RDBMSHinhThucXuLy rdbmsHinhThucXuLy);

    Map<String, RDBMSHinhThucXuLy> update(Map<String, RDBMSHinhThucXuLy> map);

    Page<RDBMSHinhThucXuLy> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSHinhThucXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSHinhThucXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}