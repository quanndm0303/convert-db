package com.fds.flex.core.inspectionmgt.rdbms.service.T_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.DateTimeUtils;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.entity.Query_Model.XuLyVPHCQueryModel;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSXuLyVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.repository.T_Repository.RDBMSXuLyVPHCRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.T_Service.RDBMSXuLyVPHCService;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class RDBMSXuLyVPHCServiceImpl implements RDBMSXuLyVPHCService {

    //@Autowired
    private RDBMSXuLyVPHCRepository rdbmsXuLyVPHCRepository;
    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    @Cacheable(value = "XuLyVPHC", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<RDBMSXuLyVPHC> findById(String id) {
        log.info(LogConstant.findById, RDBMSXuLyVPHC.class.getSimpleName(), id);
        return rdbmsXuLyVPHCRepository.findById(Long.valueOf(id));
    }

    @Override
    @CacheEvict(value = "XuLyVPHC", allEntries = true)
    public void deleteXuLyVPHC(RDBMSXuLyVPHC rdbmsXuLyVPHC) {
        log.info(LogConstant.deleteById, RDBMSXuLyVPHC.class.getSimpleName(), rdbmsXuLyVPHC.getPrimKey());
        rdbmsXuLyVPHCRepository.delete(rdbmsXuLyVPHC);
    }

    @Override
    @CacheEvict(value = "XuLyVPHC", allEntries = true)
    public RDBMSXuLyVPHC updateXuLyVPHC(RDBMSXuLyVPHC rdbmsXuLyVPHC) {
        log.info(LogConstant.updateById, RDBMSXuLyVPHC.class.getSimpleName(), rdbmsXuLyVPHC.getPrimKey());
        return rdbmsXuLyVPHCRepository.saveAndFlush(rdbmsXuLyVPHC);
    }

    @Override
    public List<RDBMSXuLyVPHC> findBySoBienBanVPHCAndTrangThaiDuLieu(String soBienBanVPHC) {
        return rdbmsXuLyVPHCRepository.findBySoBienBanVPHCAndTrangThaiDuLieu(soBienBanVPHC, TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
    }

    @Override
    public List<RDBMSXuLyVPHC> findBySoQuyetDinhAndTrangThaiDuLieu(String soQuyetDinh) {
        return rdbmsXuLyVPHCRepository.findBySoQuyetDinhAndTrangThaiDuLieu(soQuyetDinh, TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
    }

    @Override
    @CacheEvict(value = "XuLyVPHC", allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, RDBMSXuLyVPHC> update(Map<String, RDBMSXuLyVPHC> map) {
        log.info(LogConstant.updateByMap, RDBMSXuLyVPHC.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsXuLyVPHCRepository.delete(v);
            } else {
                rdbmsXuLyVPHCRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSXuLyVPHC> filter(XuLyVPHCQueryModel xuLyVPHCQueryModel, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSXuLyVPHC> xuLyVPHCCriteriaQuery = criteriaBuilder.createQuery(RDBMSXuLyVPHC.class);
        Root<RDBMSXuLyVPHC> root = xuLyVPHCCriteriaQuery.from(RDBMSXuLyVPHC.class);
        List<Predicate> conditionList = new ArrayList<>();
        if (Validator.isNotNull(xuLyVPHCQueryModel.getKeyword())) {

            String keyword = xuLyVPHCQueryModel.getKeyword();

            List<Predicate> orPredicate = new ArrayList<>();
            Predicate condition = criteriaBuilder.like(root.get("soQuyetDinh"), "%" + keyword + "%");
            orPredicate.add(condition);

            condition = criteriaBuilder.like(root.get("soBienBanVPHC"), "%" + keyword + "%");
            orPredicate.add(condition);

            condition = root.get("doiTuongVPHC").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("doiTuongVPHC"),
                            criteriaBuilder.literal("$.TenGoi")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = root.get("doiTuongVPHC").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("doiTuongVPHC"),
                            criteriaBuilder.literal("$.GiayToChungNhan.SoGiay")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = root.get("doiTuongVPHC").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("doiTuongVPHC"),
                            criteriaBuilder.literal("$.LoaiDoiTuongCNTC.TenMuc")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = root.get("coQuanQuanLy").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("coQuanQuanLy"),
                            criteriaBuilder.literal("$.TenGoi")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = root.get("coQuanQuyetDinh").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("coQuanQuyetDinh"),
                            criteriaBuilder.literal("$.TenGoi")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = root.get("donViChuTri").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("donViChuTri"),
                            criteriaBuilder.literal("$.TenGoi")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = root.get("canBoTheoDoi").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("canBoTheoDoi"),
                            criteriaBuilder.literal("$.HoVaTen")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = root.get("noiDungVPHC").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("noiDungVPHC"),
                            criteriaBuilder.literal("$.HanhViVPHC.TenMuc")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = root.get("hinhThucXuLyChinh").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("hinhThucXuLyChinh"),
                            criteriaBuilder.literal("$.TenMuc")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = root.get("hinhThucXuLyChinh").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("hinhThucXuLyChinh"),
                            criteriaBuilder.literal("$.TenMuc")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = criteriaBuilder.like(root.get("noiDungLyDoXuLy"), "%" + keyword + "%");
            orPredicate.add(condition);

            condition = root.get("truongHopPhatTien").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("truongHopPhatTien"),
                            criteriaBuilder.literal("$.TongSoTienPhat")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = root.get("truongHopPhatTien").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("truongHopPhatTien"),
                            criteriaBuilder.literal("$.NoiThuTienPhat")), "%" + keyword + "%"));
            orPredicate.add(condition);

            condition = criteriaBuilder.like(root.get("soBienBanVPHC"), "%" + keyword + "%");
            orPredicate.add(condition);

            condition = root.get("trangThaiXuLyVPHC").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("trangThaiXuLyVPHC"),
                            criteriaBuilder.literal("$.TenMuc")), "%" + keyword + "%"));
            orPredicate.add(condition);

            conditionList.add(criteriaBuilder.or(orPredicate.toArray(new Predicate[0])));
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getCoQuanQuanLy())) {
            Predicate condition = root.get("coQuanQuanLy").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("coQuanQuanLy"), criteriaBuilder.literal("$.MaDinhDanh")), xuLyVPHCQueryModel.getCoQuanQuanLy()));
            conditionList.add(condition);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayLapBienBan())) {

            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayLapBienBan(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca_to.set(Calendar.MILLISECOND, 999);
            Predicate predicate = criteriaBuilder.between(root.get("ngayLapBienBan"), ca_from.getTime(), ca_to.getTime());
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayLapBienBan_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayLapBienBan_TuNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("ngayLapBienBan"), ca.getTime());
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayLapBienBan_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayLapBienBan_DenNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("ngayLapBienBan"), ca.getTime());
            conditionList.add(predicate);
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayQuyetDinh())) {

            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayQuyetDinh(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca_to.set(Calendar.MILLISECOND, 999);
            Predicate predicate = criteriaBuilder.between(root.get("ngayQuyetDinh"), ca_from.getTime(), ca_to.getTime());
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayQuyetDinh_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayQuyetDinh_TuNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("ngayQuyetDinh"), ca.getTime());
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayQuyetDinh_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayQuyetDinh_DenNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("ngayQuyetDinh"), ca.getTime());
            conditionList.add(predicate);
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getSoBienBanVPHC())) {
            Predicate predicate = criteriaBuilder.equal(root.get("soBienBanVPHC"), xuLyVPHCQueryModel.getSoBienBanVPHC());
            conditionList.add(predicate);
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getCoQuanQuyetDinh())) {
            Predicate predicate = root.get("coQuanQuyetDinh").isNotNull();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("coQuanQuyetDinh"),
                            criteriaBuilder.literal("$.MaDinhDanh")), xuLyVPHCQueryModel.getCoQuanQuyetDinh()));
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayKetThuc())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayKetThuc(),
                    DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca_to.set(Calendar.MILLISECOND, 999);

            Predicate predicate = criteriaBuilder.between(root.get("ngayKetThuc"), ca_from.getTime(), ca_to.getTime());
            conditionList.add(predicate);
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayKetThuc_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayKetThuc_TuNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("ngayKetThuc"), ca.getTime());
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayKetThuc_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayKetThuc_DenNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("ngayKetThuc"), ca.getTime());
            conditionList.add(predicate);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getTrangThaiXuLyVPHC_MaMuc())
                && xuLyVPHCQueryModel.getTrangThaiXuLyVPHC_MaMuc().length > 0) {
            Predicate condition = root.get("trangThaiXuLyVPHC").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("trangThaiXuLyVPHC"),
                    criteriaBuilder.literal("$.MaMuc")).in(Arrays.asList(xuLyVPHCQueryModel.getTrangThaiXuLyVPHC_MaMuc())));
            conditionList.add(condition);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getTrangThaiDuLieu_MaMuc())
                && xuLyVPHCQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Predicate condition = root.get("trangThaiDuLieu").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("trangThaiDuLieu"),
                    criteriaBuilder.literal("$.MaMuc")).in(Arrays.asList(xuLyVPHCQueryModel.getTrangThaiDuLieu_MaMuc())));
            conditionList.add(condition);
        }
        //Phân Quyền
        if (!InspectionUtil.checkSuperAdmin()) {
            User user = UserContextHolder.getContext().getUser();
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_VPHC, new com.fds.flex.context.model.Service.TaiNguyenHeThong());

            List<Predicate> listPhanQuyen = new ArrayList<>();
            if (taiNguyenHeThong.isHanChePhanVung()) {
                Predicate condition = root.get("phanVungDuLieu").isNotNull();
                condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("phanVungDuLieu"),
                        criteriaBuilder.literal("$.MaMuc")).in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
                listPhanQuyen.add(condition);
            }

            if (taiNguyenHeThong.isHanCheBanGhi()) {
                Predicate condition = root.get("nguoiTaoLap").isNotNull();
                condition = criteriaBuilder.and(condition, criteriaBuilder.equal(
                        criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("nguoiTaoLap"),
                                criteriaBuilder.literal("$.MaDinhDanh")), user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(condition);
            }
            if (!listPhanQuyen.isEmpty()) {
                conditionList.add(criteriaBuilder.or(listPhanQuyen.toArray(new Predicate[0])));
            }
        }
        if (!conditionList.isEmpty()) {
            xuLyVPHCCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSXuLyVPHC> query = entityManager.createQuery(xuLyVPHCCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        xuLyVPHCCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSXuLyVPHC.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSXuLyVPHC> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsXuLyVPHCRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSXuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc) {
        return rdbmsXuLyVPHCRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSXuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsXuLyVPHCRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Map<String, Long> thongKeTrangThaiXuLyVPHC(String[] trangThaiXuLyVPHC) {

        StringBuilder sqlBuilder = new StringBuilder();

        // Base SQL query
        sqlBuilder.append("SELECT JSON_UNQUOTE(JSON_EXTRACT(trangThaiXuLyVPHC, '$.MaMuc')), COUNT(*) ");
        sqlBuilder.append("FROM" + StringPool.SPACE + DBConstant.T_XU_LY_VPHC + StringPool.SPACE);

        // Where clause - Initial condition on 'TrangThaiDuLieu'
        List<String> params = new ArrayList<>();
        List<String> whereConditions = new ArrayList<>();
        whereConditions.add("JSON_UNQUOTE(JSON_EXTRACT(trangThaiDuLieu, '$.MaMuc')) = " + TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());

        if (Validator.isNotNull(trangThaiXuLyVPHC) && trangThaiXuLyVPHC.length > 0) {
            whereConditions.add("JSON_UNQUOTE(JSON_EXTRACT(trangThaiXuLyVPHC, '$.MaMuc')) IN :trangThaiXuLyVPHC");
        }

        // Phân quyền - Authorization logic
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_VPHC)) {

            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_VPHC, new com.fds.flex.context.model.Service.TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();

            List<String> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanCheBanGhi()) {
                listPhanQuyen.add("JSON_UNQUOTE(JSON_EXTRACT(nguoiTaoLap, '$.MaDinhDanh')) = " + user.getDanhTinhDienTu().getMaDinhDanh());
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
        sqlBuilder.append(" GROUP BY JSON_UNQUOTE(JSON_EXTRACT(trangThaiXuLyVPHC, '$.MaMuc'))");

        // Create and set parameters for the native query
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("trangThaiDuLieu", TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        query.setParameter("trangThaiXuLyVPHC", Arrays.asList(trangThaiXuLyVPHC));
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
