package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucXuLyChinh;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.HinhThucXuLyChinhRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.HinhThucXuLyChinhService;
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
public class HinhThucXuLyChinhServiceImpl implements HinhThucXuLyChinhService {
    @Autowired
    private HinhThucXuLyChinhRepository hinhThucXuLyChinhRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<HinhThucXuLyChinh> findById(String id) {
        log.info(LogConstant.findById, HinhThucXuLyChinh.class.getSimpleName(), id);
        return hinhThucXuLyChinhRepository.findById(id);
    }

    @Override
    public void deleteHinhThucXuLyChinh(HinhThucXuLyChinh hinhThucXuLyChinh) {
        log.info(LogConstant.deleteById, HinhThucXuLyChinh.class.getSimpleName(), hinhThucXuLyChinh.getPrimKey());
        hinhThucXuLyChinhRepository.delete(hinhThucXuLyChinh);
    }

    @Override
    public HinhThucXuLyChinh updateHinhThucXuLyChinh(HinhThucXuLyChinh hinhThucXuLyChinh) {
        log.info(LogConstant.updateById, HinhThucXuLyChinh.class.getSimpleName(), hinhThucXuLyChinh.getPrimKey());
        return hinhThucXuLyChinhRepository.save(hinhThucXuLyChinh);
    }

    @Override
    public Map<String, HinhThucXuLyChinh> update(Map<String, HinhThucXuLyChinh> map) {
        log.info(LogConstant.updateByMap, HinhThucXuLyChinh.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                hinhThucXuLyChinhRepository.delete(v);
            } else {
                hinhThucXuLyChinhRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<HinhThucXuLyChinh> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, HinhThucXuLyChinh.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, HinhThucXuLyChinh.class.getSimpleName(),
                new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, HinhThucXuLyChinh.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, HinhThucXuLyChinh.class));
    }

    @Override
    public List<HinhThucXuLyChinh> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, HinhThucXuLyChinh.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return hinhThucXuLyChinhRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<HinhThucXuLyChinh> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, HinhThucXuLyChinh.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return hinhThucXuLyChinhRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}