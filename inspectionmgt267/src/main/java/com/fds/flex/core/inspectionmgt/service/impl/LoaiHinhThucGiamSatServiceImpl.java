package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiHinhThucGiamSat;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.LoaiHinhThucGiamSatRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.LoaiHinhThucGiamSatService;
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
public class LoaiHinhThucGiamSatServiceImpl implements LoaiHinhThucGiamSatService {
    @Autowired
    private LoaiHinhThucGiamSatRepository loaiHinhThucGiamSatRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<LoaiHinhThucGiamSat> findById(String id) {
        log.info(LogConstant.findById, LoaiHinhThucGiamSat.class.getSimpleName(), id);
        return loaiHinhThucGiamSatRepository.findById(id);
    }

    @Override
    public void deleteLoaiHinhThucGiamSat(LoaiHinhThucGiamSat loaiHinhThucGiamSat) {
        log.info(LogConstant.deleteById, LoaiHinhThucGiamSat.class.getSimpleName(), loaiHinhThucGiamSat.getPrimKey());
        loaiHinhThucGiamSatRepository.delete(loaiHinhThucGiamSat);
    }

    @Override
    public LoaiHinhThucGiamSat updateLoaiHinhThucGiamSat(LoaiHinhThucGiamSat loaiHinhThucGiamSat) {
        log.info(LogConstant.updateById, LoaiHinhThucGiamSat.class.getSimpleName(), loaiHinhThucGiamSat.getPrimKey());
        return loaiHinhThucGiamSatRepository.save(loaiHinhThucGiamSat);
    }

    @Override
    public Map<String, LoaiHinhThucGiamSat> update(Map<String, LoaiHinhThucGiamSat> map) {
        log.info(LogConstant.updateByMap, LoaiHinhThucGiamSat.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                loaiHinhThucGiamSatRepository.delete(v);
            } else {
                loaiHinhThucGiamSatRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<LoaiHinhThucGiamSat> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, LoaiHinhThucGiamSat.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, LoaiHinhThucGiamSat.class.getSimpleName(),
                new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, LoaiHinhThucGiamSat.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, LoaiHinhThucGiamSat.class));
    }

    @Override
    public List<LoaiHinhThucGiamSat> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiHinhThucGiamSat.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return loaiHinhThucGiamSatRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<LoaiHinhThucGiamSat> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiHinhThucGiamSat.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return loaiHinhThucGiamSatRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}