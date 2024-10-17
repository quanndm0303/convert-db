package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.TrangThaiGiaiQuyetDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TrangThaiGiaiQuyetDonService {


    Optional<TrangThaiGiaiQuyetDon> findById(String id);

    void deleteTrangThaiGiaiQuyetDon(TrangThaiGiaiQuyetDon trangThaiGiaiQuyetDon);

    TrangThaiGiaiQuyetDon updateTrangThaiGiaiQuyetDon(TrangThaiGiaiQuyetDon trangThaiGiaiQuyetDon);

    Map<String, TrangThaiGiaiQuyetDon> update(Map<String, TrangThaiGiaiQuyetDon> map);

    Page<TrangThaiGiaiQuyetDon> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<TrangThaiGiaiQuyetDon> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<TrangThaiGiaiQuyetDon> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    Optional<TrangThaiGiaiQuyetDon> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
