package com.fds.flex.core.cadmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSLoaiVuViecDonThu;
import com.fds.flex.core.cadmgt.rdbms.repository.C_Repository.RDBMSLoaiVuViecDonThuRepository;
import com.fds.flex.core.cadmgt.rdbms.service.C_Service.RDBMSLoaiVuViecDonThuService;
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
public class RDBMSLoaiVuViecDonThuServiceImpl implements RDBMSLoaiVuViecDonThuService {
    //@Autowired
    private RDBMSLoaiVuViecDonThuRepository rdbmsLoaiVuViecDonThuRepository;

    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSLoaiVuViecDonThu> findById(String id) {
        return rdbmsLoaiVuViecDonThuRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteLoaiVuViecDonThu(RDBMSLoaiVuViecDonThu rdbmsLoaiVuViecDonThu) {
        rdbmsLoaiVuViecDonThuRepository.delete(rdbmsLoaiVuViecDonThu);
    }

    @Override
    public RDBMSLoaiVuViecDonThu updateLoaiVuViecDonThu(RDBMSLoaiVuViecDonThu rdbmsLoaiVuViecDonThu) {
        return rdbmsLoaiVuViecDonThuRepository.saveAndFlush(rdbmsLoaiVuViecDonThu);
    }

    @Override
    public Map<String, RDBMSLoaiVuViecDonThu> update(Map<String, RDBMSLoaiVuViecDonThu> map) {
        map.forEach((k, v) -> {
            if(k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsLoaiVuViecDonThuRepository.delete(v);
            } else {
                rdbmsLoaiVuViecDonThuRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSLoaiVuViecDonThu> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSLoaiVuViecDonThu> rdbmsLoaiVuViecDonThuCriteriaQuery = criteriaBuilder.createQuery(RDBMSLoaiVuViecDonThu.class);
        Root<RDBMSLoaiVuViecDonThu> root = rdbmsLoaiVuViecDonThuCriteriaQuery.from(RDBMSLoaiVuViecDonThu.class);
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
            rdbmsLoaiVuViecDonThuCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSLoaiVuViecDonThu> query = entityManager.createQuery(rdbmsLoaiVuViecDonThuCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsLoaiVuViecDonThuCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSLoaiVuViecDonThu.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSLoaiVuViecDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsLoaiVuViecDonThuRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSLoaiVuViecDonThu> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        return rdbmsLoaiVuViecDonThuRepository.findByTrangThaiDuLieu(trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<RDBMSLoaiVuViecDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsLoaiVuViecDonThuRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}
