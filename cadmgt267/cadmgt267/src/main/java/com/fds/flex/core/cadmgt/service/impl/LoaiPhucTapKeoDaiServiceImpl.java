package com.fds.flex.core.cadmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.entity.C_Model.LoaiPhucTapKeoDai;
import com.fds.flex.core.cadmgt.repository.LoaiPhucTapKeoDaiRepository;
import com.fds.flex.core.cadmgt.service.CacheService;
import com.fds.flex.core.cadmgt.service.LoaiPhucTapKeoDaiService;
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
public class LoaiPhucTapKeoDaiServiceImpl implements LoaiPhucTapKeoDaiService {

    @Autowired
    private LoaiPhucTapKeoDaiRepository loaiPhucTapKeoDaiRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<LoaiPhucTapKeoDai> findById(String id) {
        return loaiPhucTapKeoDaiRepository.findById(id);
    }

    @Override
    public void deleteLoaiPhucTapKeoDai(LoaiPhucTapKeoDai loaiPhucTapKeoDai) {
        loaiPhucTapKeoDaiRepository.delete(loaiPhucTapKeoDai);
    }

    @Override
    public LoaiPhucTapKeoDai updateLoaiPhucTapKeoDai(LoaiPhucTapKeoDai loaiPhucTapKeoDai) {
        return loaiPhucTapKeoDaiRepository.save(loaiPhucTapKeoDai);
    }

    @Override
    public Map<String, LoaiPhucTapKeoDai> update(Map<String, LoaiPhucTapKeoDai> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                loaiPhucTapKeoDaiRepository.delete(v);
            } else {
                loaiPhucTapKeoDaiRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<LoaiPhucTapKeoDai> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        Query query = new Query().with(pageable);

        if (!CADMgtUtil.checkSuperAdmin()) {
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

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, LoaiPhucTapKeoDai.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, LoaiPhucTapKeoDai.class));
    }

    @Override
    public List<LoaiPhucTapKeoDai> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return loaiPhucTapKeoDaiRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<LoaiPhucTapKeoDai> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return loaiPhucTapKeoDaiRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}
