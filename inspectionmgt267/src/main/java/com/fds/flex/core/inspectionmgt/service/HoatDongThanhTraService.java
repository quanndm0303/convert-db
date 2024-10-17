package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.Query_Model.HoatDongThanhTraQueryModel;
import com.fds.flex.core.inspectionmgt.entity.T_Model.HoatDongThanhTra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HoatDongThanhTraService {
    Optional<HoatDongThanhTra> findById(String id);

    Map<String, Long> thongKeTrangThaiHoatDongThanhTra(String[] loaiHoatDongThanhTra_MaMuc, Integer namThongKe, String loaiCheDoThanhTra_MaMuc, String nhiemVuCongViec_NamKeHoach);

    Map<String, Long> thongKeLoaiHoatDongThanhTra(String[] loaiHoatDongThanhTra_MaMuc, Integer namThongKe);

    void deleteHoatDongThanhTra(HoatDongThanhTra hoatDongThanhTra);

    HoatDongThanhTra updateHoatDongThanhTra(HoatDongThanhTra hoatDongThanhTra);

    Map<String, HoatDongThanhTra> update(Map<String, HoatDongThanhTra> map);

    Page<HoatDongThanhTra> filter(HoatDongThanhTraQueryModel hoatDongThanhTraQueryModel, Pageable pageable);

    List<HoatDongThanhTra> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<HoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<HoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    List<HoatDongThanhTra> findByHSNVMaDinhDanhAndTrangThaiDuLieu(String hsnv_MaDinhDanh, String[] trangThaiDuLieu_MaMuc);

    List<HoatDongThanhTra> findByNVCVMaDinhDanhAndTrangThaiDuLieu(String nvcv_MaDinhDanh, String[] trangThaiDuLieu_MaMuc);
}
