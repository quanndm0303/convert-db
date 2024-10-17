package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiHoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.repository.C_Repository.RDBMSLoaiHoatDongThanhTraRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.C_Service.RDBMSLoaiHoatDongThanhTraService;
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
public class RDBMSLoaiHoatDongThanhTraServiceImpl implements RDBMSLoaiHoatDongThanhTraService {
    //@Autowired
    private RDBMSLoaiHoatDongThanhTraRepository rdbmsRDBMSLoaiHoatDongThanhTraRepository;
    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSLoaiHoatDongThanhTra> findById(String id) {
        return rdbmsRDBMSLoaiHoatDongThanhTraRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteLoaiHoatDongThanhTra(RDBMSLoaiHoatDongThanhTra rdbmsLoaiHoatDongThanhTra) {
        rdbmsRDBMSLoaiHoatDongThanhTraRepository.delete(rdbmsLoaiHoatDongThanhTra);
    }

    @Override
    public RDBMSLoaiHoatDongThanhTra updateLoaiHoatDongThanhTra(RDBMSLoaiHoatDongThanhTra rdbmsLoaiHoatDongThanhTra) {
        return rdbmsRDBMSLoaiHoatDongThanhTraRepository.saveAndFlush(rdbmsLoaiHoatDongThanhTra);
    }

    @Override
    public Map<String, RDBMSLoaiHoatDongThanhTra> update(Map<String, RDBMSLoaiHoatDongThanhTra> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsRDBMSLoaiHoatDongThanhTraRepository.delete(v);
            } else {
                rdbmsRDBMSLoaiHoatDongThanhTraRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSLoaiHoatDongThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSLoaiHoatDongThanhTra> rdbmsLoaiHoatDongThanhTraCriteriaQuery = criteriaBuilder.createQuery(RDBMSLoaiHoatDongThanhTra.class);
        Root<RDBMSLoaiHoatDongThanhTra> root = rdbmsLoaiHoatDongThanhTraCriteriaQuery.from(RDBMSLoaiHoatDongThanhTra.class);
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
            rdbmsLoaiHoatDongThanhTraCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSLoaiHoatDongThanhTra> query = entityManager.createQuery(rdbmsLoaiHoatDongThanhTraCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsLoaiHoatDongThanhTraCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSLoaiHoatDongThanhTra.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSLoaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSLoaiHoatDongThanhTraRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSLoaiHoatDongThanhTra> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSLoaiHoatDongThanhTraRepository.findByTrangThaiDuLieu(trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSLoaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsRDBMSLoaiHoatDongThanhTraRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}