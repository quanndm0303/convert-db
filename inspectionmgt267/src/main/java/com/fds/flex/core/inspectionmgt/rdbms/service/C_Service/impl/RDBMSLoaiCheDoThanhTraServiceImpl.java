package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiCheDoThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.repository.C_Repository.RDBMSLoaiCheDoThanhTraRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.RDBMSLoaiCheDoThanhTraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RDBMSLoaiCheDoThanhTraServiceImpl implements RDBMSLoaiCheDoThanhTraService {
    //@Autowired
    private RDBMSLoaiCheDoThanhTraRepository rdbmsRDBMSLoaiCheDoThanhTraRepository;
    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSLoaiCheDoThanhTra> findById(String id) {
        return rdbmsRDBMSLoaiCheDoThanhTraRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteLoaiCheDoThanhTra(RDBMSLoaiCheDoThanhTra rdbmsLoaiCheDoThanhTra) {
        rdbmsRDBMSLoaiCheDoThanhTraRepository.delete(rdbmsLoaiCheDoThanhTra);
    }

    @Override
    public RDBMSLoaiCheDoThanhTra updateLoaiCheDoThanhTra(RDBMSLoaiCheDoThanhTra rdbmsLoaiCheDoThanhTra) {
        return rdbmsRDBMSLoaiCheDoThanhTraRepository.saveAndFlush(rdbmsLoaiCheDoThanhTra);
    }

    @Override
    public Map<String, RDBMSLoaiCheDoThanhTra> update(Map<String, RDBMSLoaiCheDoThanhTra> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsRDBMSLoaiCheDoThanhTraRepository.delete(v);
            } else {
                rdbmsRDBMSLoaiCheDoThanhTraRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSLoaiCheDoThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSLoaiCheDoThanhTra> rdbmsLoaiCheDoThanhTraCriteriaQuery = criteriaBuilder.createQuery(RDBMSLoaiCheDoThanhTra.class);
        Root<RDBMSLoaiCheDoThanhTra> root = rdbmsLoaiCheDoThanhTraCriteriaQuery.from(RDBMSLoaiCheDoThanhTra.class);
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
            rdbmsLoaiCheDoThanhTraCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSLoaiCheDoThanhTra> query = entityManager.createQuery(rdbmsLoaiCheDoThanhTraCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsLoaiCheDoThanhTraCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSLoaiCheDoThanhTra.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSLoaiCheDoThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSLoaiCheDoThanhTraRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSLoaiCheDoThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSLoaiCheDoThanhTraRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}