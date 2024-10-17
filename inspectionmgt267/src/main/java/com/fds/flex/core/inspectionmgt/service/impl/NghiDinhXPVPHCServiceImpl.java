package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.NghiDinhXPVPHC;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.NghiDinhXPVPHCRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.NghiDinhXPVPHCService;
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
public class NghiDinhXPVPHCServiceImpl implements NghiDinhXPVPHCService {
    @Autowired
    private NghiDinhXPVPHCRepository nghiDinhXPVPHCRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<NghiDinhXPVPHC> findById(String id) {
        log.info(LogConstant.findById, NghiDinhXPVPHC.class.getSimpleName(), id);
        return nghiDinhXPVPHCRepository.findById(id);
    }

    @Override
    public void deleteNghiDinhXPVPHC(NghiDinhXPVPHC nghiDinhXPVPHC) {
        log.info(LogConstant.deleteById, NghiDinhXPVPHC.class.getSimpleName(), nghiDinhXPVPHC.getPrimKey());
        nghiDinhXPVPHCRepository.delete(nghiDinhXPVPHC);
    }

    @Override
    public NghiDinhXPVPHC updateNghiDinhXPVPHC(NghiDinhXPVPHC nghiDinhXPVPHC) {
        log.info(LogConstant.updateById, NghiDinhXPVPHC.class.getSimpleName(), nghiDinhXPVPHC.getPrimKey());
        return nghiDinhXPVPHCRepository.save(nghiDinhXPVPHC);
    }

    @Override
    public Map<String, NghiDinhXPVPHC> update(Map<String, NghiDinhXPVPHC> map) {
        log.info(LogConstant.updateByMap, NghiDinhXPVPHC.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                nghiDinhXPVPHCRepository.delete(v);
            } else {
                nghiDinhXPVPHCRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<NghiDinhXPVPHC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, NghiDinhXPVPHC.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, NghiDinhXPVPHC.class.getSimpleName(), new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, NghiDinhXPVPHC.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, NghiDinhXPVPHC.class));
    }

    @Override
    public List<NghiDinhXPVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, NghiDinhXPVPHC.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return nghiDinhXPVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<NghiDinhXPVPHC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, NghiDinhXPVPHC.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return nghiDinhXPVPHCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}