package com.fds.flex.core.cadmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKienNghiXuLyKNTC;
import com.fds.flex.core.cadmgt.rdbms.repository.C_Repository.RDBMSKienNghiXuLyKNTCRepository;
import com.fds.flex.core.cadmgt.rdbms.service.C_Service.RDBMSKienNghiXuLyKNTCService;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Service
public class RDBMSKienNghiXuLyKNTCServiceImpl implements RDBMSKienNghiXuLyKNTCService {
    
    //@Autowired
    private RDBMSKienNghiXuLyKNTCRepository rdbmsKienNghiXuLyKNTCRepository;

    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSKienNghiXuLyKNTC> findById(String id) {
        return rdbmsKienNghiXuLyKNTCRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteKienNghiXuLyKNTC(RDBMSKienNghiXuLyKNTC rdbmsKienNghiXuLyKNTC) {
        rdbmsKienNghiXuLyKNTCRepository.delete(rdbmsKienNghiXuLyKNTC);
    }

    @Override
    public RDBMSKienNghiXuLyKNTC updateKienNghiXuLyKNTC(RDBMSKienNghiXuLyKNTC rdbmsKienNghiXuLyKNTC) {
        return rdbmsKienNghiXuLyKNTCRepository.saveAndFlush(rdbmsKienNghiXuLyKNTC);
    }

    @Override
    public Map<String, RDBMSKienNghiXuLyKNTC> update(Map<String, RDBMSKienNghiXuLyKNTC> map) {
        map.forEach((k, v) -> {
            if(k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsKienNghiXuLyKNTCRepository.delete(v);
            } else {
                rdbmsKienNghiXuLyKNTCRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSKienNghiXuLyKNTC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSKienNghiXuLyKNTC> rdbmsKienNghiXuLyKNTCCriteriaQuery = criteriaBuilder.createQuery(RDBMSKienNghiXuLyKNTC.class);
        Root<RDBMSKienNghiXuLyKNTC> root = rdbmsKienNghiXuLyKNTCCriteriaQuery.from(RDBMSKienNghiXuLyKNTC.class);
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
            rdbmsKienNghiXuLyKNTCCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSKienNghiXuLyKNTC> query = entityManager.createQuery(rdbmsKienNghiXuLyKNTCCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsKienNghiXuLyKNTCCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSKienNghiXuLyKNTC.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSKienNghiXuLyKNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsKienNghiXuLyKNTCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSKienNghiXuLyKNTC> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        return rdbmsKienNghiXuLyKNTCRepository.findByTrangThaiDuLieu(trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSKienNghiXuLyKNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsKienNghiXuLyKNTCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}
