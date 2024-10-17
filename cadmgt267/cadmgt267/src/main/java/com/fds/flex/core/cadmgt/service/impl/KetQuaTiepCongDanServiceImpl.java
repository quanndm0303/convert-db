package com.fds.flex.core.cadmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.entity.C_Model.KetQuaTiepCongDan;
import com.fds.flex.core.cadmgt.repository.KetQuaTiepCongDanRepository;
import com.fds.flex.core.cadmgt.service.CacheService;
import com.fds.flex.core.cadmgt.service.KetQuaTiepCongDanService;
import com.fds.flex.core.cadmgt.util.CADMgtUtil;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
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
public class KetQuaTiepCongDanServiceImpl implements KetQuaTiepCongDanService {

    @Autowired
    private KetQuaTiepCongDanRepository ketQuaTiepCongDanRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<KetQuaTiepCongDan> findById(String id) {
        return ketQuaTiepCongDanRepository.findById(id);
    }

    @Override
    public void deleteKetQuaTiepCongDan(KetQuaTiepCongDan ketQuaTiepCongDan) {
        ketQuaTiepCongDanRepository.delete(ketQuaTiepCongDan);
    }

    @Override
    public KetQuaTiepCongDan updateKetQuaTiepCongDan(KetQuaTiepCongDan ketQuaTiepCongDan) {
        return ketQuaTiepCongDanRepository.save(ketQuaTiepCongDan);
    }

    @Override
    public Map<String, KetQuaTiepCongDan> update(Map<String, KetQuaTiepCongDan> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                ketQuaTiepCongDanRepository.delete(v);
            } else {
                ketQuaTiepCongDanRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<KetQuaTiepCongDan> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        Query query = new Query().with(pageable);

        if (!CADMgtUtil.checkSuperAdmin() && !CADMgtUtil.checkAdmin()) {
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

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, KetQuaTiepCongDan.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, KetQuaTiepCongDan.class));
    }

    @Override
    public List<KetQuaTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return ketQuaTiepCongDanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<KetQuaTiepCongDan> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc) {
        Query query = new Query();
        query.addCriteria(Criteria.where("TrangThaiDuLieu.MaMuc").is(trangThaiDuLieu_MaMuc));
        return mongoTemplate.find(query, KetQuaTiepCongDan.class);
    }

    @Override
    public Optional<KetQuaTiepCongDan> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return ketQuaTiepCongDanRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}
