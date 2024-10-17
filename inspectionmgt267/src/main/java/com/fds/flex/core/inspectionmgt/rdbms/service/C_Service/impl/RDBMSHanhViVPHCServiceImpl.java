package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHanhViVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.repository.C_Repository.RDBMSHanhViVPHCRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.RDBMSHanhViVPHCService;
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
public class RDBMSHanhViVPHCServiceImpl implements RDBMSHanhViVPHCService {
    //@Autowired
    private RDBMSHanhViVPHCRepository rdbmsRDBMSHanhViVPHCRepository;
    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSHanhViVPHC> findById(String id) {
        return rdbmsRDBMSHanhViVPHCRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteHanhViVPHC(RDBMSHanhViVPHC rdbmsHanhViVPHC) {
        rdbmsRDBMSHanhViVPHCRepository.delete(rdbmsHanhViVPHC);
    }

    @Override
    public RDBMSHanhViVPHC updateHanhViVPHC(RDBMSHanhViVPHC rdbmsHanhViVPHC) {
        return rdbmsRDBMSHanhViVPHCRepository.saveAndFlush(rdbmsHanhViVPHC);
    }

    @Override
    public Map<String, RDBMSHanhViVPHC> update(Map<String, RDBMSHanhViVPHC> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsRDBMSHanhViVPHCRepository.delete(v);
            } else {
                rdbmsRDBMSHanhViVPHCRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSHanhViVPHC> filter(String keyword, String nhomHanhViVPHC, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSHanhViVPHC> rdbmsHanhViVPHCCriteriaQuery = criteriaBuilder.createQuery(RDBMSHanhViVPHC.class);
        Root<RDBMSHanhViVPHC> root = rdbmsHanhViVPHCCriteriaQuery.from(RDBMSHanhViVPHC.class);
        List<Predicate> conditionList = new ArrayList<>();
        if (Validator.isNotNull(trangThaiDuLieu_MaMuc) && trangThaiDuLieu_MaMuc.length > 0) {
            Predicate condition = root.get("trangThaiDuLieu").isNotNull();
            condition = criteriaBuilder.and(condition, criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("trangThaiDuLieu"), criteriaBuilder.literal("$.MaMuc")).in((Object[]) trangThaiDuLieu_MaMuc));
            conditionList.add(condition);
        }
        if (Validator.isNotNull(nhomHanhViVPHC)) {
            Predicate condition = root.get("nhomHanhViVPHC").isNotNull();
            condition = criteriaBuilder.and(condition,
                    criteriaBuilder.equal(
                            criteriaBuilder.function("JSON_EXTRACT", String.class,
                                    root.get("nhomHanhViVPHC"),
                                    criteriaBuilder.literal("$.MaMuc")
                            ),
                            nhomHanhViVPHC
                    )
            );
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
            rdbmsHanhViVPHCCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSHanhViVPHC> query = entityManager.createQuery(rdbmsHanhViVPHCCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsHanhViVPHCCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSHanhViVPHC.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSHanhViVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSHanhViVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}