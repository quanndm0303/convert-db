package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHinhThucPhatBoSung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSHinhThucPhatBoSungService {
    Optional<RDBMSHinhThucPhatBoSung> findById(String id);

    void deleteHinhThucPhatBoSung(RDBMSHinhThucPhatBoSung rdbmsHinhThucPhatBoSung);

    RDBMSHinhThucPhatBoSung updateHinhThucPhatBoSung(RDBMSHinhThucPhatBoSung rdbmsHinhThucPhatBoSung);

    Map<String, RDBMSHinhThucPhatBoSung> update(Map<String, RDBMSHinhThucPhatBoSung> map);

    Page<RDBMSHinhThucPhatBoSung> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSHinhThucPhatBoSung> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSHinhThucPhatBoSung> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}