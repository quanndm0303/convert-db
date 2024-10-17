package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.NhomHanhViVPHC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NhomHanhViVPHCService {
    Optional<NhomHanhViVPHC> findById(String id);

    void deleteNhomHanhViVPHC(NhomHanhViVPHC nhomHanhViVPHC);

    NhomHanhViVPHC updateNhomHanhViVPHC(NhomHanhViVPHC nhomHanhViVPHC);

    Map<String, NhomHanhViVPHC> update(Map<String, NhomHanhViVPHC> map);

    Page<NhomHanhViVPHC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<NhomHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<NhomHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}