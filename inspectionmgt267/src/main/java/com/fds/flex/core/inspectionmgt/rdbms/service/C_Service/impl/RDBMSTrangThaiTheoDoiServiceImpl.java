package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiTheoDoi;
import com.fds.flex.core.inspectionmgt.rdbms.repository.C_Repository.RDBMSTrangThaiTheoDoiRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.RDBMSTrangThaiTheoDoiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RDBMSTrangThaiTheoDoiServiceImpl implements RDBMSTrangThaiTheoDoiService {
    //@Autowired
    private RDBMSTrangThaiTheoDoiRepository rdbmsRDBMSTrangThaiTheoDoiRepository;
    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSTrangThaiTheoDoi> findById(String id) {
        return rdbmsRDBMSTrangThaiTheoDoiRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteTrangThaiTheoDoi(RDBMSTrangThaiTheoDoi rdbmsTrangThaiTheoDoi) {
        rdbmsRDBMSTrangThaiTheoDoiRepository.delete(rdbmsTrangThaiTheoDoi);
    }

    @Override
    public RDBMSTrangThaiTheoDoi updateTrangThaiTheoDoi(RDBMSTrangThaiTheoDoi rdbmsTrangThaiTheoDoi) {
        return rdbmsRDBMSTrangThaiTheoDoiRepository.saveAndFlush(rdbmsTrangThaiTheoDoi);
    }

    @Override
    public Map<String, RDBMSTrangThaiTheoDoi> update(Map<String, RDBMSTrangThaiTheoDoi> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsRDBMSTrangThaiTheoDoiRepository.delete(v);
            } else {
                rdbmsRDBMSTrangThaiTheoDoiRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSTrangThaiTheoDoi> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSTrangThaiTheoDoi> rdbmsTrangThaiTheoDoiCriteriaQuery = criteriaBuilder.createQuery(RDBMSTrangThaiTheoDoi.class);
        Root<RDBMSTrangThaiTheoDoi> root = rdbmsTrangThaiTheoDoiCriteriaQuery.from(RDBMSTrangThaiTheoDoi.class);
        List<Predicate> conditionList = new ArrayList<>();
        if (Validator.isNotNull(trangThaiDuLieu_MaMuc) && trangThaiDuLieu_MaMuc.length > 0) {
            Predicate condition = root.get("trangThaiDuLieu").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("trangThaiDuLieu"), criteriaBuilder.literal("$.MaMuc")).in((Object[]) trangThaiDuLieu_MaMuc));
            conditionList.add(condition);
        }
        if (Validator.isNotNull(keyword)) {
            List<Predicate> orPredicate = new ArrayList<>();
            Predicate condition = criteriaBuilder.like(root.get("maMuc"), "%" + keyword + "%");
            orPredicate.add(condition);
            condition = criteriaBuilder.like(root.get("tenMuc"), "%" + keyword + "%");
            orPredicate.add(condition);
            conditionList.add(criteriaBuilder.or(orPredicate.toArray(new Predicate[0])));
        }
        if (!conditionList.isEmpty()) {
            rdbmsTrangThaiTheoDoiCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSTrangThaiTheoDoi> query = entityManager.createQuery(rdbmsTrangThaiTheoDoiCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsTrangThaiTheoDoiCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSTrangThaiTheoDoi.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSTrangThaiTheoDoi> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSTrangThaiTheoDoiRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSTrangThaiTheoDoi> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSTrangThaiTheoDoiRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSTrangThaiTheoDoi> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSTrangThaiTheoDoiRepository.findByTrangThaiDuLieu(trangThaiDuLieu_MaMuc);
    }
}