package com.fds.flex.core.cadmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTrangThaiGiaiQuyetDon;
import com.fds.flex.core.cadmgt.rdbms.repository.C_Repository.RDBMSTrangThaiGiaiQuyetDonRepository;
import com.fds.flex.core.cadmgt.rdbms.service.C_Service.RDBMSTrangThaiGiaiQuyetDonService;
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
public class RDBMSTrangThaiGiaiQuyetDonServiceImpl implements RDBMSTrangThaiGiaiQuyetDonService {
    //@Autowired
    private RDBMSTrangThaiGiaiQuyetDonRepository rdbmsTrangThaiGiaiQuyetDonRepository;

    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSTrangThaiGiaiQuyetDon> findById(String id) {
        return rdbmsTrangThaiGiaiQuyetDonRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteTrangThaiGiaiQuyetDon(RDBMSTrangThaiGiaiQuyetDon rdbmsTrangThaiGiaiQuyetDon) {
        rdbmsTrangThaiGiaiQuyetDonRepository.delete(rdbmsTrangThaiGiaiQuyetDon);
    }

    @Override
    public RDBMSTrangThaiGiaiQuyetDon updateTrangThaiGiaiQuyetDon(RDBMSTrangThaiGiaiQuyetDon rdbmsTrangThaiGiaiQuyetDon) {
        return rdbmsTrangThaiGiaiQuyetDonRepository.saveAndFlush(rdbmsTrangThaiGiaiQuyetDon);
    }

    @Override
    public Map<String, RDBMSTrangThaiGiaiQuyetDon> update(Map<String, RDBMSTrangThaiGiaiQuyetDon> map) {
        map.forEach((k, v) -> {
            if(k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())){
                rdbmsTrangThaiGiaiQuyetDonRepository.delete(v);
            } else {
                rdbmsTrangThaiGiaiQuyetDonRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSTrangThaiGiaiQuyetDon> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSTrangThaiGiaiQuyetDon> rdbmsTrangThaiGiaiQuyetDonCriteriaQuery = criteriaBuilder.createQuery(RDBMSTrangThaiGiaiQuyetDon.class);
        Root<RDBMSTrangThaiGiaiQuyetDon> root = rdbmsTrangThaiGiaiQuyetDonCriteriaQuery.from(RDBMSTrangThaiGiaiQuyetDon.class);
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
            rdbmsTrangThaiGiaiQuyetDonCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSTrangThaiGiaiQuyetDon> query = entityManager.createQuery(rdbmsTrangThaiGiaiQuyetDonCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsTrangThaiGiaiQuyetDonCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSTrangThaiGiaiQuyetDon.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSTrangThaiGiaiQuyetDon> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsTrangThaiGiaiQuyetDonRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSTrangThaiGiaiQuyetDon> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        return rdbmsTrangThaiGiaiQuyetDonRepository.findByTrangThaiDuLieu(trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSTrangThaiGiaiQuyetDon> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsTrangThaiGiaiQuyetDonRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}
