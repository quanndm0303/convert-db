package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.BienPhapKhacPhuc;
import com.fds.flex.core.inspectionmgt.repository.BienPhapKhacPhucRepository;
import com.fds.flex.core.inspectionmgt.service.BienPhapKhacPhucService;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
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
public class BienPhapKhacPhucServiceImpl implements BienPhapKhacPhucService {
    @Autowired
    private BienPhapKhacPhucRepository bienPhapKhacPhucRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<BienPhapKhacPhuc> findById(String id) {
        log.info(LogConstant.findById, BienPhapKhacPhuc.class.getSimpleName(), id);
        return bienPhapKhacPhucRepository.findById(id);
    }

    @Override
    public void deleteBienPhapKhacPhuc(BienPhapKhacPhuc bienPhapKhacPhuc) {
        log.info(LogConstant.deleteById, BienPhapKhacPhuc.class.getSimpleName(), bienPhapKhacPhuc.getPrimKey());
        bienPhapKhacPhucRepository.delete(bienPhapKhacPhuc);
    }

    @Override
    public BienPhapKhacPhuc updateBienPhapKhacPhuc(BienPhapKhacPhuc bienPhapKhacPhuc) {
        log.info(LogConstant.updateById, BienPhapKhacPhuc.class.getSimpleName(), bienPhapKhacPhuc.getPrimKey());
        return bienPhapKhacPhucRepository.save(bienPhapKhacPhuc);
    }

    @Override
    public Map<String, BienPhapKhacPhuc> update(Map<String, BienPhapKhacPhuc> map) {
        log.info(LogConstant.updateByMap, BienPhapKhacPhuc.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                bienPhapKhacPhucRepository.delete(v);
            } else {
                bienPhapKhacPhucRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<BienPhapKhacPhuc> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, BienPhapKhacPhuc.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, BienPhapKhacPhuc.class.getSimpleName(), new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, BienPhapKhacPhuc.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, BienPhapKhacPhuc.class));
    }

    @Override
    public List<BienPhapKhacPhuc> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, BienPhapKhacPhuc.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return bienPhapKhacPhucRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<BienPhapKhacPhuc> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, BienPhapKhacPhuc.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return bienPhapKhacPhucRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}