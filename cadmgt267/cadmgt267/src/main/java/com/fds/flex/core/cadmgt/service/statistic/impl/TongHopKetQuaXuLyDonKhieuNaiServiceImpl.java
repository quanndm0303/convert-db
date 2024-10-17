package com.fds.flex.core.cadmgt.service.statistic.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.entity.C_Model.LinhVucChuyenNganh;
import com.fds.flex.core.cadmgt.entity.C_Model.LoaiVuViecDonThu;
import com.fds.flex.core.cadmgt.entity.C_Model.TinhTrangXuLyDonThu;
import com.fds.flex.core.cadmgt.entity.T_Model.VuViecDonThu;
import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import com.fds.flex.core.cadmgt.service.statistic.TongHopKetQuaXuLyDonKhieuNaiService;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@Slf4j
public class TongHopKetQuaXuLyDonKhieuNaiServiceImpl implements TongHopKetQuaXuLyDonKhieuNaiService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //4.	Tổng số đơn phải xử lý - cột Kỳ trước chuyển sang (2)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(Criteria.where("NgayKetThuc").exists(false));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.PhanLoaiXuLyDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.XemXetThuLyDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.HuongDan.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.ChuyenDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.DonDocGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.LuuDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RutDonChamDut.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;


    }

    @Override
    public Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //5.		Tổng số đơn phải xử lý - cột Tiếp nhận trong kỳ (3)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.PhanLoaiXuLyDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.HuongDan.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.ChuyenDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.DonDocGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.LuuDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.XemXetThuLyDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RutDonChamDut.getMaMuc()
        )));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //6.	Số đơn đã xử lý - cột (4)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
        criteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.HuongDan.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.ChuyenDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.DonDocGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.LuuDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RutDonChamDut.getMaMuc()
        )));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //8.	Số đơn tiếp nhận trong kỳ và đã xử lý - cột (6)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.HuongDan.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.ChuyenDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.DonDocGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.LuuDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RutDonChamDut.getMaMuc()
        )));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //10.	Số đơn đủ điều kiện xử lý – nội dung khiếu nại - cột (7)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(Criteria.where("NgayKetThuc").exists(false));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //11.	Số vụ việc đủ điều kiện xử lý - cột (8)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(Criteria.where("NgayKetThuc").exists(false));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));
        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //12.	Vụ việc đã giải quyết lần đầu - cột (16)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
        criteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()));
        criteria.add(Criteria.where("LanGiaiQuyet").is(1));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //13.	Vụ việc đã giải quyết lần 2 - cột (17)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
        criteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()));
        criteria.add(Criteria.where("LanGiaiQuyet").gt(1));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //14.	Vụ việc đã có bản án TAND - cột (18)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
        criteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("KhoiKienHanhChinh.MaMuc").is("02"));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue20(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //16.	Vụ việc thuộc thẩm quyền - cột (20)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$eq", Arrays.asList(
                        "$CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(Criteria.where("NgayKetThuc").exists(false));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.XemXetThuLyDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.LuuDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RutDonChamDut.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();

        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.LuuDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.XemXetThuLyDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RutDonChamDut.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue22(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //17.	Vụ việc thuộc thẩm quyền, giải quyết lần 2 - cột (22)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$eq", Arrays.asList(
                        "$CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(Criteria.where("NgayKetThuc").exists(false));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.XemXetThuLyDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.LuuDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RutDonChamDut.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.LuuDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.XemXetThuLyDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RutDonChamDut.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));
        criteria.add(Criteria.where("LanGiaiQuyet").gt(1));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue24(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //19.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Hướng dẫn - cột (24)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$ne", Arrays.asList(
                        "$CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.HuongDan.getMaMuc()));

        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.HuongDan.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue25(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //20.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Đôn đốc giải quyết - cột (25)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$ne", Arrays.asList(
                        "$CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.DonDocGiaiQuyet.getMaMuc()));

        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.DonDocGiaiQuyet.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue26(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //22.	Số văn bản phúc đáp nhận được do chuyển đơn  - cột (26)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("VanBanPhucDap"));

        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$ne", Arrays.asList(
                        "$VuViecDonThu.CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));
        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.ChuyenDon.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.ChuyenDon.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));
        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("VanBanPhucDap.MaDinhDanh").as("VanBanPhucDap"));
        searchOperationList.add(Aggregation.project()
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(VanBanPhucDap)").as("VanBanPhucDap"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("VanBanPhucDap") ? obj.getLong("VanBanPhucDap") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //23.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính  - cột (9)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));

        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(Criteria.where("NgayKetThuc").exists(false));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));
        criteria.add(Criteria.where("LinhVucChuyenNganh.MaMuc").in(Arrays.asList(
                LinhVucChuyenNganh.LinhVuc.LinhVucHanhChinh.getMaMuc(),
                LinhVucChuyenNganh.LinhVuc.CheDoChinhSach.getMaMuc(),
                LinhVucChuyenNganh.LinhVuc.DatDaiNhaCua.getMaMuc(),
                LinhVucChuyenNganh.LinhVuc.CongChucCongVu.getMaMuc(),
                LinhVucChuyenNganh.LinhVuc.DauTuXayDungCoBan.getMaMuc(),
                LinhVucChuyenNganh.LinhVuc.TaiChinhNganSach.getMaMuc(),
                LinhVucChuyenNganh.LinhVuc.HanhChinhKhac.getMaMuc()
        )));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //24.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Chế độ chính sách) - cột (10)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));

        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(Criteria.where("NgayKetThuc").exists(false));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));

        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));
        criteria.add(Criteria.where("LinhVucChuyenNganh.MaMuc").is(LinhVucChuyenNganh.LinhVuc.CheDoChinhSach.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //25.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Đất đai nhà cửa) - cột (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));

        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(Criteria.where("NgayKetThuc").exists(false));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));

        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));
        criteria.add(Criteria.where("LinhVucChuyenNganh.MaMuc").is(LinhVucChuyenNganh.LinhVuc.DatDaiNhaCua.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //27.	Phân loại vụ việc theo nội dung, Lĩnh vực tư pháp - cột (13)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));

        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(Criteria.where("NgayKetThuc").exists(false));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));

        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));
        criteria.add(Criteria.where("LinhVucChuyenNganh.MaMuc").is(LinhVucChuyenNganh.LinhVuc.LinhVucTuPhap.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //28.	Phân loại vụ việc theo nội dung, Lĩnh vực Đảng, đoàn thể - cột (14)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));

        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(Criteria.where("NgayKetThuc").exists(false));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.ThuLyGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()
        )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));

        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepNhan").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc()));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));
        criteria.add(Criteria.where("LinhVucChuyenNganh.MaMuc").is(LinhVucChuyenNganh.LinhVuc.LinhVucDangToanThe.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }
}

