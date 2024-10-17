package com.fds.flex.core.cadmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSDoiTuongDuocTiep;
import com.fds.flex.core.cadmgt.rdbms.repository.C_Repository.RDBMSDoiTuongDuocTiepRepository;
import com.fds.flex.core.cadmgt.rdbms.service.C_Service.RDBMSDoiTuongDuocTiepService;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RDBMSDoiTuongDuocTiepServiceImpl implements RDBMSDoiTuongDuocTiepService {

//    @Autowired
    private RDBMSDoiTuongDuocTiepRepository rdbmsDoiTuongDuocTiepRepository;

    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSDoiTuongDuocTiep> findById(String id) {
        return rdbmsDoiTuongDuocTiepRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteDoiTuongDuocTiep(RDBMSDoiTuongDuocTiep rdbmsDoiTuongDuocTiep) {
        rdbmsDoiTuongDuocTiepRepository.delete(rdbmsDoiTuongDuocTiep);
    }

    @Override
    public RDBMSDoiTuongDuocTiep updateDoiTuongDuocTiep(RDBMSDoiTuongDuocTiep rdbmsDoiTuongDuocTiep) {
        return rdbmsDoiTuongDuocTiepRepository.saveAndFlush(rdbmsDoiTuongDuocTiep);
    }

    @Override
    public Map<String, RDBMSDoiTuongDuocTiep> update(Map<String, RDBMSDoiTuongDuocTiep> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsDoiTuongDuocTiepRepository.delete(v);
            } else {
                rdbmsDoiTuongDuocTiepRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSDoiTuongDuocTiep> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSDoiTuongDuocTiep> rdbmsDoiTuongDuocTiepCriteriaQuery = criteriaBuilder.createQuery(RDBMSDoiTuongDuocTiep.class);
        Root<RDBMSDoiTuongDuocTiep> root = rdbmsDoiTuongDuocTiepCriteriaQuery.from(RDBMSDoiTuongDuocTiep.class);
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
            rdbmsDoiTuongDuocTiepCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSDoiTuongDuocTiep> query = entityManager.createQuery(rdbmsDoiTuongDuocTiepCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsDoiTuongDuocTiepCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSDoiTuongDuocTiep.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSDoiTuongDuocTiep> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsDoiTuongDuocTiepRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSDoiTuongDuocTiep> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsDoiTuongDuocTiepRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}
