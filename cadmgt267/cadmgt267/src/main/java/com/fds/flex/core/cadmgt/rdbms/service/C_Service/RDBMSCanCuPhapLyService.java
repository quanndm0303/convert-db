package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSCanCuPhapLy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSCanCuPhapLyService {
    Optional<RDBMSCanCuPhapLy> findById(String id);

    void deleteCanCuPhapLy(RDBMSCanCuPhapLy rdbmsCanCuPhapLy);

    RDBMSCanCuPhapLy updateCanCuPhapLy(RDBMSCanCuPhapLy rdbmsCanCuPhapLy);

    Map<String, RDBMSCanCuPhapLy> update(Map<String, RDBMSCanCuPhapLy> map);

    Page<RDBMSCanCuPhapLy> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSCanCuPhapLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSCanCuPhapLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
