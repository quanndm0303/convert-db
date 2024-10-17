package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSDiaBanXuLy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSDiaBanXuLyService {
    Optional<RDBMSDiaBanXuLy> findById(String id);

    void deleteDiaBanXuLy(RDBMSDiaBanXuLy rdbmsDiaBanXuLy);

    RDBMSDiaBanXuLy updateDiaBanXuLy(RDBMSDiaBanXuLy rdbmsDiaBanXuLy);

    Map<String, RDBMSDiaBanXuLy> update(Map<String, RDBMSDiaBanXuLy> map);

    Page<RDBMSDiaBanXuLy> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSDiaBanXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSDiaBanXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
