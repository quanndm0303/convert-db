package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiXuLyVPHC;
import com.fds.flex.core.inspectionmgt.repository.TrangThaiXuLyVPHCRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.TrangThaiXuLyVPHCService;
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
public class TrangThaiXuLyVPHCServiceImpl implements TrangThaiXuLyVPHCService {
    @Autowired
    private TrangThaiXuLyVPHCRepository trangThaiXuLyVPHCRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<TrangThaiXuLyVPHC> findById(String id) {
        log.info(LogConstant.findById, TrangThaiXuLyVPHC.class.getSimpleName(), id);
        return trangThaiXuLyVPHCRepository.findById(id);
    }

    @Override
    public List<TrangThaiXuLyVPHC> findByTrangThaiDuLieu(String[] trangThaiDulieu) {
        return trangThaiXuLyVPHCRepository.findByTrangThaiDuLieu(trangThaiDulieu);
    }

    @Override
    public void deleteTrangThaiXuLyVPHC(TrangThaiXuLyVPHC trangThaiXuLyVPHC) {
        log.info(LogConstant.deleteById, TrangThaiXuLyVPHC.class.getSimpleName(), trangThaiXuLyVPHC.getPrimKey());
        trangThaiXuLyVPHCRepository.delete(trangThaiXuLyVPHC);
    }

    @Override
    public TrangThaiXuLyVPHC updateTrangThaiXuLyVPHC(TrangThaiXuLyVPHC trangThaiXuLyVPHC) {
        log.info(LogConstant.updateById, TrangThaiXuLyVPHC.class.getSimpleName(), trangThaiXuLyVPHC.getPrimKey());
        return trangThaiXuLyVPHCRepository.save(trangThaiXuLyVPHC);
    }

    @Override
    public Map<String, TrangThaiXuLyVPHC> update(Map<String, TrangThaiXuLyVPHC> map) {
        log.info(LogConstant.updateByMap, TrangThaiXuLyVPHC.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                trangThaiXuLyVPHCRepository.delete(v);
            } else {
                trangThaiXuLyVPHCRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<TrangThaiXuLyVPHC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, TrangThaiXuLyVPHC.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, TrangThaiXuLyVPHC.class.getSimpleName(),
                new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, TrangThaiXuLyVPHC.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, TrangThaiXuLyVPHC.class));
    }

    @Override
    public List<TrangThaiXuLyVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, TrangThaiXuLyVPHC.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return trangThaiXuLyVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<TrangThaiXuLyVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, TrangThaiXuLyVPHC.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return trangThaiXuLyVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}