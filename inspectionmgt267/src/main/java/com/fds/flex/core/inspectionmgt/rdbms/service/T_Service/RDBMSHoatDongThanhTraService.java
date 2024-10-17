package com.fds.flex.core.inspectionmgt.rdbms.service.T_Service;

import com.fds.flex.core.inspectionmgt.entity.Query_Model.HoatDongThanhTraQueryModel;
import com.fds.flex.core.inspectionmgt.entity.Statistic_Model.HDTT_LichCongTacDoan_Statistic;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSHoatDongThanhTra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSHoatDongThanhTraService {

    Optional<RDBMSHoatDongThanhTra> findById(String id);

    Map<String, Long> thongKeTrangThaiHoatDongThanhTra(String[] loaiHoatDongThanhTra_MaMuc, Integer namThongKe, String loaiCheDoThanhTra_MaMuc, String nhiemVuCongViec_NamKeHoach);


    void deleteHoatDongThanhTra(RDBMSHoatDongThanhTra rdbmsHoatDongThanhTra);

    RDBMSHoatDongThanhTra updateHoatDongThanhTra(RDBMSHoatDongThanhTra rdbmsHoatDongThanhTra);

    Map<String, RDBMSHoatDongThanhTra> update(Map<String, RDBMSHoatDongThanhTra> map);

    Page<RDBMSHoatDongThanhTra> filter(HoatDongThanhTraQueryModel hoatDongThanhTraQueryModel, Pageable pageable);

    List<RDBMSHoatDongThanhTra> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSHoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    List<RDBMSHoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    List<RDBMSHoatDongThanhTra> findByHSNVMaDinhDanhAndTrangThaiDuLieu(String hsnv_MaDinhDanh, String[] trangThaiDuLieu_MaMuc);

    List<HDTT_LichCongTacDoan_Statistic> thongKeLichCongTacDoan(String ngayCongTac);

    List<String> thongKeLichCongTacDoanTheoThang(String namThongKe, String tuNgay, String denNgay);

    Map<String, Long> thongKeLoaiHoatDongThanhTra(String[] loaiHoatDongThanhTra_MaMuc, Integer namThongKe);
}
