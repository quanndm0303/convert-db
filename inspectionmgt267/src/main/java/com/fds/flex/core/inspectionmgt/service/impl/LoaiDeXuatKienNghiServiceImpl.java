package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiDeXuatKienNghi;
import com.fds.flex.core.inspectionmgt.repository.LoaiDeXuatKienNghiRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.LoaiDeXuatKienNghiService;
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
public class LoaiDeXuatKienNghiServiceImpl implements LoaiDeXuatKienNghiService {
    @Autowired
    private LoaiDeXuatKienNghiRepository loaiDeXuatKienNghiRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<LoaiDeXuatKienNghi> findById(String id) {
        log.info(LogConstant.findById, LoaiDeXuatKienNghi.class.getSimpleName(), id);
        return loaiDeXuatKienNghiRepository.findById(id);
    }

    @Override
    public void deleteLoaiDeXuatKienNghi(LoaiDeXuatKienNghi loaiDeXuatKienNghi) {
        log.info(LogConstant.deleteById, LoaiDeXuatKienNghi.class.getSimpleName(), loaiDeXuatKienNghi.getPrimKey());
        loaiDeXuatKienNghiRepository.delete(loaiDeXuatKienNghi);
    }

    @Override
    public LoaiDeXuatKienNghi updateLoaiDeXuatKienNghi(LoaiDeXuatKienNghi loaiDeXuatKienNghi) {
        log.info(LogConstant.updateById, LoaiDeXuatKienNghi.class.getSimpleName(), loaiDeXuatKienNghi.getPrimKey());
        return loaiDeXuatKienNghiRepository.save(loaiDeXuatKienNghi);
    }

    @Override
    public Map<String, LoaiDeXuatKienNghi> update(Map<String, LoaiDeXuatKienNghi> map) {
        log.info(LogConstant.updateByMap, LoaiDeXuatKienNghi.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                loaiDeXuatKienNghiRepository.delete(v);
            } else {
                loaiDeXuatKienNghiRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<LoaiDeXuatKienNghi> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, LoaiDeXuatKienNghi.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, LoaiDeXuatKienNghi.class.getSimpleName(),
                new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, LoaiDeXuatKienNghi.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, LoaiDeXuatKienNghi.class));
    }

    @Override
    public List<LoaiDeXuatKienNghi> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiDeXuatKienNghi.class.getSimpleName(), maMuc,
                trangThaiDuLieu_MaMuc);
        return loaiDeXuatKienNghiRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<LoaiDeXuatKienNghi> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, LoaiDeXuatKienNghi.class.getSimpleName(), maMuc,
                trangThaiDuLieu_MaMuc);
        return loaiDeXuatKienNghiRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}