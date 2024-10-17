package com.fds.flex.core.cadmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.entity.C_Model.TinhTrangXuLyDonThu;
import com.fds.flex.core.cadmgt.repository.TinhTrangXuLyDonThuRepository;
import com.fds.flex.core.cadmgt.service.CacheService;
import com.fds.flex.core.cadmgt.service.TinhTrangXuLyDonThuService;
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
public class TinhTrangXuLyDonThuServiceImpl implements TinhTrangXuLyDonThuService {

    @Autowired
    private TinhTrangXuLyDonThuRepository tinhTrangXuLyDonThuRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Override
    public Optional<TinhTrangXuLyDonThu> findById(String id) {
        return tinhTrangXuLyDonThuRepository.findById(id);
    }

    @Override
    public void deleteTinhTrangXuLyDonThu(TinhTrangXuLyDonThu tinhTrangXuLyDonThu) {
        tinhTrangXuLyDonThuRepository.delete(tinhTrangXuLyDonThu);
    }

    @Override
    public TinhTrangXuLyDonThu updateTinhTrangXuLyDonThu(TinhTrangXuLyDonThu tinhTrangXuLyDonThu) {
        return tinhTrangXuLyDonThuRepository.save(tinhTrangXuLyDonThu);
    }

    @Override
    public Map<String, TinhTrangXuLyDonThu> update(Map<String, TinhTrangXuLyDonThu> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                tinhTrangXuLyDonThuRepository.delete(v);
            } else {
                tinhTrangXuLyDonThuRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<TinhTrangXuLyDonThu> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable) {
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

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, TinhTrangXuLyDonThu.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, TinhTrangXuLyDonThu.class));
    }

    @Override
    public List<TinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc) {
        return tinhTrangXuLyDonThuRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<TinhTrangXuLyDonThu> findBySuDungChoXuLyDonThu_TTDL() {
        return tinhTrangXuLyDonThuRepository.findBySuDungChoXuLyDonThu_TTDL();
    }

    @Override
    public List<TinhTrangXuLyDonThu> findBySuDungChoVuViecDonThu_TTDL() {
        return tinhTrangXuLyDonThuRepository.findBySuDungChoVuViecDonThu_TTDL();
    }

    @Override
    public Optional<TinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc) {
        return tinhTrangXuLyDonThuRepository.findByMaMucAndTrangThaiDuLieu(maMuc, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}
