package com.fds.flex.core.cadmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSDoiTuongTiepCongDan;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSDoiTuongTiepCongDan;
import com.fds.flex.core.cadmgt.rdbms.repository.C_Repository.RDBMSDoiTuongTiepCongDanRepository;
import com.fds.flex.core.cadmgt.rdbms.service.C_Service.RDBMSDoiTuongTiepCongDanService;
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
public class RDBMSDoiTuongTiepCongDanServiceImpl implements RDBMSDoiTuongTiepCongDanService {

//    @Autowired
    private RDBMSDoiTuongTiepCongDanRepository rdbmsDoiTuongTiepCongDanRepository;

    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSDoiTuongTiepCongDan> findById(String id) {
        return rdbmsDoiTuongTiepCongDanRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteDoiTuongTiepCongDan(RDBMSDoiTuongTiepCongDan rdbmsDoiTuongTiepCongDan) {
        rdbmsDoiTuongTiepCongDanRepository.delete(rdbmsDoiTuongTiepCongDan);
    }

    @Override
    public RDBMSDoiTuongTiepCongDan updateDoiTuongTiepCongDan(RDBMSDoiTuongTiepCongDan rdbmsDoiTuongTiepCongDan) {
        return rdbmsDoiTuongTiepCongDanRepository.saveAndFlush(rdbmsDoiTuongTiepCongDan);
    }

    @Override
    public Map<String, RDBMSDoiTuongTiepCongDan> update(Map<String, RDBMSDoiTuongTiepCongDan> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsDoiTuongTiepCongDanRepository.delete(v);
            } else {
                rdbmsDoiTuongTiepCongDanRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSDoiTuongTiepCongDan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSDoiTuongTiepCongDan> rdbmsDoiTuongTiepCongDanCriteriaQuery = criteriaBuilder.createQuery(RDBMSDoiTuongTiepCongDan.class);
        Root<RDBMSDoiTuongTiepCongDan> root = rdbmsDoiTuongTiepCongDanCriteriaQuery.from(RDBMSDoiTuongTiepCongDan.class);
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
            rdbmsDoiTuongTiepCongDanCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSDoiTuongTiepCongDan> query = entityManager.createQuery(rdbmsDoiTuongTiepCongDanCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsDoiTuongTiepCongDanCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSDoiTuongTiepCongDan.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSDoiTuongTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsDoiTuongTiepCongDanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSDoiTuongTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsDoiTuongTiepCongDanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}
