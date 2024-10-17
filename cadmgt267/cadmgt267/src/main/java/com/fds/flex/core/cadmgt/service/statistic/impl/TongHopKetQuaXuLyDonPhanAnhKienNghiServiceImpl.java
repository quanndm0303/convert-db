package com.fds.flex.core.cadmgt.service.statistic.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.entity.C_Model.LinhVucChuyenNganh;
import com.fds.flex.core.cadmgt.entity.C_Model.LoaiVuViecDonThu;
import com.fds.flex.core.cadmgt.entity.C_Model.TinhTrangXuLyDonThu;
import com.fds.flex.core.cadmgt.entity.T_Model.VuViecDonThu;
import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import com.fds.flex.core.cadmgt.service.statistic.TongHopKetQuaXuLyDonPhanAnhKienNghiService;
import com.fds.flex.modelbuilder.constant.DBConstant;
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
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Slf4j
@Service
public class TongHopKetQuaXuLyDonPhanAnhKienNghiServiceImpl implements TongHopKetQuaXuLyDonPhanAnhKienNghiService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //4.	Tổng số đơn phải xử lý - cột Kỳ trước chuyển sang (2)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));

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
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));
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
    public Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //4.	Tổng số đơn phải xử lý - cột Kỳ trước chuyển sang (2)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        LookupOperation lookupOperation = LookupOperation.newLookup().from(DBConstant.T_VU_VIEC_DON_THU)
                .localField("VuViecDonThu.MaDinhDanh").foreignField("MaDinhDanh").as("VuViecDonThuMapping");
        searchOperationList.add(lookupOperation);
        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("VuViecDonThuMapping.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));

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
        criteria.add(new Criteria().andOperator(Criteria.where("VuViecDonThuMapping.KiemTraThuLy").exists(true), Criteria.where("VuViecDonThuMapping.KiemTraThuLy.MaDinhDanh").ne(StringPool.BLANK)));

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
        //5.		Tổng số đơn phải xử lý - cột Tiếp nhận trong kỳ (3)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        LookupOperation lookupOperation = LookupOperation.newLookup().from(DBConstant.T_VU_VIEC_DON_THU)
                .localField("VuViecDonThu.MaDinhDanh").foreignField("MaDinhDanh").as("VuViecDonThuMapping");
        searchOperationList.add(lookupOperation);

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("VuViecDonThuMapping.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));
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
        criteria.add(new Criteria().andOperator(Criteria.where("VuViecDonThuMapping.KiemTraThuLy").exists(true), Criteria.where("VuViecDonThuMapping.KiemTraThuLy.MaDinhDanh").ne(StringPool.BLANK)));

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
        //11.	Số vụ việc đủ điều kiện xử lý - cột (8)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));

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
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));

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
    public Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //12.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Chế độ chính sách) - cột (9)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));

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
    public Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //13.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Đất đai nhà cửa) - cột (10)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));

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
    public Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //14.	Phân loại vụ việc theo nội dung, Lĩnh vực Tư pháp - cột (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));

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
    public Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //14.	Phân loại vụ việc theo nội dung, Lĩnh vực Tư pháp - cột (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));
        criteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        criteria.add(new Criteria().andOperator(Criteria.where("ThongBaoKetLuan").exists(true), Criteria.where("ThongBaoKetLuan.MaDinhDanh").ne(StringPool.BLANK)));


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
    public Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //18.	Vụ việc thuộc thẩm quyền giải quyết  - cột (15)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));

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
        andCriteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
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
    public Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //20.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Chuyển đơn - cột (17)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$ne", Arrays.asList(
                        "CoQuanThamQuyen.MaDinhDanh",
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
        //21.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Đôn đốc giải quyết - cột (18)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$ne", Arrays.asList(
                        "CoQuanThamQuyen.MaDinhDanh",
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
    public Map<String, Long> queryValue19(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //21.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Đôn đốc giải quyết - cột (18)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));
        criteria.add(Criteria.where("$expr")
                .is(new Document("$eq", Arrays.asList(
                        "CoQuanThamQuyen.MaDinhDanh",
                        "$CoQuanQuanLy.MaDinhDanh"
                ))));
        criteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(
                TinhTrangXuLyDonThu.Loai.LuuDon.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RaKetQuaGiaiQuyet.getMaMuc(),
                TinhTrangXuLyDonThu.Loai.RutDonChamDut.getMaMuc()
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
}
