package com.fds.flex.core.cadmgt.service.statistic.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.entity.C_Model.LoaiVuViecDonThu;
import com.fds.flex.core.cadmgt.entity.T_Model.TiepCongDan;
import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import com.fds.flex.core.cadmgt.service.statistic.PhanLoaiXuLyDonThuQuaTiepCongDanService;
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
public class PhanLoaiXuLyDonThuQuaTiepCongDanServiceImpl implements PhanLoaiXuLyDonThuQuaTiepCongDanService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //4.	Số đơn - nội dung Khiếu nại cột (3)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("HinhThucNhanDonThu.MaMuc").is("02"));
        criteria.add(new Criteria().andOperator(Criteria.where("TiepCongDan").exists(true), Criteria.where("TiepCongDan.MaDinhDanh").ne(StringPool.BLANK)));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));

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
    public Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //5.	Số đơn - nội dung Tố cáo cột (5)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("HinhThucNhanDonThu.MaMuc").is("02"));
        criteria.add(new Criteria().andOperator(Criteria.where("TiepCongDan").exists(true), Criteria.where("TiepCongDan.MaDinhDanh").ne(StringPool.BLANK)));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.ToCao.getMaMuc()));

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

        //6.	Số đơn - nội dung Phản ánh, kiến nghị cột (7)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("HinhThucNhanDonThu.MaMuc").is("02"));
        criteria.add(new Criteria().andOperator(Criteria.where("TiepCongDan").exists(true), Criteria.where("TiepCongDan.MaDinhDanh").ne(StringPool.BLANK)));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));

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
    public Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //7.	Số đơn - nội dung Thuộc thẩm quyền cột (9)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("HinhThucNhanDonThu.MaMuc").is("02"));
        criteria.add(new Criteria().andOperator(Criteria.where("TiepCongDan").exists(true), Criteria.where("TiepCongDan.MaDinhDanh").ne(StringPool.BLANK)));
        criteria.add(Criteria.where("VuViecDonThu.LanGiaiQuyet").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));

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
    public Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //8.	Số đơn - nội dung KHÔNG thuộc thẩm quyền cột (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepNhan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("HinhThucNhanDonThu.MaMuc").is("02"));
        criteria.add(new Criteria().andOperator(Criteria.where("TiepCongDan").exists(true), Criteria.where("TiepCongDan.MaDinhDanh").ne(StringPool.BLANK)));
        criteria.add(Criteria.where("VuViecDonThu.LanGiaiQuyet").exists(false));

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

        //8.	Số đơn - nội dung KHÔNG thuộc thẩm quyền cột (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is("01"));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("VuViecDonThu.MaDinhDanh").as("VuViecDonThu"));
        searchOperationList.add(Aggregation.project()
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(VuViecDonThu)").as("VuViecDonThu"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, TiepCongDan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("VuViecDonThu") ? obj.getLong("VuViecDonThu") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //8.	Số đơn - nội dung KHÔNG thuộc thẩm quyền cột (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is("01"));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("VuViecDonThu.MaDinhDanh").as("VuViecDonThu"));
        searchOperationList.add(Aggregation.project()
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(VuViecDonThu)").as("VuViecDonThu"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, TiepCongDan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("VuViecDonThu") ? obj.getLong("VuViecDonThu") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //8.	Số đơn - nội dung KHÔNG thuộc thẩm quyền cột (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is("03"));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("VuViecDonThu.MaDinhDanh").as("VuViecDonThu"));
        searchOperationList.add(Aggregation.project()
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(VuViecDonThu)").as("VuViecDonThu"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, TiepCongDan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("VuViecDonThu") ? obj.getLong("VuViecDonThu") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //8.	Số đơn - nội dung KHÔNG thuộc thẩm quyền cột (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("VuViecDonThu.LanGiaiQuyet").gte(0));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$eq", Arrays.asList(
                        "$VuViecDonThu.CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("VuViecDonThu.MaDinhDanh").as("VuViecDonThu"));
        searchOperationList.add(Aggregation.project()
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(VuViecDonThu)").as("VuViecDonThu"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, TiepCongDan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("VuViecDonThu") ? obj.getLong("VuViecDonThu") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //13.	Số vụ việc KHÔNG thuộc thẩm quyền - cột (12)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$ne", Arrays.asList(
                        "$VuViecDonThu.CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));
        criteria.add(Criteria.where("VuViecDonThu.LanGiaiQuyet").exists(false));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("VuViecDonThu.MaDinhDanh").as("VuViecDonThu"));
        searchOperationList.add(Aggregation.project()
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(VuViecDonThu)").as("VuViecDonThu"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, TiepCongDan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("VuViecDonThu") ? obj.getLong("VuViecDonThu") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //14.	Số vụ việc KHÔNG thuộc thẩm quyền, hướng dẫn - cột (13)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$ne", Arrays.asList(
                        "$VuViecDonThu.CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));
        criteria.add(Criteria.where("VuViecDonThu.LanGiaiQuyet").exists(false));
        criteria.add(Criteria.where("VuViecDonThu.TinhTrangXuLyDonThu.MaMuc").is("03"));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("VuViecDonThu.MaDinhDanh").as("VuViecDonThu"));
        searchOperationList.add(Aggregation.project()
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(VuViecDonThu)").as("VuViecDonThu"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, TiepCongDan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("VuViecDonThu") ? obj.getLong("VuViecDonThu") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //15.	Số vụ việc KHÔNG thuộc thẩm quyền, Chuyển đơn - cột (14)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$ne", Arrays.asList(
                        "$VuViecDonThu.CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));
        criteria.add(Criteria.where("VuViecDonThu.LanGiaiQuyet").exists(false));
        criteria.add(Criteria.where("VuViecDonThu.TinhTrangXuLyDonThu.MaMuc").is("04"));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("VuViecDonThu.MaDinhDanh").as("VuViecDonThu"));
        searchOperationList.add(Aggregation.project()
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(VuViecDonThu)").as("VuViecDonThu"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, TiepCongDan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("VuViecDonThu") ? obj.getLong("VuViecDonThu") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //16.	Số vụ việc KHÔNG thuộc thẩm quyền, Đôn đốc giải quyết - cột (15)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$ne", Arrays.asList(
                        "$VuViecDonThu.CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));
        criteria.add(Criteria.where("VuViecDonThu.LanGiaiQuyet").exists(false));
        criteria.add(Criteria.where("VuViecDonThu.TinhTrangXuLyDonThu.MaMuc").is("05"));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("VuViecDonThu.MaDinhDanh").as("VuViecDonThu"));
        searchOperationList.add(Aggregation.project()
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(VuViecDonThu)").as("VuViecDonThu"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, TiepCongDan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("VuViecDonThu") ? obj.getLong("VuViecDonThu") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //17.	Số vụ việc KHÔNG thuộc thẩm quyền, nhận được văn bản phúc đáp do chuyển đơn - cột (16)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("VanBanPhucDap"));

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$ne", Arrays.asList(
                        "$VuViecDonThu.CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").is("04"));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteriaLv1.add(Criteria.where("HinhThucNhanDonThu.MaMuc").is("02"));
        criteria.add(new Criteria().andOperator(Criteria.where("TiepCongDan").exists(true), Criteria.where("TiepCongDan.MaDinhDanh").ne(StringPool.BLANK)));
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
}
