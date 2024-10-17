package com.fds.flex.core.inspectionmgt.rdbms.service.Statistic_Service.impl;

import com.fds.flex.core.inspectionmgt.entity.C_Model.*;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiDoiTuongCNTC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSHoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.RDBMSLoaiDoiTuongCNTCService;
import com.fds.flex.core.inspectionmgt.rdbms.service.Statistic_Service.RDBMSCongTacThanhTraHanhChinhStatisticService;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RDBMSCongTacThanhTraHanhChinhStatisticServiceImpl implements RDBMSCongTacThanhTraHanhChinhStatisticService {
    //@PersistenceContext
    private EntityManager entityManager;

    //@Autowired
    private RDBMSLoaiDoiTuongCNTCService rdbmsLoaiDoiTuongCNTCService;

    @Override
    public Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, count(DISTINCT MaLopDuLieu)\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'LoaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :loaiHoatDongThanhTra_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu) \n" +
                "AND\n" +
                "LopDuLieu = :lopDuLieu\n" +
                "AND (\n" +
                "-- Điều kiện 1 \n" +
                "MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "    OR (TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay))\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien1)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien1 THEN 1 END) > 0\n" +
                ") \n" +
                "-- Điều kiện 2 \n" +
                "OR MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "OR (TruongDuLieu = 'GiaHanThucHien' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay OR GiaTriDuLieuDangSo > :denNgay))\n" +
                "    OR (TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay))\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien2)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'GiaHanThucHien' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay OR GiaTriDuLieuDangSo > :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien2 THEN 1 END) > 0) \n" +
                "-- Điều kiện 3\n" +
                "OR MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "OR (TruongDuLieu = 'GiaHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "    OR (TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay))\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien3)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'GiaHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien3 THEN 1 END) > 0) )\n" +
                "GROUP BY DuLieuThamChieu");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSHoatDongThanhTra.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiHoatDongThanhTra_MaMuc", Arrays.asList(LoaiHoatDongThanhTra.Loai.ThanhTraHanhChinh.getMaMuc(), LoaiHoatDongThanhTra.Loai.KiemTraTrachNhiemThuTruong.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien1", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc()));
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien2", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
        ));
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien3", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc()));

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, count(DISTINCT MaLopDuLieu)\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'LoaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :loaiHoatDongThanhTra_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu) \n" +
                "AND\n" +
                "LopDuLieu = :lopDuLieu\n" +
                "AND (\n" +
                "-- Điều kiện 1 \n" +
                "MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "    OR (TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay))\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien THEN 1 END) > 0\n" +
                "))\n" +
                "GROUP BY DuLieuThamChieu");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSHoatDongThanhTra.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiHoatDongThanhTra_MaMuc", Arrays.asList(LoaiHoatDongThanhTra.Loai.ThanhTraHanhChinh.getMaMuc(), LoaiHoatDongThanhTra.Loai.KiemTraTrachNhiemThuTruong.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien", Arrays.asList(
                TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
        ));


        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, count(DISTINCT MaLopDuLieu)\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'LoaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :loaiHoatDongThanhTra_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'LoaiCheDoThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :loaiCheDoThanhTra)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu) \n" +
                "AND\n" +
                "LopDuLieu = :lopDuLieu\n" +
                "AND (\n" +
                "MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "    OR (TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay))\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien1)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien1 THEN 1 END) > 0\n" +
                ") \n" +
                "OR MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "OR (TruongDuLieu = 'GiaHanThucHien' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay OR GiaTriDuLieuDangSo > :denNgay))\n" +
                "    OR (TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay))\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien2)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'GiaHanThucHien' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay OR GiaTriDuLieuDangSo > :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien2 THEN 1 END) > 0)\n" +
                "OR MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "OR (TruongDuLieu = 'GiaHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "    OR (TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay))\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien3)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'GiaHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien3 THEN 1 END) > 0) \n" +
                "OR MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien4)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien4 THEN 1 END) > 0))\n" +
                "GROUP BY DuLieuThamChieu");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSHoatDongThanhTra.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiCheDoThanhTra", Arrays.asList("01", "02", "03"));
        query.setParameter("loaiHoatDongThanhTra_MaMuc", Arrays.asList(LoaiHoatDongThanhTra.Loai.ThanhTraHanhChinh.getMaMuc(), LoaiHoatDongThanhTra.Loai.KiemTraTrachNhiemThuTruong.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien1", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc()));
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien2", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc()));
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien3", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc()));
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien4", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
        ));

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, count(DISTINCT MaLopDuLieu)\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'LoaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :loaiHoatDongThanhTra_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'LoaiCheDoThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :loaiCheDoThanhTra)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu) \n" +
                "AND\n" +
                "LopDuLieu = :lopDuLieu\n" +
                "AND (\n" +
                "MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "    OR (TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay))\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien1)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien1 THEN 1 END) > 0\n" +
                ") \n" +
                "OR MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "OR (TruongDuLieu = 'GiaHanThucHien' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay OR GiaTriDuLieuDangSo > :denNgay))\n" +
                "    OR (TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay))\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien2)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'GiaHanThucHien' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay OR GiaTriDuLieuDangSo > :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien2 THEN 1 END) > 0)\n" +
                "OR MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "OR (TruongDuLieu = 'GiaHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay)\n" +
                "    OR (TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay))\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien3)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'GiaHanThucHien' AND GiaTriDuLieuDangSo < :tuNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'NgayKetThuc' AND (GiaTriDuLieuDangSo IS NULL OR GiaTriDuLieuDangSo > :denNgay) THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien3 THEN 1 END) > 0) \n" +
                "OR MaLopDuLieu IN (SELECT MaLopDuLieu\n" +
                "FROM BaoCaoTongHop\n" +
                "WHERE \n" +
                "    (TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "    OR (TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien4)\n" +
                "GROUP BY MaLopDuLieu\n" +
                "HAVING \n" +
                "    COUNT(CASE WHEN TruongDuLieu = 'ThoiHanThucHien' AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay THEN 1 END) > 0\n" +
                "    AND COUNT(CASE WHEN TruongDuLieu = 'TrangThaiHoatDongThanhTra.MaMuc' AND GiaTriDuLieuDangChu IN :trangThaiHoatDongThanhTra_MaMuc_DieuKien4 THEN 1 END) > 0))\n" +
                "GROUP BY DuLieuThamChieu");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSHoatDongThanhTra.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiCheDoThanhTra", Collections.singletonList("02"));
        query.setParameter("loaiHoatDongThanhTra_MaMuc", Arrays.asList(LoaiHoatDongThanhTra.Loai.ThanhTraHanhChinh.getMaMuc(), LoaiHoatDongThanhTra.Loai.KiemTraTrachNhiemThuTruong.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien1", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc()));
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien2", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc()));
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien3", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc()));
        query.setParameter("trangThaiHoatDongThanhTra_MaMuc_DieuKien4", Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
        ));

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, \n" +
                "       COUNT(DISTINCT GiaTriDuLieuDangChu) AS CuocThanhTraBHKL\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'NgayVanBan' AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu) \n" +
                "AND\n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'DoiTuongKetLuan.@type' AND GiaTriDuLieuDangChu = :doiTuongKetLuan_Type)\n" +
                "AND \n" +
                "LopDuLieu = :lopDuLieu\n" +
                "AND\n" +
                "TruongDuLieu = 'DoiTuongKetLuan.MaDinhDanh' \n" +

                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("doiTuongKetLuan_Type", DBConstant.T_HOAT_DONG_THANH_TRA);
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, count(DISTINCT GiaTriDuLieuDangChu) DonViDuocThanhTra\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'NgayVanBan' AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu)\n" +
                "AND \n" +
                "LopDuLieu = :lopDuLieu\n" +
                "AND\n" +
                "TruongDuLieu = 'DeXuatKienNghi.DoiTuongXuLy.MaDinhDanh'\n" +
                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, SUM(GiaTriDuLieuDangSo) AS NSNNSoTienThuHoi\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'NgayVanBan' \n" +
                "                AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu = :loaiDeXuatKienNghi_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu)\n" +
                "AND \n" +
                "TruongDuLieu = 'DeXuatKienNghi.SoTienThuHoi' \n" +
                "AND \n" +
                "LopDuLieu = :lopDuLieu\n" +
                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiDeXuatKienNghi_MaMuc", LoaiDeXuatKienNghi.Loai.ThuHoiVeNSNN.getMaMuc());
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, SUM(GiaTriDuLieuDangSo) AS XLVPKTSoTienThuHoi\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'NgayVanBan' \n" +
                "                AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu = :loaiDeXuatKienNghi_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu)\n" +
                "AND \n" +
                "TruongDuLieu = 'DeXuatKienNghi.SoTienThuHoi' \n" +
                "AND \n" +
                "LopDuLieu = :lopDuLieu\n" +
                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiDeXuatKienNghi_MaMuc", LoaiDeXuatKienNghi.Loai.XuLyViPhamVeKinhTe.getMaMuc());
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, SUM(GiaTriDuLieuDangThapPhan) AS NSNNDienTichThuHoi\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'NgayVanBan' \n" +
                "                AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu = :loaiDeXuatKienNghi_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu)\n" +
                "AND \n" +
                "TruongDuLieu = 'DeXuatKienNghi.DienTichThuHoi' \n" +
                "AND \n" +
                "LopDuLieu = :lopDuLieu\n" +
                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiDeXuatKienNghi_MaMuc", LoaiDeXuatKienNghi.Loai.ThuHoiVeNSNN.getMaMuc());
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, \n" +
                "       SUM(GiaTriDuLieuDangThapPhan) AS XLVPKTDienTichThuHoi\n" +
                "\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'NgayVanBan' \n" +
                "                AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu = :loaiDeXuatKienNghi_MaMuc)\t\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu)\n" +
                "AND \n" +
                "TruongDuLieu = 'DeXuatKienNghi.DienTichThuHoi' \n" +
                "AND \n" +
                "LopDuLieu = :lopDuLieu\n" +
                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiDeXuatKienNghi_MaMuc", LoaiDeXuatKienNghi.Loai.XuLyViPhamVeKinhTe.getMaMuc());
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<RDBMSLoaiDoiTuongCNTC> resultLoaiDTCNTC = rdbmsLoaiDoiTuongCNTCService.findByThamChieuMaMucAndTrangThaiDuLieu("04", new String[]{TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()});
        List<String> maMuc = resultLoaiDTCNTC.stream().map(RDBMSLoaiDoiTuongCNTC::getMaMuc).collect(Collectors.toList());
        maMuc.addAll(Arrays.asList("04", "05", "06"));

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, \n" +
                "       COUNT(DISTINCT GiaTriDuLieuDangChu) AS XLHCToChuc\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'NgayVanBan' \n" +
                "                AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu = '03')\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.DoiTuongXuLy.LoaiDoiTuongCNTC.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiDoiTuongCNTC_MaMuc)\n" +
                "                \n" +
                "AND\n" +
                "LopDuLieu = :lopDuLieu\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu)\n" +
                "AND \n" +
                "TruongDuLieu = 'DeXuatKienNghi.DoiTuongXuLy.MaDinhDanh'\n" +
                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("loaiDoiTuongCNTC_MaMuc", maMuc);
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiDeXuatKienNghi_MaMuc", LoaiDeXuatKienNghi.Loai.XuLyViPhamVeKinhTe.getMaMuc());
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, \n" +
                "       COUNT(DISTINCT GiaTriDuLieuDangChu) AS XLHCCaNhan\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'NgayVanBan' \n" +
                "                AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu = :loaiDeXuatKienNghi_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.DoiTuongXuLy.LoaiDoiTuongCNTC.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiDoiTuongCNTC_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu) \n" +
                "AND\n" +
                "LopDuLieu = :lopDuLieu \n" +
                "AND \n" +
                "TruongDuLieu = 'DeXuatKienNghi.DoiTuongXuLy.MaDinhDanh'\n" +
                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("loaiDoiTuongCNTC_MaMuc", Arrays.asList("01", "02", "03"));
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiDeXuatKienNghi_MaMuc", LoaiDeXuatKienNghi.Loai.XuLyHanhChinh.getMaMuc());
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, \n" +
                "       COUNT(DISTINCT GiaTriDuLieuDangChu) AS CQDTVuViec\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'NgayVanBan' \n" +
                "                AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu = :loaiDeXuatKienNghi_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu) \n" +
                "AND\n" +
                "LopDuLieu = :lopDuLieu \n" +
                "AND \n" +
                "TruongDuLieu = 'DeXuatKienNghi.IDKienNghi'\n" +
                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiDeXuatKienNghi_MaMuc", LoaiDeXuatKienNghi.Loai.ChuyenCoQuanDieuTra.getMaMuc());
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, \n" +
                "       COUNT(DISTINCT GiaTriDuLieuDangChu) AS CQDTDoiTuong\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'NgayVanBan' \n" +
                "                AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu = :loaiDeXuatKienNghi_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu) \n" +
                "AND\n" +
                "LopDuLieu = :lopDuLieu \n" +
                "AND \n" +
                "TruongDuLieu = 'DeXuatKienNghi.DoiTuongXuLy.MaDinhDanh'\n" +
                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiDeXuatKienNghi_MaMuc", LoaiDeXuatKienNghi.Loai.ChuyenCoQuanDieuTra.getMaMuc());
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT DuLieuThamChieu, \n" +
                "       COUNT(DISTINCT GiaTriDuLieuDangChu) AS CQDTDoiTuong\n" +
                "FROM BaoCaoTongHop \n" +
                "WHERE \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'CoQuanQuanLy.MaDinhDanh' AND GiaTriDuLieuDangChu IN :coQuanQuanLy)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'LoaiThongBaoKetLuan.MaMuc' \n" +
                "                AND GiaTriDuLieuDangChu IN :loaiThongBaoKetLuan)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'NgayVanBan' \n" +
                "                AND GiaTriDuLieuDangSo BETWEEN :tuNgay AND :denNgay)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop \n" +
                "                WHERE TruongDuLieu = 'DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc'  \n" +
                "                AND GiaTriDuLieuDangChu = :loaiDeXuatKienNghi_MaMuc)\n" +
                "AND \n" +
                "MaLopDuLieu IN (SELECT DISTINCT MaLopDuLieu FROM BaoCaoTongHop WHERE TruongDuLieu = 'TrangThaiDuLieu.MaMuc' AND GiaTriDuLieuDangChu = :trangThaiDuLieu) \n" +
                "AND\n" +
                "LopDuLieu = :lopDuLieu \n" +
                "AND \n" +
                "TruongDuLieu = 'DeXuatKienNghi.IDKienNghi'\n" +
                "GROUP BY DuLieuThamChieu;");

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("coQuanQuanLy", coQuanQuanLy);
        query.setParameter("lopDuLieu", RDBMSThongBaoKetLuan.class.getName());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiDeXuatKienNghi_MaMuc", LoaiDeXuatKienNghi.Loai.HoanThienCoCheChinhSach.getMaMuc());
        query.setParameter("loaiThongBaoKetLuan", Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()));
        query.setParameter("tuNgay", ca_from);
        query.setParameter("denNgay", ca_to);

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maCoQuan = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maCoQuan, count);
        }
        return resultMap;
    }
}
