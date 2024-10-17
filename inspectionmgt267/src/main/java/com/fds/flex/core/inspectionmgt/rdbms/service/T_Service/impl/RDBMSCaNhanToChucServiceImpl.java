package com.fds.flex.core.inspectionmgt.rdbms.service.T_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.entity.Query_Model.CaNhanToChucQueryModel;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSCaNhanToChuc;
import com.fds.flex.core.inspectionmgt.rdbms.repository.T_Repository.RDBMSCaNhanToChucRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.T_Service.RDBMSCaNhanToChucService;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RDBMSCaNhanToChucServiceImpl implements RDBMSCaNhanToChucService {

    //@Autowired
    private RDBMSCaNhanToChucRepository rdbmsCaNhanToChucRepository;
    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    @Cacheable(value = "CaNhanToChuc", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<RDBMSCaNhanToChuc> findById(String id) {
        return rdbmsCaNhanToChucRepository.findById(Long.valueOf(id));
    }

    @Override
    @CacheEvict(value = "CaNhanToChuc", allEntries = true)
    public void deleteCaNhanToChuc(RDBMSCaNhanToChuc rdbmsCaNhanToChuc) {
        rdbmsCaNhanToChucRepository.deleteById(rdbmsCaNhanToChuc.getId());
    }

    @Override
    @CacheEvict(value = "CaNhanToChuc", allEntries = true)
    public RDBMSCaNhanToChuc updateCaNhanToChuc(RDBMSCaNhanToChuc rdbmsCaNhanToChuc) {
        return rdbmsCaNhanToChucRepository.saveAndFlush(rdbmsCaNhanToChuc);
    }

    @Override
    @CacheEvict(value = "CaNhanToChuc", allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, RDBMSCaNhanToChuc> update(Map<String, RDBMSCaNhanToChuc> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsCaNhanToChucRepository.delete(v);
            } else {
                rdbmsCaNhanToChucRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<RDBMSCaNhanToChuc> filter(CaNhanToChucQueryModel caNhanToChucFilterObject, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSCaNhanToChuc> caNhanToChucCriteriaQuery = criteriaBuilder.createQuery(RDBMSCaNhanToChuc.class);
        Root<RDBMSCaNhanToChuc> root = caNhanToChucCriteriaQuery.from(RDBMSCaNhanToChuc.class);
        List<Predicate> conditionList = new ArrayList<>();

        if (Validator.isNotNull(caNhanToChucFilterObject.getKeyword())) {
            String keywordPattern = "%" + caNhanToChucFilterObject.getKeyword() + "%";
            List<Predicate> orPredicates = new ArrayList<>();

            orPredicates.add(criteriaBuilder.like(root.get("tenGoi"), keywordPattern));
            orPredicates.add(criteriaBuilder.like(root.get("maDinhDanh"), keywordPattern));
            orPredicates.add(criteriaBuilder.like(criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("giayToChungNhan"),
                    criteriaBuilder.literal("$.SoGiay")), keywordPattern));

            conditionList.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        if (Validator.isNotNull(caNhanToChucFilterObject.getLoaiDoiTuongCNTC())) {
            Predicate predicate = criteriaBuilder.and(root.get("loaiDoiTuongCNTC").isNotNull(),
                    criteriaBuilder.equal(
                            criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("loaiDoiTuongCNTC"), criteriaBuilder.literal("$.MaMuc")),
                            caNhanToChucFilterObject.getLoaiDoiTuongCNTC()));
            conditionList.add(predicate);
        }

        if (Validator.isNotNull(caNhanToChucFilterObject.getMaDinhDanh())) {
            Predicate predicate = criteriaBuilder.equal(root.get("maDinhDanh"), caNhanToChucFilterObject.getMaDinhDanh());
            conditionList.add(predicate);
        }

        if (Validator.isNotNull(caNhanToChucFilterObject.getSoGiay())) {
            Predicate predicate = criteriaBuilder.and(root.get("giayToChungNhan").isNotNull(),
                    criteriaBuilder.equal(
                            criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("giayToChungNhan"), criteriaBuilder.literal("$.SoGiay")),
                            caNhanToChucFilterObject.getSoGiay()));
            conditionList.add(predicate);
        }

        if (Validator.isNotNull(caNhanToChucFilterObject.getTrangThaiDuLieu_MaMuc()) && caNhanToChucFilterObject.getTrangThaiDuLieu_MaMuc().length > 0) {
            Predicate trangThaiDuLieuPredicate = criteriaBuilder.and(
                    root.get("trangThaiDuLieu").isNotNull(),
                    criteriaBuilder.function("JSON_EXTRACT", String.class,
                            root.get("trangThaiDuLieu"),
                            criteriaBuilder.literal("$.MaMuc")
                    ).in((Object[]) caNhanToChucFilterObject.getTrangThaiDuLieu_MaMuc()));
            conditionList.add(trangThaiDuLieuPredicate);
        }

        //Phân quyền
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_DOI_TUONG_KE_KHAI_TAI_SAN)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_DOI_TUONG_KE_KHAI_TAI_SAN, new com.fds.flex.context.model.Service.TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Predicate> listPhanQuyen = new ArrayList<>();
            if (taiNguyenHeThong.isHanCheBanGhi()) {
                Predicate predicate = criteriaBuilder.and(root.get("nguoiTaoLap").isNotNull(),
                        criteriaBuilder.equal(
                                criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("nguoiTaoLap"), criteriaBuilder.literal("$.MaDinhDanh")),
                                user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(predicate);
            }
            if (taiNguyenHeThong.isHanChePhanVung()) {
                Predicate condition = root.get("phanVungDuLieu").isNotNull();
                condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("phanVungDuLieu"), criteriaBuilder.literal("$.MaMuc")).in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
                listPhanQuyen.add(condition);
            }
            if (!listPhanQuyen.isEmpty()) {
                conditionList.add(criteriaBuilder.or(listPhanQuyen.toArray(new Predicate[0])));
            }
        }

        if (!conditionList.isEmpty()) {
            caNhanToChucCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSCaNhanToChuc> query = entityManager.createQuery(caNhanToChucCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        caNhanToChucCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSCaNhanToChuc.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSCaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDulieu) {
        return rdbmsCaNhanToChucRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDulieu);
    }

    @Override
    public List<RDBMSCaNhanToChuc> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDulieu) {
        return rdbmsCaNhanToChucRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDulieu);
    }

    @Override
    public Optional<RDBMSCaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDulieu) {
        return rdbmsCaNhanToChucRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDulieu);
    }
}
