package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTuCachPhapLy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSTuCachPhapLyService {

    Optional<RDBMSTuCachPhapLy> findById(String id);

    void deleteTuCachPhapLy(RDBMSTuCachPhapLy rdbmsTuCachPhapLy);

    RDBMSTuCachPhapLy updateTuCachPhapLy(RDBMSTuCachPhapLy rdbmsTuCachPhapLy);

    Map<String, RDBMSTuCachPhapLy> update(Map<String, RDBMSTuCachPhapLy> map);

    Page<RDBMSTuCachPhapLy> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSTuCachPhapLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSTuCachPhapLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
