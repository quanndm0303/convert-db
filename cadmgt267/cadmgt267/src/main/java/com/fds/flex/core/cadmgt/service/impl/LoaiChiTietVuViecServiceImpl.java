package com.fds.flex.core.cadmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.entity.C_Model.LoaiChiTietVuViec;
import com.fds.flex.core.cadmgt.repository.LoaiChiTietVuViecRepository;
import com.fds.flex.core.cadmgt.service.CacheService;
import com.fds.flex.core.cadmgt.service.LoaiChiTietVuViecService;
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
public class LoaiChiTietVuViecServiceImpl implements LoaiChiTietVuViecService {

    @Autowired
    private LoaiChiTietVuViecRepository loaiChiTietVuViecRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<LoaiChiTietVuViec> findById(String id) {
        return loaiChiTietVuViecRepository.findById(id);
    }

    @Override
    public void deleteLoaiChiTietVuViec(LoaiChiTietVuViec loaiChiTietVuViec) {
        loaiChiTietVuViecRepository.delete(loaiChiTietVuViec);
    }

    @Override
    public LoaiChiTietVuViec updateLoaiChiTietVuViec(LoaiChiTietVuViec loaiChiTietVuViec) {
        return loaiChiTietVuViecRepository.save(loaiChiTietVuViec);
    }

    @Override
    public Map<String, LoaiChiTietVuViec> update(Map<String, LoaiChiTietVuViec> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                loaiChiTietVuViecRepository.delete(v);
            } else {
                loaiChiTietVuViecRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<LoaiChiTietVuViec> filter(String keyword, String loaiVuViecDonThu_MaMuc, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
        Query query = new Query().with(pageable);

        if (!CADMgtUtil.checkSuperAdmin() && !CADMgtUtil.checkAdmin()) {
            query.fields().include("MaMuc", "TenMuc", "LoaiVuViecDonThu");
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
        if (Validator.isNotNull(loaiVuViecDonThu_MaMuc)) {
            Criteria c = Criteria.where("LoaiVuViecDonThu.MaMuc").is(loaiVuViecDonThu_MaMuc);
            criteria.add(c);
        }
        if (Validator.isNotNull(trangThaiDuLieu_MaMuc) && trangThaiDuLieu_MaMuc.length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").in(Arrays.asList(trangThaiDuLieu_MaMuc));
            criteria.add(c);
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, LoaiChiTietVuViec.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, LoaiChiTietVuViec.class));
    }

    @Override
    public List<LoaiChiTietVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return loaiChiTietVuViecRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<LoaiChiTietVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return loaiChiTietVuViecRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}
