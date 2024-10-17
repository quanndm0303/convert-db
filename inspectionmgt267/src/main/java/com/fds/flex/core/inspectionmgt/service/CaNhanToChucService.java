package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.Query_Model.CaNhanToChucQueryModel;
import com.fds.flex.core.inspectionmgt.entity.T_Model.CaNhanToChuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CaNhanToChucService {
    Optional<CaNhanToChuc> findById(String id);

    void deleteCaNhanToChuc(CaNhanToChuc caNhanToChuc);

    CaNhanToChuc updateCaNhanToChuc(CaNhanToChuc caNhanToChuc);

    Map<String, CaNhanToChuc> update(Map<String, CaNhanToChuc> map);

    Page<CaNhanToChuc> filter(CaNhanToChucQueryModel caNhanToChucQueryModel, Pageable pageable);

    List<CaNhanToChuc> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<CaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<CaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);
    List<CaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String[] maDinhDanh, String[] trangThaiDuLieu_MaMuc);
}
