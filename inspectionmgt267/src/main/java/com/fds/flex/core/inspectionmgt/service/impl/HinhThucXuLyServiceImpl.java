package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucXuLy;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.HinhThucXuLyRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.HinhThucXuLyService;
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
public class HinhThucXuLyServiceImpl implements HinhThucXuLyService {
    @Autowired
    private HinhThucXuLyRepository hinhThucXuLyRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<HinhThucXuLy> findById(String id) {
        log.info(LogConstant.findById, HinhThucXuLy.class.getSimpleName(), id);
        return hinhThucXuLyRepository.findById(id);
    }

    @Override
    public void deleteHinhThucXuLy(HinhThucXuLy hinhThucXuLy) {
        log.info(LogConstant.deleteById, HinhThucXuLy.class.getSimpleName(), hinhThucXuLy.getPrimKey());
        hinhThucXuLyRepository.delete(hinhThucXuLy);
    }

    @Override
    public HinhThucXuLy updateHinhThucXuLy(HinhThucXuLy hinhThucXuLy) {
        log.info(LogConstant.updateById, HinhThucXuLy.class.getSimpleName(), hinhThucXuLy.getPrimKey());
        return hinhThucXuLyRepository.save(hinhThucXuLy);
    }

    @Override
    public Map<String, HinhThucXuLy> update(Map<String, HinhThucXuLy> map) {
        log.info(LogConstant.updateByMap, HinhThucXuLy.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                hinhThucXuLyRepository.delete(v);
            } else {
                hinhThucXuLyRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<HinhThucXuLy> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, HinhThucXuLy.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, HinhThucXuLy.class.getSimpleName(),
                new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, HinhThucXuLy.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, HinhThucXuLy.class));
    }

    @Override
    public List<HinhThucXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, HinhThucXuLy.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return hinhThucXuLyRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<HinhThucXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, HinhThucXuLy.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return hinhThucXuLyRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}