package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.Query_Model.XuLyVPHCQueryModel;
import com.fds.flex.core.inspectionmgt.entity.T_Model.XuLyVPHC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface XuLyVPHCService {
    Optional<XuLyVPHC> findById(String id);

    void deleteXuLyVPHC(XuLyVPHC xuLyVPHC);

    XuLyVPHC updateXuLyVPHC(XuLyVPHC xuLyVPHC);

    List<XuLyVPHC> findBySoBienBanVPHCAndTrangThaiDuLieu(String soBienBanVPHC);

    List<XuLyVPHC> findBySoQuyetDinhAndTrangThaiDuLieu(String soQuyetDinh);

    Map<String, XuLyVPHC> update(Map<String, XuLyVPHC> map);

    Page<XuLyVPHC> filter(XuLyVPHCQueryModel xuLyVPHCQueryModel, Pageable pageable);

    List<XuLyVPHC> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<XuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<XuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    Map<String, Long> thongKeTrangThaiXuLyVPHC(String[] trangThaiXulyVPHC);
}
