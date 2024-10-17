package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiCheDoThanhTra;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.LoaiCheDoThanhTraRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.LoaiCheDoThanhTraService;
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
public class LoaiCheDoThanhTraServiceImpl implements LoaiCheDoThanhTraService {
    @Autowired
    private LoaiCheDoThanhTraRepository loaiCheDoThanhTraRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<LoaiCheDoThanhTra> findById(String id) {
        log.info(LogConstant.findById, LoaiCheDoThanhTra.class.getSimpleName(), id);
        return loaiCheDoThanhTraRepository.findById(id);
    }

    @Override
    public void deleteLoaiCheDoThanhTra(LoaiCheDoThanhTra loaiCheDoThanhTra) {
        log.info(LogConstant.deleteById, LoaiCheDoThanhTra.class.getSimpleName(), loaiCheDoThanhTra.getPrimKey());
        loaiCheDoThanhTraRepository.delete(loaiCheDoThanhTra);
    }

    @Override
    public LoaiCheDoThanhTra updateLoaiCheDoThanhTra(LoaiCheDoThanhTra loaiCheDoThanhTra) {
        log.info(LogConstant.updateById, LoaiCheDoThanhTra.class.getSimpleName(), loaiCheDoThanhTra.getPrimKey());
        return loaiCheDoThanhTraRepository.save(loaiCheDoThanhTra);
    }

    @Override
    public Map<String, LoaiCheDoThanhTra> update(Map<String, LoaiCheDoThanhTra> map) {
        log.info(LogConstant.updateByMap, LoaiCheDoThanhTra.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                loaiCheDoThanhTraRepository.delete(v);
            } else {
                loaiCheDoThanhTraRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<LoaiCheDoThanhTra> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, LoaiCheDoThanhTra.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, LoaiCheDoThanhTra.class.getSimpleName(),
                new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, LoaiCheDoThanhTra.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, LoaiCheDoThanhTra.class));
    }

    @Override
    public List<LoaiCheDoThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiCheDoThanhTra.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return loaiCheDoThanhTraRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<LoaiCheDoThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiCheDoThanhTra.class.getSimpleName(), maMuc, trangThaiDuLieu_MaMuc);
        return loaiCheDoThanhTraRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}