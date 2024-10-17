package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.HanhViVPHC;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.HanhViVPHCRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.HanhViVPHCService;
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
public class HanhViVPHCServiceImpl implements HanhViVPHCService {
    @Autowired
    private HanhViVPHCRepository hanhViVPHCRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<HanhViVPHC> findById(String id) {
        log.info(LogConstant.findById, HanhViVPHC.class.getSimpleName(), id);
        return hanhViVPHCRepository.findById(id);
    }

    @Override
    public void deleteHanhViVPHC(HanhViVPHC hanhViVPHC) {
        log.info(LogConstant.deleteById, HanhViVPHC.class.getSimpleName(), hanhViVPHC.getPrimKey());
        hanhViVPHCRepository.delete(hanhViVPHC);
    }

    @Override
    public HanhViVPHC updateHanhViVPHC(HanhViVPHC hanhViVPHC) {
        log.info(LogConstant.updateById, HanhViVPHC.class.getSimpleName(), hanhViVPHC.getPrimKey());
        return hanhViVPHCRepository.save(hanhViVPHC);
    }

    @Override
    public Map<String, HanhViVPHC> update(Map<String, HanhViVPHC> map) {
        log.info(LogConstant.updateByMap, HanhViVPHC.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                hanhViVPHCRepository.delete(v);
            } else {
                hanhViVPHCRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<HanhViVPHC> filter(String keyword, String nhomHanhViVPHC_MaMuc, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, HanhViVPHC.class.getSimpleName());
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
        if (Validator.isNotNull(nhomHanhViVPHC_MaMuc)) {
            Criteria c = Criteria.where("NhomHanhViVPHC.MaMuc").is(nhomHanhViVPHC_MaMuc);
            criteria.add(c);
        }

        if (Validator.isNotNull(trangThaiDuLieu_MaMuc) && trangThaiDuLieu_MaMuc.length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").in(Arrays.asList(trangThaiDuLieu_MaMuc));
            criteria.add(c);
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }
        log.debug(LogConstant.finishGenerateQueryFilter, HanhViVPHC.class.getSimpleName(), new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, HanhViVPHC.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, HanhViVPHC.class));
    }

    @Override
    public List<HanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, HanhViVPHC.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return hanhViVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<HanhViVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, HanhViVPHC.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return hanhViVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}