package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiHoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.repository.TrangThaiHoatDongThanhTraRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.TrangThaiHoatDongThanhTraService;
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
public class TrangThaiHoatDongThanhTraServiceImpl implements TrangThaiHoatDongThanhTraService {
    @Autowired
    private TrangThaiHoatDongThanhTraRepository trangThaiHoatDongThanhTraRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<TrangThaiHoatDongThanhTra> findById(String id) {
        log.info(LogConstant.findById, TrangThaiHoatDongThanhTra.class.getSimpleName(), id);
        return trangThaiHoatDongThanhTraRepository.findById(id);
    }

    @Override
    public void deleteTrangThaiHoatDongThanhTra(TrangThaiHoatDongThanhTra trangThaiHoatDongThanhTra) {
        log.info(LogConstant.deleteById, TrangThaiHoatDongThanhTra.class.getSimpleName(),
                trangThaiHoatDongThanhTra.getPrimKey());
        trangThaiHoatDongThanhTraRepository.delete(trangThaiHoatDongThanhTra);
    }

    @Override
    public TrangThaiHoatDongThanhTra updateTrangThaiHoatDongThanhTra(
            TrangThaiHoatDongThanhTra trangThaiHoatDongThanhTra) {
        log.info(LogConstant.updateById, TrangThaiHoatDongThanhTra.class.getSimpleName(),
                trangThaiHoatDongThanhTra.getPrimKey());
        return trangThaiHoatDongThanhTraRepository.save(trangThaiHoatDongThanhTra);
    }

    @Override
    public Map<String, TrangThaiHoatDongThanhTra> update(Map<String, TrangThaiHoatDongThanhTra> map) {
        log.info(LogConstant.updateByMap, TrangThaiHoatDongThanhTra.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                trangThaiHoatDongThanhTraRepository.delete(v);
            } else {
                trangThaiHoatDongThanhTraRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<TrangThaiHoatDongThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, TrangThaiHoatDongThanhTra.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, TrangThaiHoatDongThanhTra.class.getSimpleName(),
                new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, TrangThaiHoatDongThanhTra.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, TrangThaiHoatDongThanhTra.class));
    }

    @Override
    public List<TrangThaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, TrangThaiHoatDongThanhTra.class.getSimpleName(), maMuc,
                trangThaiDuLieu_MaMuc);
        return trangThaiHoatDongThanhTraRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<TrangThaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc,
                                                                             String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, TrangThaiHoatDongThanhTra.class.getSimpleName(), maMuc,
                trangThaiDuLieu_MaMuc);
        return trangThaiHoatDongThanhTraRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<TrangThaiHoatDongThanhTra> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        return trangThaiHoatDongThanhTraRepository.findByTrangThaiDuLieu(trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}