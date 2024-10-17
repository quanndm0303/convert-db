package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSNhomHanhViVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.repository.C_Repository.RDBMSNhomHanhViVPHCRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.RDBMSNhomHanhViVPHCService;
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
public class RDBMSNhomHanhViVPHCServiceImpl implements RDBMSNhomHanhViVPHCService {
    //@Autowired
    private RDBMSNhomHanhViVPHCRepository rdbmsRDBMSNhomHanhViVPHCRepository;
    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSNhomHanhViVPHC> findById(String id) {
        return rdbmsRDBMSNhomHanhViVPHCRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteNhomHanhViVPHC(RDBMSNhomHanhViVPHC rdbmsNhomHanhViVPHC) {
        rdbmsRDBMSNhomHanhViVPHCRepository.delete(rdbmsNhomHanhViVPHC);
    }

    @Override
    public RDBMSNhomHanhViVPHC updateNhomHanhViVPHC(RDBMSNhomHanhViVPHC rdbmsNhomHanhViVPHC) {
        return rdbmsRDBMSNhomHanhViVPHCRepository.saveAndFlush(rdbmsNhomHanhViVPHC);
    }

    @Override
    public Map<String, RDBMSNhomHanhViVPHC> update(Map<String, RDBMSNhomHanhViVPHC> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsRDBMSNhomHanhViVPHCRepository.delete(v);
            } else {
                rdbmsRDBMSNhomHanhViVPHCRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSNhomHanhViVPHC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSNhomHanhViVPHC> rdbmsNhomHanhViVPHCCriteriaQuery = criteriaBuilder.createQuery(RDBMSNhomHanhViVPHC.class);
        Root<RDBMSNhomHanhViVPHC> root = rdbmsNhomHanhViVPHCCriteriaQuery.from(RDBMSNhomHanhViVPHC.class);
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
            rdbmsNhomHanhViVPHCCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSNhomHanhViVPHC> query = entityManager.createQuery(rdbmsNhomHanhViVPHCCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsNhomHanhViVPHCCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSNhomHanhViVPHC.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSNhomHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSNhomHanhViVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSNhomHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSNhomHanhViVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}