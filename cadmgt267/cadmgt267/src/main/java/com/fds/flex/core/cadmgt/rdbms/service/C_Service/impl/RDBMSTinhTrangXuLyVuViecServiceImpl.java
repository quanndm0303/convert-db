package com.fds.flex.core.cadmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTinhTrangXuLyVuViec;
import com.fds.flex.core.cadmgt.rdbms.repository.C_Repository.RDBMSTinhTrangXuLyVuViecRepository;
import com.fds.flex.core.cadmgt.rdbms.service.C_Service.RDBMSTinhTrangXuLyVuViecService;
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
public class RDBMSTinhTrangXuLyVuViecServiceImpl implements RDBMSTinhTrangXuLyVuViecService {
    //@Autowired
    private RDBMSTinhTrangXuLyVuViecRepository rdbmsTinhTrangXuLyVuViecRepository;

    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSTinhTrangXuLyVuViec> findById(String id) {
        return rdbmsTinhTrangXuLyVuViecRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteTinhTrangXuLyVuViec(RDBMSTinhTrangXuLyVuViec rdbmsTinhTrangXuLyVuViec) {
        rdbmsTinhTrangXuLyVuViecRepository.delete(rdbmsTinhTrangXuLyVuViec);
    }

    @Override
    public RDBMSTinhTrangXuLyVuViec updateTinhTrangXuLyVuViec(RDBMSTinhTrangXuLyVuViec rdbmsTinhTrangXuLyVuViec) {
        return rdbmsTinhTrangXuLyVuViecRepository.saveAndFlush(rdbmsTinhTrangXuLyVuViec);
    }

    @Override
    public Map<String, RDBMSTinhTrangXuLyVuViec> update(Map<String, RDBMSTinhTrangXuLyVuViec> map) {
        map.forEach((k, v) -> {
            if(k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsTinhTrangXuLyVuViecRepository.delete(v);
            } else {
                rdbmsTinhTrangXuLyVuViecRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSTinhTrangXuLyVuViec> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSTinhTrangXuLyVuViec> rdbmsTinhTrangXuLyVuViecCriteriaQuery = criteriaBuilder.createQuery(RDBMSTinhTrangXuLyVuViec.class);
        Root<RDBMSTinhTrangXuLyVuViec> root = rdbmsTinhTrangXuLyVuViecCriteriaQuery.from(RDBMSTinhTrangXuLyVuViec.class);
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
            rdbmsTinhTrangXuLyVuViecCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSTinhTrangXuLyVuViec> query = entityManager.createQuery(rdbmsTinhTrangXuLyVuViecCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsTinhTrangXuLyVuViecCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSTinhTrangXuLyVuViec.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSTinhTrangXuLyVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsTinhTrangXuLyVuViecRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSTinhTrangXuLyVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsTinhTrangXuLyVuViecRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSTinhTrangXuLyVuViec> thongKeTinhTrangXuLyVuViec() {
        return rdbmsTinhTrangXuLyVuViecRepository.findByTrangThaiDuLieu(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
    }
}
