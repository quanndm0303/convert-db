package com.fds.flex.core.inspectionmgt.service.statistic.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.*;
import com.fds.flex.core.inspectionmgt.entity.T_Model.HoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.entity.T_Model.ThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.service.statistic.CongTacThanhTraHanhChinhStatisticService;
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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@Slf4j
public class CongTacThanhTraHanhChinhStatisticServiceImpl implements CongTacThanhTraHanhChinhStatisticService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to) {


        List<AggregationOperation> searchOperationList = new ArrayList<>();

        //4.	Số cuộc thanh tra thực hiện trong kỳ - cột Triển khai từ kỳ trước chuyển sang (2)
        List<Criteria> criteria = new ArrayList<>();
        //a.	Thống kê từ Dữ liệu đoàn thanh tra, kiểm tra T_HoatDongThanhTra, Phân vùng dữ liệu áp dụng đầu vào là Cơ quan quản lý đoàn thanh tra CoQuanQuanLy
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        //b.	Lọc Loại hoạt động công việc được giao
        criteria.add(Criteria.where("LoaiHoatDongThanhTra.MaMuc").in(Arrays.asList(LoaiHoatDongThanhTra.Loai.ThanhTraHanhChinh.getMaMuc(), LoaiHoatDongThanhTra.Loai.KiemTraTrachNhiemThuTruong.getMaMuc())));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        //c.	Lọc Thời hạn tiến hành hoạt động được ghi theo quyết định (Đáp ứng 1 trong 3 điều kiện c hoặc d hoặc e)
        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        //-	ThoiHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        //-	Và NgayKetThuc null hoặc NgayKetThuc > denngay
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").exists(false), Criteria.where("NgayKetThuc").gt(ca_to)));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        //d.	hoặc Ngày được gia hạn hoàn thành theo quyết định, kết hợp Ngày hoàn thành kết thúc hoạt động, kết hợp với Trạng thái đoàn thanh tra/ đoàn kiểm tra  (Đáp ứng 1 trong 3 điều kiện c hoặc d hoặc e)
        andCriteria = new ArrayList<>();
        //-	ThoiHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        //-	GiaHanThucHien trong khoảng (tungay và denngay) hoặc GiaHanThucHien > denngay
        andCriteria.add(new Criteria().orOperator(Criteria.where("GiaHanThucHien").gte(ca_from).lte(ca_to), Criteria.where("GiaHanThucHien").gt(ca_to)));
        //-	Và NgayKetThuc trong khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện 04: Báo cáo kết quả 05: Ban hành kết luận, thông báo 06: Đình chỉ thực hiện (hủy)
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(
                Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
                )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        //e.	hoặc Ngày được gia hạn hoàn thành theo quyết định , kết hợp với Trạng thái đoàn thanh tra/ đoàn kiểm tra  (Đáp ứng 1 trong 3 điều kiện c hoặc d hoặc e)
        andCriteria = new ArrayList<>();
        //-	ThoiHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        //-	GiaHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("GiaHanThucHien").lt(ca_from));
        //-	Và NgayKetThuc null hoặc NgayKetThuc > denngay
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").exists(false), Criteria.where("NgayKetThuc").gt(ca_to)));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
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
        //5.	Số cuộc thanh tra thực hiện trong kỳ - cột Triển khai trong kỳ (3)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        //b.	Lọc Loại hoạt động công việc được giao
        criteria.add(Criteria.where("LoaiHoatDongThanhTra.MaMuc").in(Arrays.asList(LoaiHoatDongThanhTra.Loai.ThanhTraHanhChinh.getMaMuc(), LoaiHoatDongThanhTra.Loai.KiemTraTrachNhiemThuTruong.getMaMuc())));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        //c.	Lọc Thời hạn tiến hành hoạt động được ghi theo quyết định
        List<Criteria> andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("ThoiHanThucHien").gte(ca_from).lte(ca_to));
        //-	TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện 04: Báo cáo kết quả 05: Ban hành kết luận, thông báo 06: Đình chỉ thực hiện (hủy)
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(
                Arrays.asList(
                        TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
                )));
        criteria.add(new Criteria().andOperator(andCriteria));

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
        //6.	Số cuộc thanh tra thực hiện trong kỳ - cột Theo Kế hoạch (4)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        //b.	Lọc Loại hoạt động công việc được giao
        criteria.add(Criteria.where("LoaiHoatDongThanhTra.MaMuc").in(Arrays.asList(LoaiHoatDongThanhTra.Loai.ThanhTraHanhChinh.getMaMuc(), LoaiHoatDongThanhTra.Loai.KiemTraTrachNhiemThuTruong.getMaMuc())));
        //c.	Lọc Loại hình kế hoạch thanh tra
        criteria.add(Criteria.where("LoaiCheDoThanhTra.MaMuc").in(Arrays.asList("01", "02", "03")));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        //d.	Lọc Thời hạn tiến hành hoạt động được ghi theo quyết định (Đáp ứng 1 trong 4 điều kiện d,e,f,g)
        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        //-	ThoiHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("ThoiHanThucHien").gte(ca_from).lte(ca_to));
        //-	Và NgayKetThuc null hoặc NgayKetThuc > denngay
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").exists(false), Criteria.where("NgayKetThuc").gt(ca_to)));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        //e.	hoặc Ngày được gia hạn hoàn thành theo quyết định, kết hợp Ngày hoàn thành kết thúc hoạt động, kết hợp với Trạng thái đoàn thanh tra/ đoàn kiểm tra
        andCriteria = new ArrayList<>();
        //-	ThoiHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        //-	GiaHanThucHien trong khoảng (tungay và denngay) hoặc GiaHanThucHien > denngay
        andCriteria.add(new Criteria().orOperator(Criteria.where("GiaHanThucHien").gte(ca_from).lte(ca_to), Criteria.where("GiaHanThucHien").gt(ca_to)));
        //-	Và NgayKetThuc trong khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện 04: Báo cáo kết quả 05: Ban hành kết luận, thông báo 06: Đình chỉ thực hiện (hủy)
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        //f.	hoặc Ngày được gia hạn hoàn thành theo quyết định , kết hợp với Trạng thái đoàn thanh tra/ đoàn kiểm tra  (Đáp ứng 1 trong 4 điều kiện d,e,f,g)
        andCriteria = new ArrayList<>();
        //-	ThoiHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        //-	GiaHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("GiaHanThucHien").lt(ca_from));
        //-	Và NgayKetThuc null hoặc NgayKetThuc > denngay
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").exists(false), Criteria.where("NgayKetThuc").gt(ca_to)));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        //g.	hoặc Thời hạn tiến hành hoạt động được ghi theo quyết định (Đáp ứng 1 trong 4 điều kiện d,e,f,g)
        //-	ThoiHanThucHien trong khoảng (tungay và denngay) và

        andCriteria = new ArrayList<>();
        andCriteria.add(Criteria.where("ThoiHanThucHien").gte(ca_from).lte(ca_to));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện 04: Báo cáo kết quả 05: Ban hành kết luận, thông báo 06: Đình chỉ thực hiện (hủy)
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
    public Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //7.	Số cuộc thanh tra thực hiện trong kỳ - cột Đột xuất (5)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));

        //b.	Lọc Loại hoạt động công việc được giao
        criteria.add(Criteria.where("LoaiHoatDongThanhTra.MaMuc").in(Arrays.asList(LoaiHoatDongThanhTra.Loai.ThanhTraHanhChinh.getMaMuc(), LoaiHoatDongThanhTra.Loai.KiemTraTrachNhiemThuTruong.getMaMuc())));
        //c.	Lọc Loại hình kế hoạch thanh tra
        criteria.add(Criteria.where("LoaiCheDoThanhTra.MaMuc").is(LoaiCheDoThanhTra.Loai.DotXuat.getMaMuc()));
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        //d.	Lọc Thời hạn tiến hành hoạt động được ghi theo quyết định (Đáp ứng 1 trong 4 điều kiện d,e,f,g)
        List<Criteria> criteriaLv1 = new ArrayList<>();
        List<Criteria> andCriteria = new ArrayList<>();
        //-	ThoiHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        //-	Và NgayKetThuc null hoặc NgayKetThuc > denngay
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").exists(false), Criteria.where("NgayKetThuc").gt(ca_to)));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        //e.	hoặc Ngày được gia hạn hoàn thành theo quyết định, kết hợp Ngày hoàn thành kết thúc hoạt động, kết hợp với Trạng thái đoàn thanh tra/ đoàn kiểm tra  (Đáp ứng 1 trong 3 điều kiện c hoặc d hoặc e)
        andCriteria = new ArrayList<>();
        //-	ThoiHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        //-	GiaHanThucHien trong khoảng (tungay và denngay) hoặc GiaHanThucHien > denngay
        andCriteria.add(new Criteria().orOperator(Criteria.where("GiaHanThucHien").gte(ca_from).lte(ca_to), Criteria.where("GiaHanThucHien").gt(ca_to)));
        //-	Và NgayKetThuc trong khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("NgayKetThuc").gte(ca_from).lte(ca_to));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện 04: Báo cáo kết quả 05: Ban hành kết luận, thông báo 06: Đình chỉ thực hiện (hủy)
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(
                Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.ChoBaoCaoKetQua.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HoanThanh.getMaMuc(),
                        TrangThaiHoatDongThanhTra.TrangThai.HuyThucHien.getMaMuc()
                )));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        //f.	hoặc Ngày được gia hạn hoàn thành theo quyết định , kết hợp với Trạng thái đoàn thanh tra/ đoàn kiểm tra  (Đáp ứng 1 trong 4 điều kiện d,e,f,g)
        andCriteria = new ArrayList<>();
        //-	ThoiHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("ThoiHanThucHien").lt(ca_from));
        //-	GiaHanThucHien trước khoảng (tungay và denngay)
        andCriteria.add(Criteria.where("GiaHanThucHien").lt(ca_from));
        //-	Và NgayKetThuc null hoặc NgayKetThuc > denngay
        andCriteria.add(new Criteria().orOperator(Criteria.where("NgayKetThuc").exists(false), Criteria.where("NgayKetThuc").gt(ca_to)));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện
        andCriteria.add(Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(Arrays.asList(TrangThaiHoatDongThanhTra.TrangThai.LapKeHoach.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.DangThucHien.getMaMuc(), TrangThaiHoatDongThanhTra.TrangThai.TamDinhChi.getMaMuc())));
        criteriaLv1.add(new Criteria().andOperator(andCriteria));
        //g.	hoặc Thời hạn tiến hành hoạt động được ghi theo quyết định (Đáp ứng 1 trong 4 điều kiện d,e,f,g)
        andCriteria = new ArrayList<>();
        //-	ThoiHanThucHien trong khoảng (tungay và denngay) và
        andCriteria.add(Criteria.where("ThoiHanThucHien").gte(ca_from).lte(ca_to));
        //-	Và TrangThaiHoatDongThanhTra = 01: Chuẩn bị chương trình 02: Đang thực hiện 03: Tạm dừng thực hiện 04: Báo cáo kết quả 05: Ban hành kết luận, thông báo 06: Đình chỉ thực hiện (hủy)
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
    public Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //8.	Số cuộc thanh tra đã ban hành kết luận(6)
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        //b.	Lọc theo Loại theo dõi kết luận thanh kiểm tra
        criteria.add(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc())));
        //c.	Lọc Ngày ban hành kết luận : -	NgayVanBan trong khoảng (tungay và denngay)
        criteria.add(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to));
        //d.	Đếm số lượng lặp lại theo Đối tượng kết luận DoiTuongKetLuan = T_HoatDongThanhTra, trả về CuocThanhTraBHKL ???
        criteria.add(Criteria.where("DoiTuongKetLuan.@type").is(DBConstant.T_HOAT_DONG_THANH_TRA));

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
    public Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //9.	Số đơn vị được thanh tra theo Kết luận(7)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi.DoiTuongXuLy"));
        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
        criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy));
        //b.	Lọc theo Loại theo dõi kết luận thanh kiểm tra
        criteria.add(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(), LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc())));
        //c.	Lọc Ngày ban hành kết luận : -	NgayVanBan trong khoảng (tungay và denngay)
        criteria.add(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to));
        //d.	Đếm số lượng lặp lại theo Đối tượng bị xử lý theo kiến nghị DoiTuongXuLy, ko trùng lặp, trả về DonViDuocThanhTra ???
        //TODO DEM
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
    public Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //10.	Kiến nghị xử lý Thu hồi về NSNN cột Tiền (Tr.đ) (10)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(), LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()))));
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

    public Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //11.	Kiến nghị xử lý Xử lý khác về kinh tế cột Tiền (Tr.đ) (12)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(), LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()))));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
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
    public Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //13.	Kiến nghị xử lý Thu hồi về NSNN cột Đất (m2) (11)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(), LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()))));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is(LoaiDeXuatKienNghi.Loai.ThuHoiVeNSNN.getMaMuc())));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("DeXuatKienNghi.DienTichThuHoi").as("DienTichThuHoi"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("DienTichThuHoi") ? obj.getLong("DienTichThuHoi") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to) {
        //14.	Kiến nghị xử lý Xử lý khác về kinh tế cột Đất (m2) (13)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(), LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()))));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is(LoaiDeXuatKienNghi.Loai.XuLyViPhamVeKinhTe.getMaMuc())));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").sum("DeXuatKienNghi.DienTichThuHoi").as("DienTichThuHoi"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("DienTichThuHoi") ? obj.getLong("DienTichThuHoi") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to) {

        //16.	Kiến nghị xử lý Hành chính cột (14) Tổ chức

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(Criteria.where("TrangThaiDulieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()), Criteria.where("ThamChieu.MaMuc").is("04")));
        List<LoaiDoiTuongCNTC> resultLoaiDTCNTC = mongoTemplate.find(query, LoaiDoiTuongCNTC.class);
        List<String> maMuc = resultLoaiDTCNTC.stream().map(LoaiDoiTuongCNTC::getMaMuc).collect(Collectors.toList());
        maMuc.addAll(Arrays.asList("04", "05", "06"));

        //14.	Kiến nghị xử lý Xử lý khác về kinh tế cột Đất (m2) (13)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi.DoiTuongXuLy"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(), LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()))));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.DoiTuongXuLy.LoaiDoiTuongCNTC.MaMuc").in(maMuc)));
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
    public Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to) {


        //17.	Kiến nghị xử lý Hành chính cột (15) Cá nhân
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi.DoiTuongXuLy"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(), LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()))));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is(LoaiDeXuatKienNghi.Loai.XuLyHanhChinh.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.DoiTuongXuLy.LoaiDoiTuongCNTC.MaMuc").in(Arrays.asList("01", "02", "03"))));
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
    public Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to) {


        //18.	Kiến nghị xử lý Chuyển cơ quan điều tra số vụ việc cột (16)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()))));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is(LoaiDeXuatKienNghi.Loai.ChuyenCoQuanDieuTra.getMaMuc())));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("DeXuatKienNghi.IDKienNghi").as("IDKienNghi"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(IDKienNghi)").as("IDKienNghi"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("IDKienNghi") ? obj.getLong("IDKienNghi") : 0L);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to) {


        //18.	Kiến nghị xử lý Chuyển cơ quan điều tra số vụ việc cột (16)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi.DoiTuongXuLy"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()))));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is(LoaiDeXuatKienNghi.Loai.ChuyenCoQuanDieuTra.getMaMuc())));
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
    public Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to) {


        //20.	Hoàn thiện cơ chế chính sách cột (18) (số văn bản)
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));
        searchOperationList.add(Aggregation.match(Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())));
        searchOperationList.add(Aggregation.match(Criteria.where("CoQuanQuanLy.MaDinhDanh").in(coQuanQuanLy)));
        searchOperationList.add(Aggregation.match(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(Arrays.asList(LoaiThongBaoKetLuan.Loai.KetLuanThanhTraHanhChinh.getMaMuc(),
                LoaiThongBaoKetLuan.Loai.KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN.getMaMuc()))));
        searchOperationList.add(Aggregation.match(Criteria.where("NgayVanBan").gte(ca_from).lte(ca_to)));
        searchOperationList.add(Aggregation.match(Criteria.where("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").is(LoaiDeXuatKienNghi.Loai.HoanThienCoCheChinhSach.getMaMuc())));
        searchOperationList.add(Aggregation.group("CoQuanQuanLy.MaDinhDanh").addToSet("DeXuatKienNghi.IDKienNghi").as("IDKienNghi"));
        searchOperationList.add(Aggregation.project()
                .andExpression("_id").as("_id")
                .andExpression("size(IDKienNghi)").as("IDKienNghi"));
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
                    resultMap.put(obj.getString(Constant._ID), obj.has("IDKienNghi") ? obj.getLong("IDKienNghi") : 0L);
                }
            }
        }
        return resultMap;
    }
}
