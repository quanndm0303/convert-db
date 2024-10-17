package com.fds.flex.core.inspectionmgt.rdbms.service.T_Service;

import com.fds.flex.core.inspectionmgt.entity.Query_Model.XuLyVPHCQueryModel;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSXuLyVPHC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSXuLyVPHCService {

    Optional<RDBMSXuLyVPHC> findById(String id);

    void deleteXuLyVPHC(RDBMSXuLyVPHC xuLyVPHC);

    RDBMSXuLyVPHC updateXuLyVPHC(RDBMSXuLyVPHC xuLyVPHC);

    List<RDBMSXuLyVPHC> findBySoBienBanVPHCAndTrangThaiDuLieu(String soBienBanVPHC);

    List<RDBMSXuLyVPHC> findBySoQuyetDinhAndTrangThaiDuLieu(String soQuyetDinh);

    Map<String, RDBMSXuLyVPHC> update(Map<String, RDBMSXuLyVPHC> map);

    Page<RDBMSXuLyVPHC> filter(XuLyVPHCQueryModel xuLyVPHCQueryModel, Pageable pageable);

    List<RDBMSXuLyVPHC> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSXuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<RDBMSXuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    Map<String, Long> thongKeTrangThaiXuLyVPHC(String[] trangThaiXulyVPHC);
}
