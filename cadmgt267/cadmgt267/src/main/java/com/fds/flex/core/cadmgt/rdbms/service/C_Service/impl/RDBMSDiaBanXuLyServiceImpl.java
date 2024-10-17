package com.fds.flex.core.cadmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSDiaBanXuLy;
import com.fds.flex.core.cadmgt.rdbms.repository.C_Repository.RDBMSDiaBanXuLyRepository;
import com.fds.flex.core.cadmgt.rdbms.service.C_Service.RDBMSDiaBanXuLyService;
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
public class RDBMSDiaBanXuLyServiceImpl implements RDBMSDiaBanXuLyService {

//    @Autowired
    private RDBMSDiaBanXuLyRepository rdbmsDiaBanXuLyRepository;

    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSDiaBanXuLy> findById(String id) {
        return rdbmsDiaBanXuLyRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteDiaBanXuLy(RDBMSDiaBanXuLy rdbmsDiaBanXuLy) {
        rdbmsDiaBanXuLyRepository.delete(rdbmsDiaBanXuLy);
    }

    @Override
    public RDBMSDiaBanXuLy updateDiaBanXuLy(RDBMSDiaBanXuLy rdbmsDiaBanXuLy) {
        return rdbmsDiaBanXuLyRepository.saveAndFlush(rdbmsDiaBanXuLy);
    }

    @Override
    public Map<String, RDBMSDiaBanXuLy> update(Map<String, RDBMSDiaBanXuLy> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsDiaBanXuLyRepository.delete(v);
            } else {
                rdbmsDiaBanXuLyRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSDiaBanXuLy> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSDiaBanXuLy> rdbmsDiaBanXuCriteriaQuery = criteriaBuilder.createQuery(RDBMSDiaBanXuLy.class);
        Root<RDBMSDiaBanXuLy> root = rdbmsDiaBanXuCriteriaQuery.from(RDBMSDiaBanXuLy.class);
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
            rdbmsDiaBanXuCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSDiaBanXuLy> query = entityManager.createQuery(rdbmsDiaBanXuCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsDiaBanXuCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSDiaBanXuLy.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSDiaBanXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsDiaBanXuLyRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSDiaBanXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsDiaBanXuLyRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}
