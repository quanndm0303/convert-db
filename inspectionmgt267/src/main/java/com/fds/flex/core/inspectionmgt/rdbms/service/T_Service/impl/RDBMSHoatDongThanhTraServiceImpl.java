package com.fds.flex.core.inspectionmgt.rdbms.service.T_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.*;
import com.fds.flex.core.inspectionmgt.entity.Query_Model.HoatDongThanhTraQueryModel;
import com.fds.flex.core.inspectionmgt.entity.Statistic_Model.HDTT_LichCongTacDoan_Statistic;
import com.fds.flex.core.inspectionmgt.entity.T_Model.HoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.publisher.PublisherEvent;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSBienPhapKhacPhuc;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSHoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.repository.T_Repository.RDBMSHoatDongThanhTraRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.T_Service.RDBMSHoatDongThanhTraService;
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
import java.util.*;

@Slf4j
@Service
public class RDBMSHoatDongThanhTraServiceImpl implements RDBMSHoatDongThanhTraService {
    //@Autowired
    private RDBMSHoatDongThanhTraRepository rdbmsHoatDongThanhTraRepository;
    //@PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Cacheable(value = "HoatDongThanhTra", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<RDBMSHoatDongThanhTra> findById(String id) {
        log.info(LogConstant.findById, HoatDongThanhTra.class.getSimpleName(), id);
        return rdbmsHoatDongThanhTraRepository.findById(Long.valueOf(id));

    }

    @Override
    public Map<String, Long> thongKeTrangThaiHoatDongThanhTra(String[] loaiHoatDongThanhTra_MaMuc, Integer namThongKe, String loaiCheDoThanhTra_MaMuc, String nhiemVuCongViec_NamKeHoach) {

        StringBuilder sqlBuilder = new StringBuilder();

        // Base SQL query
        sqlBuilder.append("SELECT JSON_UNQUOTE(JSON_EXTRACT(trangThaiHoatDongThanhTra, '$.MaMuc')), COUNT(*) ");
        sqlBuilder.append("FROM" + StringPool.SPACE + DBConstant.T_HOAT_DONG_THANH_TRA + StringPool.SPACE);

        // Where clause - Initial condition on 'TrangThaiDuLieu'
        List<String> params = new ArrayList<>();
        List<String> whereConditions = new ArrayList<>();
        whereConditions.add("JSON_UNQUOTE(JSON_EXTRACT(trangThaiDuLieu, '$.MaMuc')) = :trangThaiDuLieu");

        // Phân quyền - Authorization logic
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_HOAT_DONG_THANH_TRA)) {

            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_HOAT_DONG_THANH_TRA, new com.fds.flex.context.model.Service.TaiNguyenHeThong());
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

        if (!results.isEmpty()) {
            if (Validator.isNotNull(loaiCheDoThanhTra_MaMuc)
                    && loaiCheDoThanhTra_MaMuc.equals(LoaiCheDoThanhTra.Loai.DotXuat.getMaMuc())
                    && Validator.isNotNull(loaiHoatDongThanhTra_MaMuc)
                    && (Arrays.asList(loaiHoatDongThanhTra_MaMuc).contains(LoaiHoatDongThanhTra.Loai.ThanhTraHanhChinh.getMaMuc())
                    || (Arrays.asList(loaiHoatDongThanhTra_MaMuc).contains(LoaiHoatDongThanhTra.Loai.ThanhTraChuyenNganh.getMaMuc()))
                    || (Arrays.asList(loaiHoatDongThanhTra_MaMuc).contains(LoaiHoatDongThanhTra.Loai.KiemTraTrachNhiemThuTruong.getMaMuc()))
                    || (Arrays.asList(loaiHoatDongThanhTra_MaMuc).contains(LoaiHoatDongThanhTra.Loai.KiemTraPhongChongThamNhung.getMaMuc()))
            )) {

                for (Object[] result : results) {
                    String maMuc = (String) result[0];
                    long count = ((Number) result[1]).longValue();
                    if (!maMuc.equals(TrangThaiHoatDongThanhTra.TrangThai.ChuaTrienKhai.getMaMuc())) {
                        resultMap.put(maMuc, count);
                        total += count;
                    }
                }

            } else {
                for (Object[] result : results) {
                    String maMuc = (String) result[0];
                    long count = ((Number) result[1]).longValue();
                    resultMap.put(maMuc, count);
                    total += count;
                }
            }
        }
        resultMap.put(Constant._ALL, total);
        return resultMap;
    }

    @Override
    @CacheEvict(value = "HoatDongThanhTra", allEntries = true)
    public void deleteHoatDongThanhTra(RDBMSHoatDongThanhTra rdbmsHoatDongThanhTra) {
        log.info(LogConstant.deleteById, RDBMSHoatDongThanhTra.class.getSimpleName(), rdbmsHoatDongThanhTra.getPrimKey());
        rdbmsHoatDongThanhTraRepository.delete(rdbmsHoatDongThanhTra);
        PublisherEvent syncBaoCaoEvent = new PublisherEvent("deleteBaoCao", rdbmsHoatDongThanhTra);
        applicationEventPublisher.publishEvent(syncBaoCaoEvent);
    }

    @Override
    @CacheEvict(value = "HoatDongThanhTra", allEntries = true)
    public RDBMSHoatDongThanhTra updateHoatDongThanhTra(RDBMSHoatDongThanhTra rdbmsHoatDongThanhTra) {
        log.info(LogConstant.updateById, RDBMSHoatDongThanhTra.class.getSimpleName(), rdbmsHoatDongThanhTra.getPrimKey());
        rdbmsHoatDongThanhTra = rdbmsHoatDongThanhTraRepository.saveAndFlush(rdbmsHoatDongThanhTra);
        PublisherEvent syncBaoCaoEvent = new PublisherEvent("syncBaoCao", rdbmsHoatDongThanhTra);
        applicationEventPublisher.publishEvent(syncBaoCaoEvent);
        return rdbmsHoatDongThanhTra;
    }

    @Override
    @CacheEvict(value = "HoatDongThanhTra", allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, RDBMSHoatDongThanhTra> update(Map<String, RDBMSHoatDongThanhTra> map) {
        log.info(LogConstant.updateByMap, RDBMSHoatDongThanhTra.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsHoatDongThanhTraRepository.delete(v);
            } else {
                rdbmsHoatDongThanhTraRepository.saveAndFlush(v);
                PublisherEvent syncBaoCaoEvent = new PublisherEvent("syncBaoCao", v);
                applicationEventPublisher.publishEvent(syncBaoCaoEvent);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSHoatDongThanhTra> filter(HoatDongThanhTraQueryModel hoatDongThanhTraQueryModel, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSHoatDongThanhTra> hoatDongThanhTraCriteriaQuery = criteriaBuilder.createQuery(RDBMSHoatDongThanhTra.class);
        Root<RDBMSHoatDongThanhTra> root = hoatDongThanhTraCriteriaQuery.from(RDBMSHoatDongThanhTra.class);
        List<Predicate> conditionList = new ArrayList<>();
        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getKeyword())) {
            List<Predicate> orPredicate = new ArrayList<>();
            Predicate condition = criteriaBuilder.like(root.get("soQuyetDinh"), "%" + hoatDongThanhTraQueryModel.getKeyword() + "%");
            orPredicate.add(condition);
            condition = criteriaBuilder.like(root.get("tenGoi"), "%" + hoatDongThanhTraQueryModel.getKeyword() + "%");
            orPredicate.add(condition);
            condition = criteriaBuilder.like(root.get("tenHoatDong"), "%" + hoatDongThanhTraQueryModel.getKeyword() + "%");
            orPredicate.add(condition);
            conditionList.add(criteriaBuilder.or(orPredicate.toArray(new Predicate[0])));
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getLoaiHoatDongThanhTra_MaMuc())
                && hoatDongThanhTraQueryModel.getLoaiHoatDongThanhTra_MaMuc().length > 0) {
            Predicate condition = root.get("loaiHoatDongThanhTra").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("loaiHoatDongThanhTra"),
                    criteriaBuilder.literal("$.MaMuc")).in(Arrays.asList(hoatDongThanhTraQueryModel.getLoaiHoatDongThanhTra_MaMuc())));
            conditionList.add(condition);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getHoSoNghiepVu_SoDangKy())) {
            Predicate condition = root.get("hoSoNghiepVu").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("hoSoNghiepVu"), criteriaBuilder.literal("$.SoDangKy")), hoatDongThanhTraQueryModel.getHoSoNghiepVu_SoDangKy()));
            conditionList.add(condition);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getHoSoNghiepVu_TrangThaiHoSoNghiepVu_MaMuc())) {
            Predicate condition = root.get("hoSoNghiepVu").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("hoSoNghiepVu"),
                            criteriaBuilder.literal("$.TrangThaiHoSoNghiepVu.MaMuc")), hoatDongThanhTraQueryModel.getHoSoNghiepVu_SoDangKy()));
            conditionList.add(condition);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getTrangThaiHoatDongThanhTra_MaMuc())) {
            Predicate condition = root.get("trangThaiHoatDongThanhTra").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("trangThaiHoatDongThanhTra"),
                            criteriaBuilder.literal("$.MaMuc")), hoatDongThanhTraQueryModel.getTrangThaiHoatDongThanhTra_MaMuc()));
            conditionList.add(condition);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getCoQuanBanHanh())) {
            Predicate condition = root.get("coQuanBanHanh").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("coQuanBanHanh"),
                            criteriaBuilder.literal("$.MaDinhDanh")), hoatDongThanhTraQueryModel.getCoQuanBanHanh()));
            conditionList.add(condition);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getTruongDoan())) {
            Predicate condition = root.get("thanhVienDoan").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("thanhVienDoan"),
                            criteriaBuilder.literal("$.CanBo.HoVaTen")), "%" + hoatDongThanhTraQueryModel.getTruongDoan() + "%"));
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("thanhVienDoan"),
                            criteriaBuilder.literal("$.ChucDanhDoan.MaMuc")), ChucDanhDoan.ChucDanh.TruongDoan.getMaMuc()));
            conditionList.add(condition);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getTrangThaiDuLieu_MaMuc()) && hoatDongThanhTraQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Predicate condition = root.get("trangThaiDuLieu").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("trangThaiDuLieu"),
                    criteriaBuilder.literal("$.MaMuc")).in(Arrays.asList(hoatDongThanhTraQueryModel.getTrangThaiDuLieu_MaMuc())));
            conditionList.add(condition);
        }


        //Phân quyền
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_HOAT_DONG_THANH_TRA)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_HOAT_DONG_THANH_TRA, new com.fds.flex.context.model.Service.TaiNguyenHeThong());
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

                        condition = root.get("thanhVienDoan").isNotNull();
                        condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                                criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("thanhVienDoan"),
                                        criteriaBuilder.literal("$.CanBo.MaDinhDanh")), user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
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
            hoatDongThanhTraCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSHoatDongThanhTra> query = entityManager.createQuery(hoatDongThanhTraCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        hoatDongThanhTraCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSBienPhapKhacPhuc.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSHoatDongThanhTra> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsHoatDongThanhTraRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSHoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh,
                                                                              String trangThaiDuLieu_MaMuc) {
        return rdbmsHoatDongThanhTraRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSHoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh,
                                                                          String[] trangThaiDuLieu_MaMuc) {
        return rdbmsHoatDongThanhTraRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSHoatDongThanhTra> findByHSNVMaDinhDanhAndTrangThaiDuLieu(String hsnv_MaDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsHoatDongThanhTraRepository.findByHSNVMaDinhDanhAndTrangThaiDuLieu(hsnv_MaDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<HDTT_LichCongTacDoan_Statistic> thongKeLichCongTacDoan(String ngayCongTac) {
        return null;
    }

    @Override
    public List<String> thongKeLichCongTacDoanTheoThang(String namThongKe, String tuNgay, String denNgay) {
        return Collections.emptyList();
    }


    @Override
    public Map<String, Long> thongKeLoaiHoatDongThanhTra(String[] loaiHoatDongThanhTra_MaMuc, Integer namThongKe) {

        StringBuilder sqlBuilder = new StringBuilder();

        // Base SQL query
        sqlBuilder.append("SELECT JSON_UNQUOTE(JSON_EXTRACT(loaiHoatDongThanhTra, '$.MaMuc')), COUNT(*) ");
        sqlBuilder.append("FROM" + StringPool.SPACE + DBConstant.T_HOAT_DONG_THANH_TRA + StringPool.SPACE);

        // Where clause - Initial condition on 'TrangThaiDuLieu'
        List<String> params = new ArrayList<>();
        List<String> whereConditions = new ArrayList<>();
        whereConditions.add("JSON_UNQUOTE(JSON_EXTRACT(trangThaiDuLieu, '$.MaMuc')) = :trangThaiDuLieu");

        if (Validator.isNotNull(loaiHoatDongThanhTra_MaMuc) && loaiHoatDongThanhTra_MaMuc.length > 0) {
            whereConditions.add("JSON_UNQUOTE(JSON_EXTRACT(loaiHoatDongThanhTra, '$.MaMuc')) IN :loaiHoatDongThanhTra_MaMuc");
        }

        // Phân quyền - Authorization logic
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_HOAT_DONG_THANH_TRA)) {

            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_HOAT_DONG_THANH_TRA, new com.fds.flex.context.model.Service.TaiNguyenHeThong());
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
        sqlBuilder.append(" GROUP BY JSON_UNQUOTE(JSON_EXTRACT(loaiHoatDongThanhTra, '$.MaMuc'))");

        // Create and set parameters for the native query
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("loaiHoatDongThanhTra_MaMuc", Arrays.asList(loaiHoatDongThanhTra_MaMuc));
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
}
