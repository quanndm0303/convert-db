package com.fds.flex.core.inspectionmgt.service.statistic.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.*;
import com.fds.flex.core.inspectionmgt.entity.T_Model.HoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.entity.T_Model.ThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.service.LoaiDoiTuongCNTCService;
import com.fds.flex.core.inspectionmgt.service.statistic.CongTacThanhTraChuyenNganhStatisticService;
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
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@Slf4j
public class CongTacThanhTraChuyenNganhStatisticServiceImpl implements CongTacThanhTraChuyenNganhStatisticService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LoaiDoiTuongCNTCService loaiDoiTuongCNTCService;

    @Override
    public Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //4.	Số cuộc thanh tra thực hiện trong kỳ - cột Theo Kế hoạch (2)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("LoaiHoatDongThanhTra.MaMuc").is(LoaiHoatDongThanhTra.Loai.ThanhTraChuyenNganh.getMaMuc()));
        criteria.add(Criteria.where("LoaiCheDoThanhTra.MaMuc").in(Arrays.asList(LoaiCheDoThanhTra.Loai.DinhKy.getMaMuc(), LoaiCheDoThanhTra.Loai.DotXuat.getMaMuc(), "03")));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        List<Criteria> criteriaLv1 = new ArrayList<>();

        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").exists(false), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(
                Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("GiaHanThucHien").gte(ca_from).lte(ca_to), Criteria.where("GiaHanThucHien").gt(ca_to)));
        andCriteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(
                Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
                )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        andCriteria.add(Criteria.where("GiaHanThucHien").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").exists(false), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(
                Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("ThoiHanThucHien").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(
                Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
                )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        criteria.add(new Criteria().orOperator(criteriaLv1));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as("SoLuong"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, HoatDongThanhTra.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong("SoLuong", 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //5.	Số cuộc thanh tra thực hiện trong kỳ - cột Đột xuất (3)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("LoaiHoatDongThanhTra.MaMuc").is(LoaiHoatDongThanhTra.Loai.ThanhTraChuyenNganh.getMaMuc()));
        criteria.add(Criteria.where("LoaiCheDoThanhTra.MaMuc").is("04"));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").exists(false), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(
                Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("GiaHanThucHien").gte(ca_from).lte(ca_to), Criteria.where("GiaHanThucHien").gt(ca_to)));
        andCriteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(
                Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
                )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        andCriteria.add(Criteria.where("GiaHanThucHien").lt(ca_from));
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").exists(false), Criteria.where("NgayKetThuc").gt(ca_to)));
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(Arrays.asList(
                TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("ThoiHanThucHien").gte(ca_from).lte(ca_to));
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(
                Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
                )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));

        criteria.add(new Criteria().orOperator(criteriaLv1));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").count().as("SoLuong"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, HoatDongThanhTra.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.optLong("SoLuong", 0L));
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //6.	Số cuộc thanh tra đã ban hành kết luận(4)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc()));
        criteria.add(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("DoiTuongKetLuan.@type").is(DBConstant.T_HOAT_DONG_THANH_TRA));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));

        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("DoiTuongKetLuan.MaDinhDanh").as("DoiTuongKetLuan"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(DoiTuongKetLuan)").as("DoiTuongKetLuan"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("DoiTuongKetLuan") ? obj.getLong("DoiTuongKetLuan") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<LoaiDoiTuongCNTC> loaiDoiTuongCNTCList = loaiDoiTuongCNTCService.findByThamChieuMaMucAndTrangThaiDuLieu("04", new String[]{TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()});
        List<String> maMuc = loaiDoiTuongCNTCList.stream().map(LoaiDoiTuongCNTC::getMaMuc).collect(Collectors.toList());
        maMuc.addAll(Arrays.asList("04", "05", "06"));

        //7.	Số tổ chức được thanh tra theo Kết luận(5)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        LookupOperation lookupOperation = LookupOperation.newLookup().from(DBConstant.T_HOAT_DONG_THANH_TRA)
                .localField("DoiTuongKetLuan.MaDinhDanh").foreignField("MaDinhDanh").as("HoatDongThanhTraMapping");
        searchOperationList.add(lookupOperation);
        searchOperationList.add(Aggregation.unwind("HoatDongThanhTraMapping"));
        searchOperationList.add(Aggregation.unwind("HoatDongThanhTraMapping.DoiTuongThanhTra"));
        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc()));
        criteria.add(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("DoiTuongKetLuan.@type").is(DBConstant.T_HOAT_DONG_THANH_TRA));
        criteria.add(Criteria.where("HoatDongThanhTraMapping.DoiTuongThanhTra.LoaiDoiTuongCNTC.MaMuc").in(maMuc));
        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("HoatDongThanhTraMapping.DoiTuongThanhTra.MaDinhDanh").as("DoiTuongThanhTra"));

        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(DoiTuongThanhTra)").as("DoiTuongThanhTra"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("DoiTuongThanhTra") ? obj.getLong("DoiTuongThanhTra") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //8.	Số cá nhân được thanh tra theo Kết luận(6)

        List<AggregationOperation> searchOperationList = new ArrayList<>();
        LookupOperation lookupOperation = LookupOperation.newLookup().from(DBConstant.T_HOAT_DONG_THANH_TRA)
                .localField("DoiTuongKetLuan.MaDinhDanh").foreignField("MaDinhDanh").as("HoatDongThanhTraMapping");
        searchOperationList.add(lookupOperation);
        searchOperationList.add(Aggregation.unwind("HoatDongThanhTraMapping"));
        searchOperationList.add(Aggregation.unwind("HoatDongThanhTraMapping.DoiTuongThanhTra"));
        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc()));
        criteria.add(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("DoiTuongKetLuan.@type").is(DBConstant.T_HOAT_DONG_THANH_TRA));
        criteria.add(Criteria.where("HoatDongThanhTraMapping.DoiTuongThanhTra.LoaiDoiTuongCNTC.MaMuc").in(Arrays.asList("01", "02", "03")));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("HoatDongThanhTraMapping.DoiTuongThanhTra.MaDinhDanh").as("DoiTuongThanhTra"));

        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(DoiTuongThanhTra)").as("DoiTuongThanhTra"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("DoiTuongThanhTra") ? obj.getLong("DoiTuongThanhTra") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<LoaiDoiTuongCNTC> loaiDoiTuongCNTCList = loaiDoiTuongCNTCService.findByThamChieuMaMucAndTrangThaiDuLieu("04", new String[]{TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()});
        List<String> maMuc = loaiDoiTuongCNTCList.stream().map(LoaiDoiTuongCNTC::getMaMuc).collect(Collectors.toList());
        maMuc.addAll(Arrays.asList("04", "05", "06"));

        //9.	Số tổ chức vi phạm cột (8)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi.DoiTuongXuLy"));

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        //b.	Lọc theo Loại theo dõi kết luận thanh kiểm tra
        criteria.add(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc()));
        //c.	Lọc Ngày ban hành kết luận : -	NgayVanBan trong khoảng (tungay và denngay)
        criteria.add(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("DeXuatKienNghi.DoiTuongXuLy.LoaiDoiTuongCNTC.MaMuc").in(maMuc));
        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("DeXuatKienNghi.DoiTuongXuLy.MaDinhDanh").as("DoiTuongXuLy"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(DoiTuongXuLy)").as("DoiTuongXuLy"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("DoiTuongXuLy") ? obj.getLong("DoiTuongXuLy") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //9.	Số tổ chức vi phạm cột (8)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi.DoiTuongXuLy"));

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        criteria.add(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc()));
        criteria.add(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to));
        criteria.add(Criteria.where("DeXuatKienNghi.DoiTuongXuLy.LoaiDoiTuongCNTC.MaMuc").in(Arrays.asList("01", "02", "03")));
        searchOperationList.add(Aggregation.match(new Criteria().andOperator(criteria)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("DeXuatKienNghi.DoiTuongXuLy.MaDinhDanh").as("DoiTuongXuLy"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(DoiTuongXuLy)").as("DoiTuongXuLy"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("DoiTuongXuLy") ? obj.getLong("DoiTuongXuLy") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<LoaiDoiTuongCNTC> loaiDoiTuongCNTCList = loaiDoiTuongCNTCService.findByThamChieuMaMucAndTrangThaiDuLieu("04", new String[]{TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()});
        List<String> maMuc = loaiDoiTuongCNTCList.stream().map(LoaiDoiTuongCNTC::getMaMuc).collect(Collectors.toList());
        maMuc.addAll(Arrays.asList("04", "05", "06"));

        //12.	Số Tiền (Tr.đ) vi phạm của Tổ chức cột (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.DoiTuongXuLy.LoaiDoiTuongCNTC.MaMuc").in(maMuc)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is(LoaiDeXuatKienNghi.Loai.XuLyViPhamVeKinhTe.getMaMuc())));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("DeXuatKienNghi.SoTienThuHoi").as("SoTienThuHoi"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoTienThuHoi") ? obj.getLong("SoTienThuHoi") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //13.	Số Tiền (Tr.đ) vi phạm của Cá nhân cột (12)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.DoiTuongXuLy.LoaiDoiTuongCNTC.MaMuc").in(Arrays.asList("01", "02", "03"))));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is(LoaiDeXuatKienNghi.Loai.XuLyViPhamVeKinhTe.getMaMuc())));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("DeXuatKienNghi.SoTienThuHoi").as("SoTienThuHoi"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoTienThuHoi") ? obj.getLong("SoTienThuHoi") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //13.	Số Tiền (Tr.đ) vi phạm của Cá nhân cột (12)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is(LoaiDeXuatKienNghi.Loai.ThuHoiVeNSNN.getMaMuc())));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("DeXuatKienNghi.SoTienThuHoi").as("SoTienThuHoi"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoTienThuHoi") ? obj.getLong("SoTienThuHoi") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //13.	Số Tiền (Tr.đ) vi phạm của Cá nhân cột (12)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is(LoaiDeXuatKienNghi.Loai.TraLaiKinhTeChoToChucCaNhan.getMaMuc())));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("DeXuatKienNghi.SoTienThuHoi").as("SoTienThuHoi"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("SoTienThuHoi") ? obj.getLong("SoTienThuHoi") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<LoaiDoiTuongCNTC> loaiDoiTuongCNTCList = loaiDoiTuongCNTCService.findByThamChieuMaMucAndTrangThaiDuLieu("04", new String[]{TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()});
        List<String> maMuc = loaiDoiTuongCNTCList.stream().map(LoaiDoiTuongCNTC::getMaMuc).collect(Collectors.toList());
        maMuc.addAll(Arrays.asList("04", "05", "06"));

        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("XuLyVPHC"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("XuLyVPHC.DoiTuongVPHC.LoaiDoiTuongCNTC.MaMuc").in(maMuc)));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("XuLyVPHC.MaDinhDanh").as("XuLyVPHC"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(XuLyVPHC)").as("XuLyVPHC"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("XuLyVPHC") ? obj.getLong("XuLyVPHC") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue19(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("XuLyVPHC"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("XuLyVPHC.DoiTuongVPHC.LoaiDoiTuongCNTC.MaMuc").in(Arrays.asList("01", "02", "03"))));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("XuLyVPHC.MaDinhDanh").as("XuLyVPHC"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(XuLyVPHC)").as("XuLyVPHC"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("XuLyVPHC") ? obj.getLong("XuLyVPHC") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue21(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<LoaiDoiTuongCNTC> loaiDoiTuongCNTCList = loaiDoiTuongCNTCService.findByThamChieuMaMucAndTrangThaiDuLieu("04", new String[]{TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()});
        List<String> maMuc = loaiDoiTuongCNTCList.stream().map(LoaiDoiTuongCNTC::getMaMuc).collect(Collectors.toList());
        maMuc.addAll(Arrays.asList("04", "05", "06"));

        List<AggregationOperation> searchOperationList = new ArrayList<>();
        LookupOperation lookupOperation = LookupOperation.newLookup().from(DBConstant.T_XU_LY_VPHC)
                .localField("XuLyVPHC.MaDinhDanh").foreignField("MaDinhDanh").as("XuLyVPHCMapping");
        searchOperationList.add(lookupOperation);
        searchOperationList.add(Aggregation.unwind("XuLyVPHCMapping"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("XuLyVPHCMapping.DoiTuongVPHC.LoaiDoiTuongCNTC.MaMuc").in(maMuc)));
        searchOperationList.add(Aggregation.match(Criteria.where("XuLyVPHCMapping.HinhThucXuLyChinh.MaMuc").is("03")));

        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("XuLyVPHCMapping.MaDinhDanh").as("XuLyVPHC"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(XuLyVPHC)").as("XuLyVPHC"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("XuLyVPHC") ? obj.getLong("XuLyVPHC") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue22(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<AggregationOperation> searchOperationList = new ArrayList<>();
        LookupOperation lookupOperation = LookupOperation.newLookup().from(DBConstant.T_XU_LY_VPHC)
                .localField("XuLyVPHC.MaDinhDanh").foreignField("MaDinhDanh").as("XuLyVPHCMapping");
        searchOperationList.add(lookupOperation);
        searchOperationList.add(Aggregation.unwind("XuLyVPHCMapping"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("XuLyVPHCMapping.DoiTuongVPHC.LoaiDoiTuongCNTC.MaMuc").in(Arrays.asList("01", "02", "03"))));
        searchOperationList.add(Aggregation.match(Criteria.where("XuLyVPHCMapping.HinhThucXuLyChinh.MaMuc").is("03")));

        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("XuLyVPHCMapping.MaDinhDanh").as("XuLyVPHC"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(XuLyVPHC)").as("XuLyVPHC"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("XuLyVPHC") ? obj.getLong("XuLyVPHC") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue24(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<LoaiDoiTuongCNTC> loaiDoiTuongCNTCList = loaiDoiTuongCNTCService.findByThamChieuMaMucAndTrangThaiDuLieu("04", new String[]{TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()});
        List<String> maMuc = loaiDoiTuongCNTCList.stream().map(LoaiDoiTuongCNTC::getMaMuc).collect(Collectors.toList());
        maMuc.addAll(Arrays.asList("04", "05", "06"));

        List<AggregationOperation> searchOperationList = new ArrayList<>();
        LookupOperation lookupOperation = LookupOperation.newLookup().from(DBConstant.T_XU_LY_VPHC)
                .localField("XuLyVPHC.MaDinhDanh").foreignField("MaDinhDanh").as("XuLyVPHCMapping");
        searchOperationList.add(lookupOperation);
        searchOperationList.add(Aggregation.unwind("XuLyVPHCMapping"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("XuLyVPHCMapping.DoiTuongVPHC.LoaiDoiTuongCNTC.MaMuc").in(maMuc)));
        searchOperationList.add(Aggregation.match(Criteria.where("XuLyVPHCMapping.HinhThucXuLyChinh.MaMuc").ne("03")));

        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("XuLyVPHCMapping.MaDinhDanh").as("XuLyVPHC"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(XuLyVPHC)").as("XuLyVPHC"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("XuLyVPHC") ? obj.getLong("XuLyVPHC") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue25(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<AggregationOperation> searchOperationList = new ArrayList<>();
        LookupOperation lookupOperation = LookupOperation.newLookup().from(DBConstant.T_XU_LY_VPHC)
                .localField("XuLyVPHC.MaDinhDanh").foreignField("MaDinhDanh").as("XuLyVPHCMapping");
        searchOperationList.add(lookupOperation);
        searchOperationList.add(Aggregation.unwind("XuLyVPHCMapping"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("XuLyVPHCMapping.DoiTuongVPHC.LoaiDoiTuongCNTC.MaMuc").in(Arrays.asList("01", "02", "03"))));
        searchOperationList.add(Aggregation.match(Criteria.where("XuLyVPHCMapping.HinhThucXuLyChinh.MaMuc").ne("03")));

        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("XuLyVPHCMapping.MaDinhDanh").as("XuLyVPHC"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(XuLyVPHC)").as("XuLyVPHC"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("XuLyVPHC") ? obj.getLong("XuLyVPHC") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue26(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is("04")));

        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("DeXuatKienNghi.IDKienNghi").as("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(DeXuatKienNghi)").as("DeXuatKienNghi"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("DeXuatKienNghi") ? obj.getLong("DeXuatKienNghi") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue27(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").is(LoaiThongBaoKetLuan.Loai.KetLuanThanhKiemTraChuyenNganh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is("04")));

        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("DeXuatKienNghi.DoiTuongXuLy.MaDinhDanh").as("DoiTuongXuLy"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(DoiTuongXuLy)").as("DoiTuongXuLy"));
        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        Map<String, Long> resultMap = new HashMap<>();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    resultMap.put(obj.getString(Constant._ID), obj.has("DoiTuongXuLy") ? obj.getLong("DoiTuongXuLy") : 0L);
                }
            }
        }
        return resultMap;
    }
}
