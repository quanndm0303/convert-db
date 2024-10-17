package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiTheoDoi;
import com.fds.flex.core.inspectionmgt.repository.TrangThaiTheoDoiRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.TrangThaiTheoDoiService;
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
public class TrangThaiTheoDoiServiceImpl implements TrangThaiTheoDoiService {
    @Autowired
    private TrangThaiTheoDoiRepository trangThaiTheoDoiRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<TrangThaiTheoDoi> findById(String id) {
        log.info(LogConstant.findById, TrangThaiTheoDoi.class.getSimpleName(), id);
        return trangThaiTheoDoiRepository.findById(id);
    }

    @Override
    public void deleteTrangThaiTheoDoi(TrangThaiTheoDoi trangThaiTheoDoi) {
        log.info(LogConstant.deleteById, TrangThaiTheoDoi.class.getSimpleName(), trangThaiTheoDoi.getPrimKey());
        trangThaiTheoDoiRepository.delete(trangThaiTheoDoi);
    }

    @Override
    public TrangThaiTheoDoi updateTrangThaiTheoDoi(TrangThaiTheoDoi trangThaiTheoDoi) {
        log.info(LogConstant.updateById, TrangThaiTheoDoi.class.getSimpleName(), trangThaiTheoDoi.getPrimKey());
        return trangThaiTheoDoiRepository.save(trangThaiTheoDoi);
    }

    @Override
    public Map<String, TrangThaiTheoDoi> update(Map<String, TrangThaiTheoDoi> map) {
        log.info(LogConstant.updateByMap, TrangThaiTheoDoi.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                trangThaiTheoDoiRepository.delete(v);
            } else {
                trangThaiTheoDoiRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<TrangThaiTheoDoi> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, TrangThaiTheoDoi.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, TrangThaiTheoDoi.class.getSimpleName(), new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, TrangThaiTheoDoi.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, TrangThaiTheoDoi.class));
    }

    @Override
    public List<TrangThaiTheoDoi> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, TrangThaiTheoDoi.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return trangThaiTheoDoiRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<TrangThaiTheoDoi> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, TrangThaiTheoDoi.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return trangThaiTheoDoiRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<TrangThaiTheoDoi> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        return trangThaiTheoDoiRepository.findByTrangThaiDuLieu(trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}