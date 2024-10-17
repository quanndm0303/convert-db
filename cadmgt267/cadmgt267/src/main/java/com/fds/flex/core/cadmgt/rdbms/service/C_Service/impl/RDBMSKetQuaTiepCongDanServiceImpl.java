package com.fds.flex.core.cadmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSKetQuaTiepCongDan;
import com.fds.flex.core.cadmgt.rdbms.repository.C_Repository.RDBMSKetQuaTiepCongDanRepository;
import com.fds.flex.core.cadmgt.rdbms.service.C_Service.RDBMSKetQuaTiepCongDanService;
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
public class RDBMSKetQuaTiepCongDanServiceImpl implements RDBMSKetQuaTiepCongDanService {

    //@Autowired
    private RDBMSKetQuaTiepCongDanRepository rdbmsKetQuaTiepCongDanRepository;

    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSKetQuaTiepCongDan> findById(String id) {
        return rdbmsKetQuaTiepCongDanRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteKetQuaTiepCongDan(RDBMSKetQuaTiepCongDan rdbmsKetQuaTiepCongDan) {
        rdbmsKetQuaTiepCongDanRepository.delete(rdbmsKetQuaTiepCongDan);
    }

    @Override
    public RDBMSKetQuaTiepCongDan updateKetQuaTiepCongDan(RDBMSKetQuaTiepCongDan rdbmsKetQuaTiepCongDan) {
        return rdbmsKetQuaTiepCongDanRepository.saveAndFlush(rdbmsKetQuaTiepCongDan);
    }

    @Override
    public Map<String, RDBMSKetQuaTiepCongDan> update(Map<String, RDBMSKetQuaTiepCongDan> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsKetQuaTiepCongDanRepository.delete(v);
            } else {
                rdbmsKetQuaTiepCongDanRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSKetQuaTiepCongDan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSKetQuaTiepCongDan> rdbmsKetQuaTiepCongDanCriteriaQuery = criteriaBuilder.createQuery(RDBMSKetQuaTiepCongDan.class);
        Root<RDBMSKetQuaTiepCongDan> root = rdbmsKetQuaTiepCongDanCriteriaQuery.from(RDBMSKetQuaTiepCongDan.class);
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
            rdbmsKetQuaTiepCongDanCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSKetQuaTiepCongDan> query = entityManager.createQuery(rdbmsKetQuaTiepCongDanCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsKetQuaTiepCongDanCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSKetQuaTiepCongDan.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSKetQuaTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsKetQuaTiepCongDanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSKetQuaTiepCongDan> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        return rdbmsKetQuaTiepCongDanRepository.findByTrangThaiDuLieu(trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSKetQuaTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsKetQuaTiepCongDanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}
