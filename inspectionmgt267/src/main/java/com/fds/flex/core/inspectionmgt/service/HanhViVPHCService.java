package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HanhViVPHC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HanhViVPHCService {
    Optional<HanhViVPHC> findById(String id);

    void deleteHanhViVPHC(HanhViVPHC hanhViVPHC);

    HanhViVPHC updateHanhViVPHC(HanhViVPHC hanhViVPHC);

    Map<String, HanhViVPHC> update(Map<String, HanhViVPHC> map);

    Page<HanhViVPHC> filter(String keyword, String nhomHanhViVPHC_MaMuc, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<HanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<HanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}