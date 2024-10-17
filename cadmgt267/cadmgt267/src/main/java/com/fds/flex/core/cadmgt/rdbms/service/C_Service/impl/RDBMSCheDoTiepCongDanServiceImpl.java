package com.fds.flex.core.cadmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSCheDoTiepCongDan;
import com.fds.flex.core.cadmgt.rdbms.repository.C_Repository.RDBMSCheDoTiepCongDanRepository;
import com.fds.flex.core.cadmgt.rdbms.service.C_Service.RDBMSCheDoTiepCongDanService;
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
public class RDBMSCheDoTiepCongDanServiceImpl implements RDBMSCheDoTiepCongDanService {

//    @Autowired
    private RDBMSCheDoTiepCongDanRepository rdbmsCheDoTiepCongDanRepository;

    //@PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<RDBMSCheDoTiepCongDan> findById(String id) {
        return rdbmsCheDoTiepCongDanRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteCheDoTiepCongDan(RDBMSCheDoTiepCongDan rdbmsCheDoTiepCongDan) {
        rdbmsCheDoTiepCongDanRepository.delete(rdbmsCheDoTiepCongDan);
    }

    @Override
    public RDBMSCheDoTiepCongDan updateCheDoTiepCongDan(RDBMSCheDoTiepCongDan rdbmsCheDoTiepCongDan) {
        return rdbmsCheDoTiepCongDanRepository.saveAndFlush(rdbmsCheDoTiepCongDan);
    }

    @Override
    public Map<String, RDBMSCheDoTiepCongDan> update(Map<String, RDBMSCheDoTiepCongDan> map) {
        map.forEach((k, v) -> {
            if (k.equals((TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc()))) {
                rdbmsCheDoTiepCongDanRepository.delete(v);
            } else {
                rdbmsCheDoTiepCongDanRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSCheDoTiepCongDan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSCheDoTiepCongDan> rdbmsCheDoTiepCongDanCriteriaQuery = criteriaBuilder.createQuery(RDBMSCheDoTiepCongDan.class);
        Root<RDBMSCheDoTiepCongDan> root = rdbmsCheDoTiepCongDanCriteriaQuery.from(RDBMSCheDoTiepCongDan.class);
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
            rdbmsCheDoTiepCongDanCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSCheDoTiepCongDan> query = entityManager.createQuery(rdbmsCheDoTiepCongDanCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsCheDoTiepCongDanCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSCheDoTiepCongDan.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSCheDoTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsCheDoTiepCongDanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSCheDoTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsCheDoTiepCongDanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}
