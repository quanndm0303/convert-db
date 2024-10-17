package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHinhThucXuLyChinh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSHinhThucXuLyChinhService {
    Optional<RDBMSHinhThucXuLyChinh> findById(String id);

    void deleteHinhThucXuLyChinh(RDBMSHinhThucXuLyChinh rdbmsHinhThucXuLyChinh);

    RDBMSHinhThucXuLyChinh updateHinhThucXuLyChinh(RDBMSHinhThucXuLyChinh rdbmsHinhThucXuLyChinh);

    Map<String, RDBMSHinhThucXuLyChinh> update(Map<String, RDBMSHinhThucXuLyChinh> map);

    Page<RDBMSHinhThucXuLyChinh> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSHinhThucXuLyChinh> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSHinhThucXuLyChinh> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}