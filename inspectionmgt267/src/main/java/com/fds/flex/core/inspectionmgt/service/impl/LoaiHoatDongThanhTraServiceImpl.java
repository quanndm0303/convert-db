package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiHoatDongThanhTra;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.LoaiHoatDongThanhTraRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.LoaiHoatDongThanhTraService;
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
public class LoaiHoatDongThanhTraServiceImpl implements LoaiHoatDongThanhTraService {
    @Autowired
    private LoaiHoatDongThanhTraRepository loaiHoatDongThanhTraRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<LoaiHoatDongThanhTra> findById(String id) {
        log.info(LogConstant.findById, LoaiHoatDongThanhTra.class.getSimpleName(), id);
        return loaiHoatDongThanhTraRepository.findById(id);
    }

    @Override
    public void deleteLoaiHoatDongThanhTra(LoaiHoatDongThanhTra loaiHoatDongThanhTra) {
        log.info(LogConstant.deleteById, LoaiHoatDongThanhTra.class.getSimpleName(), loaiHoatDongThanhTra.getPrimKey());
        loaiHoatDongThanhTraRepository.delete(loaiHoatDongThanhTra);
    }

    @Override
    public LoaiHoatDongThanhTra updateLoaiHoatDongThanhTra(LoaiHoatDongThanhTra loaiHoatDongThanhTra) {
        log.info(LogConstant.updateById, LoaiHoatDongThanhTra.class.getSimpleName(), loaiHoatDongThanhTra.getPrimKey());
        return loaiHoatDongThanhTraRepository.save(loaiHoatDongThanhTra);
    }

    @Override
    public Map<String, LoaiHoatDongThanhTra> update(Map<String, LoaiHoatDongThanhTra> map) {
        log.info(LogConstant.updateByMap, LoaiHoatDongThanhTra.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                loaiHoatDongThanhTraRepository.delete(v);
            } else {
                loaiHoatDongThanhTraRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<LoaiHoatDongThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, LoaiHoatDongThanhTra.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, LoaiHoatDongThanhTra.class.getSimpleName(),
                new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, LoaiHoatDongThanhTra.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, LoaiHoatDongThanhTra.class));
    }

    @Override
    public List<LoaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiHoatDongThanhTra.class.getSimpleName(), maMuc,
                trangThaiDuLieu_MaMuc);
        return loaiHoatDongThanhTraRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<LoaiHoatDongThanhTra> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        return loaiHoatDongThanhTraRepository.findByTrangThaiDuLieu(trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<LoaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiHoatDongThanhTra.class.getSimpleName(), maMuc,
                trangThaiDuLieu_MaMuc);
        return loaiHoatDongThanhTraRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}