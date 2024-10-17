package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.utility.datetime.TimeZoneUtil;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucPhatBoSung;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.repository.HinhThucPhatBoSungRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.HinhThucPhatBoSungService;
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
public class HinhThucPhatBoSungServiceImpl implements HinhThucPhatBoSungService {
    @Autowired
    private HinhThucPhatBoSungRepository hinhThucPhatBoSungRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<HinhThucPhatBoSung> findById(String id) {
        log.info(LogConstant.findById, HinhThucPhatBoSung.class.getSimpleName(), id);
        return hinhThucPhatBoSungRepository.findById(id);
    }

    @Override
    public void deleteHinhThucPhatBoSung(HinhThucPhatBoSung hinhThucPhatBoSung) {
        log.info(LogConstant.deleteById, HinhThucPhatBoSung.class.getSimpleName(), hinhThucPhatBoSung.getPrimKey());
        hinhThucPhatBoSungRepository.delete(hinhThucPhatBoSung);
    }

    @Override
    public HinhThucPhatBoSung updateHinhThucPhatBoSung(HinhThucPhatBoSung hinhThucPhatBoSung) {
        log.info(LogConstant.updateById, HinhThucPhatBoSung.class.getSimpleName(), hinhThucPhatBoSung.getPrimKey());
        return hinhThucPhatBoSungRepository.save(hinhThucPhatBoSung);
    }

    @Override
    public Map<String, HinhThucPhatBoSung> update(Map<String, HinhThucPhatBoSung> map) {
        log.info(LogConstant.updateByMap, HinhThucPhatBoSung.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                hinhThucPhatBoSungRepository.delete(v);
            } else {
                hinhThucPhatBoSungRepository.save(v);
            }
        });
        return map;
    }

    @Override
    public Page<HinhThucPhatBoSung> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, HinhThucPhatBoSung.class.getSimpleName());
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
        log.debug(LogConstant.finishGenerateQueryFilter, HinhThucPhatBoSung.class.getSimpleName(),
                new JSONObject(query));
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, HinhThucPhatBoSung.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, HinhThucPhatBoSung.class));
    }

    @Override
    public List<HinhThucPhatBoSung> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, HinhThucPhatBoSung.class.getSimpleName(), maMuc,
                trangThaiDuLieu_MaMuc);
        return hinhThucPhatBoSungRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<HinhThucPhatBoSung> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        log.info(LogConstant.findByMaMucAndTTDL, HinhThucPhatBoSung.class.getSimpleName(), maMuc,
                trangThaiDuLieu_MaMuc);
        return hinhThucPhatBoSungRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}