package com.fds.flex.core.cadmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.entity.C_Model.TinhTrangXuLyVuViec;
import com.fds.flex.core.cadmgt.repository.TinhTrangXuLyVuViecRepository;
import com.fds.flex.core.cadmgt.service.CacheService;
import com.fds.flex.core.cadmgt.service.TinhTrangXuLyVuViecService;
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
public class TinhTrangXuLyVuViecServiceImpl implements TinhTrangXuLyVuViecService {

    @Autowired
    private TinhTrangXuLyVuViecRepository tinhTrangXuLyVuViecRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<TinhTrangXuLyVuViec> findById(String id) {
        return tinhTrangXuLyVuViecRepository.findById(id);
    }

    @Override
    public void deleteTinhTrangXuLyVuViec(TinhTrangXuLyVuViec tinhTrangXuLyVuViec) {
        tinhTrangXuLyVuViecRepository.delete(tinhTrangXuLyVuViec);
    }

    @Override
    public TinhTrangXuLyVuViec updateTinhTrangXuLyVuViec(TinhTrangXuLyVuViec tinhTrangXuLyVuViec) {
        return tinhTrangXuLyVuViecRepository.save(tinhTrangXuLyVuViec);
    }

    @Override
    public Map<String, TinhTrangXuLyVuViec> update(Map<String, TinhTrangXuLyVuViec> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                tinhTrangXuLyVuViecRepository.delete(v);
            } else {
                tinhTrangXuLyVuViecRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<TinhTrangXuLyVuViec> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
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

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, TinhTrangXuLyVuViec.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, TinhTrangXuLyVuViec.class));
    }

    @Override
    public List<TinhTrangXuLyVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return tinhTrangXuLyVuViecRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<TinhTrangXuLyVuViec> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return tinhTrangXuLyVuViecRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<TinhTrangXuLyVuViec> thongKeTinhTrangXuLyVuViec() {
        return tinhTrangXuLyVuViecRepository.findByTrangThaiDuLieu(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}
