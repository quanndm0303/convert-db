package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiDoiTuongCNTC;
import com.fds.flex.core.inspectionmgt.repository.LoaiDoiTuongCNTCRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.LoaiDoiTuongCNTCService;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class LoaiDoiTuongCNTCServiceImpl implements LoaiDoiTuongCNTCService {
    @Autowired
    private LoaiDoiTuongCNTCRepository loaiDoiTuongCNTCRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<LoaiDoiTuongCNTC> findById(String id) {
        log.info(LogConstant.findById, LoaiDoiTuongCNTC.class.getSimpleName(), id);
        return loaiDoiTuongCNTCRepository.findById(id);
    }

    @Override
    public void deleteLoaiDoiTuongCNTC(LoaiDoiTuongCNTC loaiDoiTuongCNTC) {
        log.info(LogConstant.deleteById, LoaiDoiTuongCNTC.class.getSimpleName(), loaiDoiTuongCNTC.getPrimKey());
        loaiDoiTuongCNTCRepository.delete(loaiDoiTuongCNTC);
    }

    @Override
    public LoaiDoiTuongCNTC updateLoaiDoiTuongCNTC(LoaiDoiTuongCNTC loaiDoiTuongCNTC) {
        log.info(LogConstant.updateById, LoaiDoiTuongCNTC.class.getSimpleName(), loaiDoiTuongCNTC.getPrimKey());
        return loaiDoiTuongCNTCRepository.save(loaiDoiTuongCNTC);
    }

    @Override
    public Map<String, LoaiDoiTuongCNTC> update(Map<String, LoaiDoiTuongCNTC> map) {
        log.info(LogConstant.updateByMap, LoaiDoiTuongCNTC.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                loaiDoiTuongCNTCRepository.delete(v);
            } else {
                loaiDoiTuongCNTCRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<LoaiDoiTuongCNTC> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, LoaiDoiTuongCNTC.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, LoaiDoiTuongCNTC.class.getSimpleName(), new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, LoaiDoiTuongCNTC.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, LoaiDoiTuongCNTC.class));
    }

    @Override
    public List<LoaiDoiTuongCNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiDoiTuongCNTC.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return loaiDoiTuongCNTCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<LoaiDoiTuongCNTC> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiDoiTuongCNTC.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return loaiDoiTuongCNTCRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    @Cacheable(value = "LoaiDoiTuongCNTC_ThamChieu", key = "{#thamChieu_MaMuc}")
    public List<LoaiDoiTuongCNTC> findByThamChieuMaMucAndTrangThaiDuLieu(String thamChieu_MaMuc, String[] trangThaiDuLieu_MaMuc) {
        return loaiDoiTuongCNTCRepository.findByThamChieuMaMucAndTrangThaiDuLieu(thamChieu_MaMuc, trangThaiDuLieu_MaMuc);
    }


    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}