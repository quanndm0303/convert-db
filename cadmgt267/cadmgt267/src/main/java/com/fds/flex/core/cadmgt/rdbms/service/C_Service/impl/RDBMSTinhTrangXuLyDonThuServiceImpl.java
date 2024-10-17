package com.fds.flex.core.cadmgt.rdbms.service.C_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSTinhTrangXuLyDonThu;
import com.fds.flex.core.cadmgt.rdbms.repository.C_Repository.RDBMSTinhTrangXuLyDonThuRepository;
import com.fds.flex.core.cadmgt.rdbms.service.C_Service.RDBMSTinhTrangXuLyDonThuService;
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
public class RDBMSTinhTrangXuLyDonThuServiceImpl implements RDBMSTinhTrangXuLyDonThuService {
    //@Autowired
    private RDBMSTinhTrangXuLyDonThuRepository rdbmsTinhTrangXuLyDonThuRepository;

    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RDBMSTinhTrangXuLyDonThu> findById(String id) {
        return rdbmsTinhTrangXuLyDonThuRepository.findById(Long.valueOf(id));
    }

    @Override
    public void deleteTinhTrangXuLyDonThu(RDBMSTinhTrangXuLyDonThu rdbmsTinhTrangXuLyDonThu) {
        rdbmsTinhTrangXuLyDonThuRepository.delete(rdbmsTinhTrangXuLyDonThu);
    }

    @Override
    public RDBMSTinhTrangXuLyDonThu updateTinhTrangXuLyDonThu(RDBMSTinhTrangXuLyDonThu rdbmsTinhTrangXuLyDonThu) {
        return rdbmsTinhTrangXuLyDonThuRepository.saveAndFlush(rdbmsTinhTrangXuLyDonThu);
    }

    @Override
    public Map<String, RDBMSTinhTrangXuLyDonThu> update(Map<String, RDBMSTinhTrangXuLyDonThu> map) {
        map.forEach((k, v) -> {
            if(k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())){
                rdbmsTinhTrangXuLyDonThuRepository.delete(v);
            } else {
                rdbmsTinhTrangXuLyDonThuRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSTinhTrangXuLyDonThu> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSTinhTrangXuLyDonThu> rdbmsTinhTrangXuLyDonThuCriteriaQuery = criteriaBuilder.createQuery(RDBMSTinhTrangXuLyDonThu.class);
        Root<RDBMSTinhTrangXuLyDonThu> root = rdbmsTinhTrangXuLyDonThuCriteriaQuery.from(RDBMSTinhTrangXuLyDonThu.class);
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
            rdbmsTinhTrangXuLyDonThuCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }
        TypedQuery<RDBMSTinhTrangXuLyDonThu> query = entityManager.createQuery(rdbmsTinhTrangXuLyDonThuCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String key = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(key)) : criteriaBuilder.desc(root.get(key));
            orders.add(jpaOrder);
        }
        rdbmsTinhTrangXuLyDonThuCriteriaQuery.orderBy(orders);
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        cqCount.select(criteriaBuilder.count(cqCount.from(RDBMSTinhTrangXuLyDonThu.class)));
        cqCount.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(cqCount).getSingleResult();
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total);
    }

    @Override
    public List<RDBMSTinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return rdbmsTinhTrangXuLyDonThuRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<RDBMSTinhTrangXuLyDonThu> findBySuDungChoXuLyDonThu_TTDL() {
        return rdbmsTinhTrangXuLyDonThuRepository.findBySuDungChoXuLyDonThu_TTDL();
    }

    @Override
    public List<RDBMSTinhTrangXuLyDonThu> findBySuDungChoVuViecDonThu_TTDL() {
        return rdbmsTinhTrangXuLyDonThuRepository.findBySuDungChoVuViecDonThu_TTDL();
    }

    @Override
    public Optional<RDBMSTinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return rdbmsTinhTrangXuLyDonThuRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }
}
