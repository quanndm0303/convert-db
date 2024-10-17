package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHinhThucPhatBoSung;
import com.fds.flex.core.inspectionmgt.rdbms.repository.C_Repository.RDBMSHinhThucPhatBoSungRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.RDBMSHinhThucPhatBoSungService;
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
public class RDBMSHinhThucPhatBoSungServiceImpl implements RDBMSHinhThucPhatBoSungService {
    //@Autowired
    private RDBMSHinhThucPhatBoSungRepository rdbmsRDBMSHinhThucPhatBoSungRepository;
    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSHinhThucPhatBoSung> findById(String id) {
        return rdbmsRDBMSHinhThucPhatBoSungRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteHinhThucPhatBoSung(RDBMSHinhThucPhatBoSung rdbmsHinhThucPhatBoSung) {
        rdbmsRDBMSHinhThucPhatBoSungRepository.delete(rdbmsHinhThucPhatBoSung);
    }

    @Override
    public RDBMSHinhThucPhatBoSung updateHinhThucPhatBoSung(RDBMSHinhThucPhatBoSung rdbmsHinhThucPhatBoSung) {
        return rdbmsRDBMSHinhThucPhatBoSungRepository.saveAndFlush(rdbmsHinhThucPhatBoSung);
    }

    @Override
    public Map<String, RDBMSHinhThucPhatBoSung> update(Map<String, RDBMSHinhThucPhatBoSung> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsRDBMSHinhThucPhatBoSungRepository.delete(v);
            } else {
                rdbmsRDBMSHinhThucPhatBoSungRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSHinhThucPhatBoSung> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSHinhThucPhatBoSung> rdbmsHinhThucPhatBoSungCriteriaQuery = criteriaBuilder.createQuery(RDBMSHinhThucPhatBoSung.class);
        Root<RDBMSHinhThucPhatBoSung> root = rdbmsHinhThucPhatBoSungCriteriaQuery.from(RDBMSHinhThucPhatBoSung.class);
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
            rdbmsHinhThucPhatBoSungCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSHinhThucPhatBoSung> query = entityManager.createQuery(rdbmsHinhThucPhatBoSungCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsHinhThucPhatBoSungCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSHinhThucPhatBoSung.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSHinhThucPhatBoSung> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSHinhThucPhatBoSungRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSHinhThucPhatBoSung> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSHinhThucPhatBoSungRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}