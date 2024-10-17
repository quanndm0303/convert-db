package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiDoiTuongCNTC;
import com.fds.flex.core.inspectionmgt.rdbms.repository.C_Repository.RDBMSLoaiDoiTuongCNTCRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.RDBMSLoaiDoiTuongCNTCService;
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
public class RDBMSLoaiDoiTuongCNTCServiceImpl implements RDBMSLoaiDoiTuongCNTCService {
    //@Autowired
    private RDBMSLoaiDoiTuongCNTCRepository rdbmsLoaiDoiTuongCNTCRepository;
    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSLoaiDoiTuongCNTC> findById(String id) {
        return rdbmsLoaiDoiTuongCNTCRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteLoaiDoiTuongCNTC(RDBMSLoaiDoiTuongCNTC rdbmsLoaiDoiTuongCNTC) {
        rdbmsLoaiDoiTuongCNTCRepository.delete(rdbmsLoaiDoiTuongCNTC);
    }

    @Override
    public RDBMSLoaiDoiTuongCNTC updateLoaiDoiTuongCNTC(RDBMSLoaiDoiTuongCNTC rdbmsLoaiDoiTuongCNTC) {
        return rdbmsLoaiDoiTuongCNTCRepository.saveAndFlush(rdbmsLoaiDoiTuongCNTC);
    }

    @Override
    public Map<String, RDBMSLoaiDoiTuongCNTC> update(Map<String, RDBMSLoaiDoiTuongCNTC> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsLoaiDoiTuongCNTCRepository.delete(v);
            } else {
                rdbmsLoaiDoiTuongCNTCRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSLoaiDoiTuongCNTC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSLoaiDoiTuongCNTC> rdbmsLoaiDoiTuongCNTCCriteriaQuery = criteriaBuilder.createQuery(RDBMSLoaiDoiTuongCNTC.class);
        Root<RDBMSLoaiDoiTuongCNTC> root = rdbmsLoaiDoiTuongCNTCCriteriaQuery.from(RDBMSLoaiDoiTuongCNTC.class);
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
            rdbmsLoaiDoiTuongCNTCCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSLoaiDoiTuongCNTC> query = entityManager.createQuery(rdbmsLoaiDoiTuongCNTCCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsLoaiDoiTuongCNTCCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSLoaiDoiTuongCNTC.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSLoaiDoiTuongCNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsLoaiDoiTuongCNTCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSLoaiDoiTuongCNTC> findByThamChieuMaMucAndTrangThaiDuLieu(String thamChieu_MaMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsLoaiDoiTuongCNTCRepository.findByThamChieuMaMucAndTrangThaiDuLieu(thamChieu_MaMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSLoaiDoiTuongCNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsLoaiDoiTuongCNTCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}