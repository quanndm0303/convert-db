package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.NhomHanhViVPHC;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.NhomHanhViVPHCRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.NhomHanhViVPHCService;
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
public class NhomHanhViVPHCServiceImpl implements NhomHanhViVPHCService {
    @Autowired
    private NhomHanhViVPHCRepository nhomHanhViVPHCRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<NhomHanhViVPHC> findById(String id) {
        log.info(LogConstant.findById, NhomHanhViVPHC.class.getSimpleName(), id);
        return nhomHanhViVPHCRepository.findById(id);
    }

    @Override
    public void deleteNhomHanhViVPHC(NhomHanhViVPHC nhomHanhViVPHC) {
        log.info(LogConstant.deleteById, NhomHanhViVPHC.class.getSimpleName(), nhomHanhViVPHC.getPrimKey());
        nhomHanhViVPHCRepository.delete(nhomHanhViVPHC);
    }

    @Override
    public NhomHanhViVPHC updateNhomHanhViVPHC(NhomHanhViVPHC nhomHanhViVPHC) {
        log.info(LogConstant.updateById, NhomHanhViVPHC.class.getSimpleName(), nhomHanhViVPHC.getPrimKey());
        return nhomHanhViVPHCRepository.save(nhomHanhViVPHC);
    }

    @Override
    public Map<String, NhomHanhViVPHC> update(Map<String, NhomHanhViVPHC> map) {
        log.info(LogConstant.updateByMap, NhomHanhViVPHC.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                nhomHanhViVPHCRepository.delete(v);
            } else {
                nhomHanhViVPHCRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<NhomHanhViVPHC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, NhomHanhViVPHC.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, NhomHanhViVPHC.class.getSimpleName(), new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, NhomHanhViVPHC.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, NhomHanhViVPHC.class));
    }

    @Override
    public List<NhomHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, NhomHanhViVPHC.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return nhomHanhViVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<NhomHanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, NhomHanhViVPHC.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return nhomHanhViVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}