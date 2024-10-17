package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.NghiDinhXPVPHC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NghiDinhXPVPHCService {
    Optional<NghiDinhXPVPHC> findById(String id);

    void deleteNghiDinhXPVPHC(NghiDinhXPVPHC nghiDinhXPVPHC);

    NghiDinhXPVPHC updateNghiDinhXPVPHC(NghiDinhXPVPHC nghiDinhXPVPHC);

    Map<String, NghiDinhXPVPHC> update(Map<String, NghiDinhXPVPHC> map);

    Page<NghiDinhXPVPHC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<NghiDinhXPVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<NghiDinhXPVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}