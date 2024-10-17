package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.TuCachPhapLy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TuCachPhapLyService {


    Optional<TuCachPhapLy> findById(String id);

    void deleteTuCachPhapLy(TuCachPhapLy tuCachPhapLy);

    TuCachPhapLy updateTuCachPhapLy(TuCachPhapLy tuCachPhapLy);

    Map<String, TuCachPhapLy> update(Map<String, TuCachPhapLy> map);

    Page<TuCachPhapLy> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<TuCachPhapLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<TuCachPhapLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
