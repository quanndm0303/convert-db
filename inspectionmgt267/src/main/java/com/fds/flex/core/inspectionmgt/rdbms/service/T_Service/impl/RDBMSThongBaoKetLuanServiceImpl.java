package com.fds.flex.core.inspectionmgt.rdbms.service.T_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.DateTimeUtils;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.Query_Model.ThongBaoKetLuanQueryModel;
import com.fds.flex.core.inspectionmgt.publisher.PublisherEvent;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.rdbms.repository.T_Repository.RDBMSThongBaoKetLuanRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.T_Service.RDBMSThongBaoKetLuanService;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.*;


@Service
@Slf4j
public class RDBMSThongBaoKetLuanServiceImpl implements RDBMSThongBaoKetLuanService {
    //@Autowired
    private RDBMSThongBaoKetLuanRepository rdbmsThongBaoKetLuanRepository;
    //@PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Cacheable(value = "ThongBaoKetLuan", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<RDBMSThongBaoKetLuan> findById(String id) {
        return rdbmsThongBaoKetLuanRepository.findById(Long.valueOf(id));
    }

    @Override
    @CacheEvict(value = "ThongBaoKetLuan", allEntries = true)
    public void deleteThongBaoKetLuan(RDBMSThongBaoKetLuan rdbmsThongBaoKetLuan) {
        log.info(LogConstant.deleteById, RDBMSThongBaoKetLuan.class.getSimpleName(), rdbmsThongBaoKetLuan.getPrimKey());
        rdbmsThongBaoKetLuanRepository.delete(rdbmsThongBaoKetLuan);
        PublisherEvent syncBaoCaoEvent = new PublisherEvent("deleteBaoCao", rdbmsThongBaoKetLuan);
        applicationEventPublisher.publishEvent(syncBaoCaoEvent);
    }

    @Override
    public Map<String, Long> thongKeTrangThaiKetLuanTKT(String loaiThongBaoKetLuan_MaMuc, Integer namThongKe) {
        StringBuilder sqlBuilder = new StringBuilder();

        // Base SQL query
        sqlBuilder.append("SELECT JSON_UNQUOTE(JSON_EXTRACT(trangThaiTheoDoi, '$.MaMuc')), COUNT(*) ");
        sqlBuilder.append("FROM" + StringPool.SPACE + DBConstant.T_THONG_BAO_KET_LUAN + StringPool.SPACE);

        // Where clause - Initial condition on 'TrangThaiDuLieu'
        List<String> params = new ArrayList<>();
        List<String> whereConditions = new ArrayList<>();
        whereConditions.add("JSON_UNQUOTE(JSON_EXTRACT(trangThaiDuLieu, '$.MaMuc')) = " + TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());

        if (Validator.isNotNull(loaiThongBaoKetLuan_MaMuc)) {
            whereConditions.add("JSON_UNQUOTE(JSON_EXTRACT(loaiThongBaoKetLuan, '$.MaMuc')) = " + loaiThongBaoKetLuan_MaMuc);
        }
        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);
            whereConditions.add("NgayTheoDoi BETWEEN " + ca_from.getTimeInMillis() + " AND " + ca_to.getTimeInMillis());
        }

        // Phân quyền - Authorization logic
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_THONG_BAO_KET_LUAN)) {

            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_THONG_BAO_KET_LUAN, new com.fds.flex.context.model.Service.TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();

            List<String> listPhanQuyen = new ArrayList<>();
            if (taiNguyenHeThong.isHanCheBanGhi()) {
                listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(nguoiTaoLap, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getMaDinhDanh());
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(canBoTheoDoi, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh());
                    listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(thanhVienDoan, '$.CanBo.MaDinhDanh')) = " + user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh());
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(coQuanBanHanh, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh());
                    listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(coQuanQuanLy, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh());
                    listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(donViChuTri, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh());
                }
            }

            if (taiNguyenHeThong.isHanChePhanVung()) {
                listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(phanVungDuLieu, '$.MaMuc')) IN :maPhanVungList");
                params.add("maPhanVungList");
            }
            if (!listPhanQuyen.isEmpty()) {
                whereConditions.add(String.join(" OR ", whereConditions));
            }
        }

        // Add WHERE clause if conditions are present
        if (!whereConditions.isEmpty()) {
            sqlBuilder.append("WHERE ");
            sqlBuilder.append(String.join(" AND ", whereConditions));
        }

        // Group by 'MaMuc'
        sqlBuilder.append(" GROUP BY JSON_UNQUOTE(JSON_EXTRACT(trangThaiHoatDongThanhTra, '$.MaMuc'))");

        // Create and set parameters for the native query
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        if (params.contains("maPhanVungList")) {
            User user = UserContextHolder.getContext().getUser();
            query.setParameter("maPhanVungList", user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst());
        }

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        long total = 0L;
        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maMuc = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maMuc, count);
            total += count;
        }

        resultMap.put(Constant._ALL, total);
        return resultMap;
    }

    @Override
    public Map<String, Long> thongKeLoaiDeXuatKienNghi(String loaiThongBaoKetLuan_MaMuc, Integer namThongKe) {
        StringBuilder sqlBuilder = new StringBuilder();

        // Base SQL query
        sqlBuilder.append("SELECT JSON_UNQUOTE(JSON_EXTRACT(deXuatKienNghi, '$.LoaiDeXuatKienNghi.MaMuc')), COUNT(*) ");
        sqlBuilder.append("FROM" + StringPool.SPACE + DBConstant.T_THONG_BAO_KET_LUAN + StringPool.SPACE);

        // Where clause - Initial condition on 'TrangThaiDuLieu'
        List<String> params = new ArrayList<>();
        List<String> whereConditions = new ArrayList<>();
        whereConditions.add("JSON_UNQUOTE(JSON_EXTRACT(trangThaiDuLieu, '$.MaMuc')) = " + TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());

        if (Validator.isNotNull(loaiThongBaoKetLuan_MaMuc)) {
            whereConditions.add("JSON_UNQUOTE(JSON_EXTRACT(loaiThongBaoKetLuan, '$.MaMuc')) = " + loaiThongBaoKetLuan_MaMuc);
        }
        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);
            whereConditions.add("NgayTheoDoi BETWEEN " + ca_from.getTimeInMillis() + " AND " + ca_to.getTimeInMillis());
        }

        // Phân quyền - Authorization logic
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_THONG_BAO_KET_LUAN)) {

            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_THONG_BAO_KET_LUAN, new com.fds.flex.context.model.Service.TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();

            List<String> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanCheBanGhi()) {
                listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(nguoiTaoLap, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getMaDinhDanh());
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(canBoTheoDoi, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh());
                    listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(thanhVienDoan, '$.CanBo.MaDinhDanh')) = " + user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh());
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(coQuanBanHanh, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh());
                    listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(coQuanQuanLy, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh());
                    listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(donViChuTri, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh());
                }
            }

            if (taiNguyenHeThong.isHanChePhanVung()) {
                listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(phanVungDuLieu, '$.MaMuc')) IN :maPhanVungList");
                params.add("maPhanVungList");
            }
            if (!listPhanQuyen.isEmpty()) {
                whereConditions.add(String.join(" OR ", whereConditions));
            }
        }

        // Add WHERE clause if conditions are present
        if (!whereConditions.isEmpty()) {
            sqlBuilder.append("WHERE ");
            sqlBuilder.append(String.join(" AND ", whereConditions));
        }

        // Group by 'MaMuc'
        sqlBuilder.append(" GROUP BY JSON_UNQUOTE(JSON_EXTRACT(deXuatKienNghi, '$.LoaiDeXuatKienNghi.MaMuc'))");

        // Create and set parameters for the native query
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        if (params.contains("maPhanVungList")) {
            User user = UserContextHolder.getContext().getUser();
            query.setParameter("maPhanVungList", user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst());
        }

        // Execute the query and process results
        List<Object[]> results = query.getResultList();

        long total = 0L;
        Map<String, Long> resultMap = new HashMap<>();

        for (Object[] result : results) {
            String maMuc = (String) result[0];
            long count = ((Number) result[1]).longValue();
            resultMap.put(maMuc, count);
            total += count;
        }

        resultMap.put(Constant._ALL, total);
        return resultMap;
    }


    @Override
    @CacheEvict(value = "ThongBaoKetLuan", allEntries = true)
    public RDBMSThongBaoKetLuan updateThongBaoKetLuan(RDBMSThongBaoKetLuan rdbmsThongBaoKetLuan) {
        log.info(LogConstant.updateById, RDBMSThongBaoKetLuan.class.getSimpleName(), rdbmsThongBaoKetLuan.getPrimKey());
        rdbmsThongBaoKetLuan = rdbmsThongBaoKetLuanRepository.saveAndFlush(rdbmsThongBaoKetLuan);
        PublisherEvent syncBaoCaoEvent = new PublisherEvent("syncBaoCao", rdbmsThongBaoKetLuan);
        applicationEventPublisher.publishEvent(syncBaoCaoEvent);

        return rdbmsThongBaoKetLuan;
    }

    @Override
    @CacheEvict(value = "ThongBaoKetLuan", allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, RDBMSThongBaoKetLuan> update(Map<String, RDBMSThongBaoKetLuan> map) {
        log.info(LogConstant.updateByMap, RDBMSThongBaoKetLuan.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsThongBaoKetLuanRepository.delete(v);
                PublisherEvent syncBaoCaoEvent = new PublisherEvent("deleteBaoCao", v);
                applicationEventPublisher.publishEvent(syncBaoCaoEvent);
            } else {
                rdbmsThongBaoKetLuanRepository.saveAndFlush(v);
                PublisherEvent syncBaoCaoEvent = new PublisherEvent("syncBaoCao", v);
                applicationEventPublisher.publishEvent(syncBaoCaoEvent);
            }
        });

        return map;
    }

    @Override
    public Page<RDBMSThongBaoKetLuan> filter(ThongBaoKetLuanQueryModel thongBaoKetLuanQueryModel, Pageable pageable) {


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSThongBaoKetLuan> thongBaoKetLuanCriteriaQuery = criteriaBuilder.createQuery(RDBMSThongBaoKetLuan.class);
        Root<RDBMSThongBaoKetLuan> root = thongBaoKetLuanCriteriaQuery.from(RDBMSThongBaoKetLuan.class);
        List<Predicate> conditionList = new ArrayList<>();
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getKeyword())) {
            List<Predicate> orPredicate = new ArrayList<>();
            String keyword = thongBaoKetLuanQueryModel.getKeyword();
            Predicate condition = criteriaBuilder.like(root.get("soHieuVanBan"), "%" + keyword + "%");
            orPredicate.add(condition);
            condition = criteriaBuilder.like(root.get("trichYeuVanBan"), "%" + keyword + "%");
            orPredicate.add(condition);
            conditionList.add(criteriaBuilder.or(orPredicate.toArray(new Predicate[0])));
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getDoiTuongKetLuan_MaDinhDanh())) {
            Predicate predicate = criteriaBuilder.and(root.get("doiTuongKetLuan").isNotNull(),
                    criteriaBuilder.equal(
                            criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("doiTuongKetLuan"), criteriaBuilder.literal("$.MaDinhDanh")),
                            thongBaoKetLuanQueryModel.getDonViChuTri_MaDinhDanh()));
            conditionList.add(predicate);
        }

        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getDonViChuTri_MaDinhDanh())) {
            Predicate predicate = criteriaBuilder.and(root.get("donViChuTri").isNotNull(),
                    criteriaBuilder.equal(
                            criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("donViChuTri"), criteriaBuilder.literal("$.MaDinhDanh")),
                            thongBaoKetLuanQueryModel.getDonViChuTri_MaDinhDanh()));
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getCoQuanBanHanh_MaDinhDanh())) {
            Predicate predicate = criteriaBuilder.and(root.get("coQuanBanHanh").isNotNull(),
                    criteriaBuilder.equal(
                            criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("coQuanBanHanh"), criteriaBuilder.literal("$.MaDinhDanh")),
                            thongBaoKetLuanQueryModel.getDonViChuTri_MaDinhDanh()));
            conditionList.add(predicate);
        }

        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getNgayVanBan_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(thongBaoKetLuanQueryModel.getNgayVanBan_TuNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("ngayVanBan"), ca.getTime());
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getNgayVanBan_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(thongBaoKetLuanQueryModel.getNgayVanBan_DenNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("ngayVanBan"), ca.getTime());
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getNgayVanBan())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(thongBaoKetLuanQueryModel.getNgayVanBan(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            Calendar ca2 = Calendar.getInstance();
            ca2.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            ca2.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca2.set(Calendar.MILLISECOND, 999);
            Predicate predicate = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("ngayVanBan"), ca.getTime()), criteriaBuilder.lessThanOrEqualTo(root.get("ngayVanBan"), ca2.getTime()));
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getCanBoTheoDoi_MaDinhDanh())) {
            Predicate predicate = criteriaBuilder.and(root.get("canBoTheoDoi").isNotNull(),
                    criteriaBuilder.equal(
                            criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("canBoTheoDoi"), criteriaBuilder.literal("$.MaDinhDanh")),
                            thongBaoKetLuanQueryModel.getDonViChuTri_MaDinhDanh()));
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getLoaiThongBaoKetLuan_MaMuc())
                && thongBaoKetLuanQueryModel.getLoaiThongBaoKetLuan_MaMuc().length > 0) {
            Predicate condition = root.get("loaiThongBaoKetLuan").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("loaiThongBaoKetLuan"),
                    criteriaBuilder.literal("$.MaMuc")).in(Arrays.asList(thongBaoKetLuanQueryModel.getLoaiThongBaoKetLuan_MaMuc())));
            conditionList.add(condition);
        }

        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getTrangThaiTheoDoi_MaMuc())
                && thongBaoKetLuanQueryModel.getTrangThaiTheoDoi_MaMuc().length > 0) {
            Predicate condition = root.get("trangThaiTheoDoi").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("trangThaiTheoDoi"),
                    criteriaBuilder.literal("$.MaMuc")).in(Arrays.asList(thongBaoKetLuanQueryModel.getTrangThaiTheoDoi_MaMuc())));
            conditionList.add(condition);
        }

        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getTrangThaiDuLieu_MaMuc())
                && thongBaoKetLuanQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Predicate condition = root.get("trangThaiDuLieu").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("trangThaiDuLieu"),
                    criteriaBuilder.literal("$.MaMuc")).in(Arrays.asList(thongBaoKetLuanQueryModel.getTrangThaiDuLieu_MaMuc())));
            conditionList.add(condition);
        }

        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_THONG_BAO_KET_LUAN)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_THONG_BAO_KET_LUAN, new com.fds.flex.context.model.Service.TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Predicate> listPhanQuyen = new ArrayList<>();

            if (Validator.isNotNull(taiNguyenHeThong)) {
                if (taiNguyenHeThong.isHanChePhanVung()) {
                    Predicate condition = root.get("phanVungDuLieu").isNotNull();
                    condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("phanVungDuLieu"),
                            criteriaBuilder.literal("$.MaMuc")).in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
                    listPhanQuyen.add(condition);
                }

                if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {

                    Predicate condition = root.get("nguoiTaoLap").isNotNull();
                    condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                            criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("nguoiTaoLap"),
                                    criteriaBuilder.literal("$.MaDinhDanh")), user.getDanhTinhDienTu().getMaDinhDanh()));
                    listPhanQuyen.add(condition);

                    if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {

                        condition = root.get("canBoTheoDoi").isNotNull();
                        condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                                criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("canBoTheoDoi"),
                                        criteriaBuilder.literal("$.MaDinhDanh")), user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                        listPhanQuyen.add(condition);

                    } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                            || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {

                        condition = root.get("coQuanBanHanh").isNotNull();
                        condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                                criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("coQuanBanHanh"),
                                        criteriaBuilder.literal("$.MaDinhDanh")), user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                        listPhanQuyen.add(condition);

                        condition = root.get("coQuanQuanLy").isNotNull();
                        condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                                criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("coQuanQuanLy"),
                                        criteriaBuilder.literal("$.MaDinhDanh")), user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                        listPhanQuyen.add(condition);

                        condition = root.get("donViChuTri").isNotNull();
                        condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                                criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("donViChuTri"),
                                        criteriaBuilder.literal("$.MaDinhDanh")), user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                        listPhanQuyen.add(condition);
                    }
                }
            }
            if (!listPhanQuyen.isEmpty()) {
                conditionList.add(criteriaBuilder.or(listPhanQuyen.toArray(new Predicate[0])));
            }
        }

        if (!conditionList.isEmpty()) {
            thongBaoKetLuanCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSThongBaoKetLuan> query = entityManager.createQuery(thongBaoKetLuanCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        thongBaoKetLuanCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSThongBaoKetLuan.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSThongBaoKetLuan> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsThongBaoKetLuanRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSThongBaoKetLuan> findByDTKT_TTDL(String doanThanhKiemTra, String trangThaiDuLieu_MaMuc) {
        return rdbmsThongBaoKetLuanRepository.findByDTKT_TTDL(doanThanhKiemTra, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSThongBaoKetLuan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh,
                                                                             String trangThaiDuLieu_MaMuc) {
        return rdbmsThongBaoKetLuanRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSThongBaoKetLuan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsThongBaoKetLuanRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }
}
