package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiKhaoSat;
import com.fds.flex.core.inspectionmgt.repository.TrangThaiKhaoSatRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.TrangThaiKhaoSatService;
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
public class TrangThaiKhaoSatServiceImpl implements TrangThaiKhaoSatService {
    @Autowired
    private TrangThaiKhaoSatRepository trangThaiKhaoSatRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<TrangThaiKhaoSat> findById(String id) {
        log.info(LogConstant.findById, TrangThaiKhaoSat.class.getSimpleName(), id);
        return trangThaiKhaoSatRepository.findById(id);
    }

    @Override
    public void deleteTrangThaiKhaoSat(TrangThaiKhaoSat trangThaiKhaoSat) {
        log.info(LogConstant.deleteById, TrangThaiKhaoSat.class.getSimpleName(), trangThaiKhaoSat.getPrimKey());
        trangThaiKhaoSatRepository.delete(trangThaiKhaoSat);
    }

    @Override
    public TrangThaiKhaoSat updateTrangThaiKhaoSat(TrangThaiKhaoSat trangThaiKhaoSat) {
        log.info(LogConstant.updateById, TrangThaiKhaoSat.class.getSimpleName(), trangThaiKhaoSat.getPrimKey());
        return trangThaiKhaoSatRepository.save(trangThaiKhaoSat);
    }

    @Override
    public Map<String, TrangThaiKhaoSat> update(Map<String, TrangThaiKhaoSat> map) {
        log.info(LogConstant.updateByMap, TrangThaiKhaoSat.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                trangThaiKhaoSatRepository.delete(v);
            } else {
                trangThaiKhaoSatRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<TrangThaiKhaoSat> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, TrangThaiKhaoSat.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, TrangThaiKhaoSat.class.getSimpleName(), new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, TrangThaiKhaoSat.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, TrangThaiKhaoSat.class));
    }

    @Override
    public List<TrangThaiKhaoSat> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, TrangThaiKhaoSat.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return trangThaiKhaoSatRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<TrangThaiKhaoSat> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        return trangThaiKhaoSatRepository.findByTrangThaiDuLieu(trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<TrangThaiKhaoSat> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, TrangThaiKhaoSat.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return trangThaiKhaoSatRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}