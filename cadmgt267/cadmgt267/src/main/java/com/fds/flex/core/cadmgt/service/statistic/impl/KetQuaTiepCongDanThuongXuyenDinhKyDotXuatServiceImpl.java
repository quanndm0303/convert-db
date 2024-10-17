package com.fds.flex.core.cadmgt.service.statistic.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.entity.C_Model.CheDoTiepCongDan;
import com.fds.flex.core.cadmgt.entity.T_Model.TiepCongDan;
import com.fds.flex.core.cadmgt.service.statistic.KetQuaTiepCongDanThuongXuyenDinhKyDotXuatService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@Slf4j
public class KetQuaTiepCongDanThuongXuyenDinhKyDotXuatServiceImpl implements KetQuaTiepCongDanThuongXuyenDinhKyDotXuatService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //4.	Tiếp thường xuyên  - Số lượt tiếp  cột (4)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.TiepThuongXuyen.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //5.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp – Số lượt tiếp cột (13)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.ThuTruongTiep.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue22(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //6.	Tiếp định kỳ và đột xuất - Thủ trưởng ỦY QUYỀN – Số lượt tiếp cột (22)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.UyQuyenCuaThuTruongTiepDinhKy.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //8.	Tiếp thường xuyên  - Số người được tiếp  cột (5)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.TiepThuongXuyen.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("SoNguoiDuocTiep").as("SoNguoiDuocTiep"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoNguoiDuocTiep") ? obj.getLong("SoNguoiDuocTiep") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //9.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp – Số người được tiếp cột (14)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.ThuTruongTiep.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("SoNguoiDuocTiep").as("SoNguoiDuocTiep"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoNguoiDuocTiep") ? obj.getLong("SoNguoiDuocTiep") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue23(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //10.	Tiếp định kỳ và đột xuất - Thủ trưởng ỦY QUYỀN – Số người được tiếp cột (23)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.UyQuyenCuaThuTruongTiepDinhKy.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("SoNguoiDuocTiep").as("SoNguoiDuocTiep"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoNguoiDuocTiep") ? obj.getLong("SoNguoiDuocTiep") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //12.	Tiếp thường xuyên  - Số vụ việc tiếp lần đầu - cột (6)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.TiepThuongXuyen.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").is(1));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //13.	Tiếp thường xuyên  - Số vụ việc tiếp nhiều lần - cột (7)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.TiepThuongXuyen.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").gte(2));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //14.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp – Số vụ việc tiếp lần đầu -  cột (15)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.ThuTruongTiep.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").is(1));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //15.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp – Số vụ việc tiếp nhiều lần -  cột (16)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.ThuTruongTiep.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").gte(2));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue24(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //16.	Tiếp định kỳ và đột xuất - Thủ trưởng ỦY QUYỀN – Số vụ việc tiếp lần đầu - cột (24)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.UyQuyenCuaThuTruongTiepDinhKy.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").is(1));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue25(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //17.	Tiếp định kỳ và đột xuất - Thủ trưởng ỦY QUYỀN – Số vụ việc tiếp nhiều lần - cột (25)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.UyQuyenCuaThuTruongTiepDinhKy.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").gte(2));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //19.	Tiếp thường xuyên  - Đoàn đông người - Số đoàn tiếp  cột (8)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.TiepThuongXuyen.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //20.	Tiếp thường xuyên  - Đoàn đông người - Số người được tiếp  cột (9)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.TiepThuongXuyen.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("SoNguoiDuocTiep").as("SoNguoiDuocTiep"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoNguoiDuocTiep") ? obj.getLong("SoNguoiDuocTiep") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //21.	Tiếp thường xuyên  - Đoàn đông người - Số vụ việc tiếp lần đầu - cột (10)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.TiepThuongXuyen.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").is(1));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //22.	Tiếp thường xuyên  - Đoàn đông người - Số vụ việc tiếp nhiều lần - cột (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.TiepThuongXuyen.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").gte(2));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //23.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp – Số kỳ tiếp  - cột (12)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.ThuTruongTiep.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("KyTiepCongDan").as("KyTiepCongDan"));
        searchOperationList.add(Aggregation.project()
                
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(KyTiepCongDan)").as("KyTiepCongDan"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("KyTiepCongDan") ? obj.getLong("KyTiepCongDan") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue21(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //24.	Tiếp định kỳ và đột xuất - Thủ trưởng ủy quyền tiếp – Số kỳ tiếp  - cột (21)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.UyQuyenCuaThuTruongTiepDinhKy.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("KyTiepCongDan").as("KyTiepCongDan"));
        searchOperationList.add(Aggregation.project()
                .andExpression(Constant._ID).as(Constant._ID)
                .andExpression("size(KyTiepCongDan)").as("KyTiepCongDan"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("KyTiepCongDan") ? obj.getLong("KyTiepCongDan") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //25.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp  - Đoàn đông người - Số đoàn tiếp  cột (17)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.ThuTruongTiep.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //26.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp - Đoàn đông người - Số người được tiếp  cột (18)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.ThuTruongTiep.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("SoNguoiDuocTiep").as("SoNguoiDuocTiep"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoNguoiDuocTiep") ? obj.getLong("SoNguoiDuocTiep") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue19(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //27.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp  - Đoàn đông người - Số vụ việc tiếp lần đầu - cột (19)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.ThuTruongTiep.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").is(1));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue20(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //28.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp  - Đoàn đông người - Số vụ việc tiếp nhiều lần - cột (20)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.ThuTruongTiep.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").gte(2));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue26(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //29.	Tiếp định kỳ và đột xuất - Thủ trưởng ủy quyền tiếp - Đoàn đông người - Số đoàn tiếp  cột (26)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.UyQuyenCuaThuTruongTiepDinhKy.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue27(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //30.	Tiếp định kỳ và đột xuất - Thủ trưởng ủy quyền tiếp  - Đoàn đông người - Số người được tiếp  cột (27)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.UyQuyenCuaThuTruongTiepDinhKy.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("SoNguoiDuocTiep").as("SoNguoiDuocTiep"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoNguoiDuocTiep") ? obj.getLong("SoNguoiDuocTiep") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue28(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //31.	Tiếp định kỳ và đột xuất - Thủ trưởng ủy quyền tiếp - Đoàn đông người - Số vụ việc tiếp lần đầu - cột (28)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.UyQuyenCuaThuTruongTiepDinhKy.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").is(1));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as(Constant._SO_LUONG));
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
                    resultMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue29(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //31.	Tiếp định kỳ và đột xuất - Thủ trưởng ủy quyền tiếp - Đoàn đông người - Số vụ việc tiếp lần đầu - cột (28)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").is(CheDoTiepCongDan.CheDo.UyQuyenCuaThuTruongTiepDinhKy.getMaMuc()));
        criteria.add(Criteria.where("NgayTiepCongDan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("SoNguoiDuocTiep").gte(5));
        criteria.add(Criteria.where("VuViecDonThu.LanTiepThu").gte(2));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as("SoNguoiDuocTiep"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoNguoiDuocTiep") ? obj.getLong("SoNguoiDuocTiep") : 0L);
                }
            }
        }
        return resultMap;
    }
}
