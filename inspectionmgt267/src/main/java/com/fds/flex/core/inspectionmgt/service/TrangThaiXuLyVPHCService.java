package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiXuLyVPHC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TrangThaiXuLyVPHCService {
    Optional<TrangThaiXuLyVPHC> findById(String id);

    List<TrangThaiXuLyVPHC> findByTrangThaiDuLieu(String[] trangThaiDulieu);

    void deleteTrangThaiXuLyVPHC(TrangThaiXuLyVPHC trangThaiXuLyVPHC);

    TrangThaiXuLyVPHC updateTrangThaiXuLyVPHC(TrangThaiXuLyVPHC trangThaiXuLyVPHC);

    Map<String, TrangThaiXuLyVPHC> update(Map<String, TrangThaiXuLyVPHC> map);

    Page<TrangThaiXuLyVPHC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<TrangThaiXuLyVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<TrangThaiXuLyVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}