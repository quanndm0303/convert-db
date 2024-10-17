package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.ChucDanhDoan;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.ChucDanhDoanRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.ChucDanhDoanService;
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
public class ChucDanhDoanServiceImpl implements ChucDanhDoanService {
    @Autowired
    private ChucDanhDoanRepository chucDanhDoanRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<ChucDanhDoan> findById(String id) {
        log.info(LogConstant.findById, ChucDanhDoan.class.getSimpleName(), id);
        return chucDanhDoanRepository.findById(id);
    }

    @Override
    public void deleteChucDanhDoan(ChucDanhDoan chucDanhDoan) {
        log.info(LogConstant.deleteById, ChucDanhDoan.class.getSimpleName(), chucDanhDoan.getPrimKey());
        chucDanhDoanRepository.delete(chucDanhDoan);
    }

    @Override
    public ChucDanhDoan updateChucDanhDoan(ChucDanhDoan chucDanhDoan) {
        log.info(LogConstant.updateById, ChucDanhDoan.class.getSimpleName(), chucDanhDoan.getPrimKey());
        return chucDanhDoanRepository.save(chucDanhDoan);
    }

    @Override
    public Map<String, ChucDanhDoan> update(Map<String, ChucDanhDoan> map) {
        log.info(LogConstant.updateByMap, ChucDanhDoan.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                chucDanhDoanRepository.delete(v);
            } else {
                chucDanhDoanRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<ChucDanhDoan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, ChucDanhDoan.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, ChucDanhDoan.class.getSimpleName(), new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, ChucDanhDoan.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, ChucDanhDoan.class));
    }

    @Override
    public List<ChucDanhDoan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, ChucDanhDoan.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return chucDanhDoanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<ChucDanhDoan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, ChucDanhDoan.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return chucDanhDoanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}