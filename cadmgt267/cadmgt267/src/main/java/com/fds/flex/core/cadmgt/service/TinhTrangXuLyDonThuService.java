package com.fds.flex.core.cadmgt.service;

import com.fds.flex.core.cadmgt.entity.C_Model.TinhTrangXuLyDonThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TinhTrangXuLyDonThuService {


    Optional<TinhTrangXuLyDonThu> findById(String id);

    void deleteTinhTrangXuLyDonThu(TinhTrangXuLyDonThu tinhTrangXuLyDonThu);

    TinhTrangXuLyDonThu updateTinhTrangXuLyDonThu(TinhTrangXuLyDonThu tinhTrangXuLyDonThu);

    Map<String, TinhTrangXuLyDonThu> update(Map<String, TinhTrangXuLyDonThu> map);

    Page<TinhTrangXuLyDonThu> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<TinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    List<TinhTrangXuLyDonThu> findBySuDungChoXuLyDonThu_TTDL();

    List<TinhTrangXuLyDonThu> findBySuDungChoVuViecDonThu_TTDL();

    Optional<TinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);

}
