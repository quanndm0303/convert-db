package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.Query_Model.KhaoSatNamTinhHinhQueryModel;
import com.fds.flex.core.inspectionmgt.entity.T_Model.KhaoSatNamTinhHinh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface KhaoSatNamTinhHinhService {
    Optional<KhaoSatNamTinhHinh> findById(String id);

    void deleteKhaoSatNamTinhHinh(KhaoSatNamTinhHinh khaoSatNamTinhHinh);

    KhaoSatNamTinhHinh updateKhaoSatNamTinhHinh(KhaoSatNamTinhHinh khaoSatNamTinhHinh);

    Map<String, KhaoSatNamTinhHinh> update(Map<String, KhaoSatNamTinhHinh> map);

    Page<KhaoSatNamTinhHinh> filter(KhaoSatNamTinhHinhQueryModel khaoSatNamTinhHinhQueryModel, Pageable pageable);

    List<KhaoSatNamTinhHinh> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<KhaoSatNamTinhHinh> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<KhaoSatNamTinhHinh> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    Map<String, Long> thongKeTrangThaiKhaoSat(String loaiCheDoThanhTra_MaMuc, String nhiemVuCongViec_NamKeHoach, String namVanBan);
}
