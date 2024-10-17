package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiThongBaoKetLuan;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.LoaiThongBaoKetLuanRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.LoaiThongBaoKetLuanService;
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
public class LoaiThongBaoKetLuanServiceImpl implements LoaiThongBaoKetLuanService {
    @Autowired
    private LoaiThongBaoKetLuanRepository loaiThongBaoKetLuanRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<LoaiThongBaoKetLuan> findById(String id) {
        log.info(LogConstant.findById, LoaiThongBaoKetLuan.class.getSimpleName(), id);
        return loaiThongBaoKetLuanRepository.findById(id);
    }

    @Override
    public void deleteLoaiThongBaoKetLuan(LoaiThongBaoKetLuan loaiThongBaoKetLuan) {
        log.info(LogConstant.deleteById, LoaiThongBaoKetLuan.class.getSimpleName(), loaiThongBaoKetLuan.getPrimKey());
        loaiThongBaoKetLuanRepository.delete(loaiThongBaoKetLuan);
    }

    @Override
    public LoaiThongBaoKetLuan updateLoaiThongBaoKetLuan(LoaiThongBaoKetLuan loaiThongBaoKetLuan) {
        log.info(LogConstant.updateById, LoaiThongBaoKetLuan.class.getSimpleName(), loaiThongBaoKetLuan.getPrimKey());
        return loaiThongBaoKetLuanRepository.save(loaiThongBaoKetLuan);
    }

    @Override
    public Map<String, LoaiThongBaoKetLuan> update(Map<String, LoaiThongBaoKetLuan> map) {
        log.info(LogConstant.updateByMap, LoaiThongBaoKetLuan.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                loaiThongBaoKetLuanRepository.delete(v);
            } else {
                loaiThongBaoKetLuanRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<LoaiThongBaoKetLuan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, LoaiThongBaoKetLuan.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, LoaiThongBaoKetLuan.class.getSimpleName(),
                new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, LoaiThongBaoKetLuan.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, LoaiThongBaoKetLuan.class));
    }

    @Override
    public List<LoaiThongBaoKetLuan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiThongBaoKetLuan.class.getSimpleName(), maMuc,
                trangThaiDuLieu_MaMuc);
        return loaiThongBaoKetLuanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<LoaiThongBaoKetLuan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiThongBaoKetLuan.class.getSimpleName(), maMuc,
                trangThaiDuLieu_MaMuc);
        return loaiThongBaoKetLuanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}