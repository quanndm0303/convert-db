package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.HieuLucThiHanh;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.HieuLucThiHanhRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.HieuLucThiHanhService;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class HieuLucThiHanhServiceImpl implements HieuLucThiHanhService {
    @Autowired
    private HieuLucThiHanhRepository hieuLucThiHanhRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<HieuLucThiHanh> findById(String id) {
        log.info(LogConstant.findById, HieuLucThiHanh.class.getSimpleName(), id);
        return hieuLucThiHanhRepository.findById(id);
    }

    @Override
    public void deleteHieuLucThiHanh(HieuLucThiHanh hieuLucThiHanh) {
        log.info(LogConstant.deleteById, HieuLucThiHanh.class.getSimpleName(), hieuLucThiHanh.getPrimKey());
        hieuLucThiHanhRepository.delete(hieuLucThiHanh);
    }

    @Override
    public HieuLucThiHanh updateHieuLucThiHanh(HieuLucThiHanh hieuLucThiHanh) {
        log.info(LogConstant.updateById, HieuLucThiHanh.class.getSimpleName(), hieuLucThiHanh.getPrimKey());
        return hieuLucThiHanhRepository.save(hieuLucThiHanh);
    }

    @Override
    public Map<String, HieuLucThiHanh> update(Map<String, HieuLucThiHanh> map) {
        log.info(LogConstant.updateByMap, HieuLucThiHanh.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                hieuLucThiHanhRepository.delete(v);
            } else {
                hieuLucThiHanhRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<HieuLucThiHanh> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, HieuLucThiHanh.class.getSimpleName());
        Query query = new Query().with(pageable);

        if (!InspectionUtil.checkSuperAdmin() && !InspectionUtil.checkAdmin()) {
            query.fields().include("MaMuc", "TenMuc");
        }
        List<Criteria> criteria = new ArrayList<>();
        if (Validator.isNotNull(keyword)) {
            List<Criteria> subCriterias = new ArrayList<>();
            Criteria c = Criteria.where("MaMuc").regex(toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("TenMuc").regex(toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            criteria.add(new Criteria().orOperator(subCriterias));
        }
        if (Validator.isNotNull(trangThaiDuLieu_MaMuc) && trangThaiDuLieu_MaMuc.length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").in(Arrays.asList(trangThaiDuLieu_MaMuc));
            criteria.add(c);
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }
        log.debug(LogConstant.finishGenerateQueryFilter, HieuLucThiHanh.class.getSimpleName(), new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, HieuLucThiHanh.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, HieuLucThiHanh.class));
    }

    @Override
    public List<HieuLucThiHanh> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, HieuLucThiHanh.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return hieuLucThiHanhRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<HieuLucThiHanh> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, HieuLucThiHanh.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return hieuLucThiHanhRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}