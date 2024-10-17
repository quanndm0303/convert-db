package com.fds.flex.core.cadmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.context.model.User;
import com.fds.flex.core.cadmgt.entity.C_Model.PhanTichKetQuaKNTC;
import com.fds.flex.core.cadmgt.entity.C_Model.TinhTrangXuLyDonThu;
import com.fds.flex.core.cadmgt.entity.T_Model.VuViecDonThu;
import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import com.fds.flex.core.cadmgt.service.StatisticService;
import com.fds.flex.core.cadmgt.util.CADMgtUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Long deXuatLanhBaoBoGiaoKiemTraThuLy(Integer namThongKe_Int) {
        //Đang đề xuất lãnh đạo Bộ giao kiểm tra điều kiện thụ lý:  C_TinhTrangXuLyDonThu = 07 và KiemTraThuLy = NULL
        List<AggregationOperation> countOperationList = new ArrayList<>();
        if (Validator.isNotNull(namThongKe_Int)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe_Int, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe_Int + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            countOperationList.add(aggregationOperation);
        }


        Criteria c = Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.XemXetThuLyDon.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new com.fds.flex.context.model.Service.TaiNguyenHeThong());

            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanDaGiaiQuyet.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanThamQuyen.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }
            if (!listPhanQuyen.isEmpty())
                countOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        CountOperation countOperation = Aggregation.count().as("total");
        countOperationList.add(countOperation);
        Aggregation countAgg = Aggregation.newAggregation(countOperationList);

        AggregationResults<String> countAggregationResults = mongoTemplate.aggregate(countAgg, VuViecDonThu.class,
                String.class);

        long totalDL = 0L;
        try {
            totalDL = new JSONObject(countAggregationResults.getUniqueMappedResult()).getLong("total");
        } catch (Exception ignored) {

        }
        return totalDL;
    }

    @Override
    public Long dangChoKiemTraDieuKien(Integer namThongKe_Int) {
        //Đang chờ để kiểm tra điều kiện: KiemTraThuLy có C_TrangThaiHoatDongThanhTra = Chuẩn bị chương trình
        List<AggregationOperation> countOperationList = new ArrayList<>();
        if (Validator.isNotNull(namThongKe_Int)) {
            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.set(namThongKe_Int, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.set(namThongKe_Int + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            countOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("KiemTraThuLy.LoaiHoatDongThanhTra.MaMuc").is("01");
        AggregationOperation aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new com.fds.flex.context.model.Service.TaiNguyenHeThong());

            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();


            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanDaGiaiQuyet.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanThamQuyen.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }
            if (!listPhanQuyen.isEmpty())
                countOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        CountOperation countOperation = Aggregation.count().as("total");
        countOperationList.add(countOperation);
        Aggregation countAgg = Aggregation.newAggregation(countOperationList);

        AggregationResults<String> countAggregationResults = mongoTemplate.aggregate(countAgg, VuViecDonThu.class,
                String.class);

        long totalDL = 0L;
        try {
            totalDL = new JSONObject(countAggregationResults.getUniqueMappedResult()).getLong("total");
        } catch (Exception ignored) {

        }
        return totalDL;
    }

    @Override
    public Long dangKiemTraDieuKienThuLy(Integer namThongKe_Int) {
        //Đang kiểm tra điều kiện thụ lý:  KiemTraThuLy có C_TrangThaiHoatDongThanhTra = 02, 04 (Đang thực hiện, Báo cáo kết quả)
        List<AggregationOperation> countOperationList = new ArrayList<>();
        if (Validator.isNotNull(namThongKe_Int)) {
            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.set(namThongKe_Int, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.set(namThongKe_Int + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            countOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("KiemTraThuLy.LoaiHoatDongThanhTra.MaMuc").in(Arrays.asList("02", "04"));
        AggregationOperation aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new com.fds.flex.context.model.Service.TaiNguyenHeThong());

            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();


            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanDaGiaiQuyet.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanThamQuyen.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }
            if (!listPhanQuyen.isEmpty())
                countOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        CountOperation countOperation = Aggregation.count().as("total");
        countOperationList.add(countOperation);
        Aggregation countAgg = Aggregation.newAggregation(countOperationList);

        AggregationResults<String> countAggregationResults = mongoTemplate.aggregate(countAgg, VuViecDonThu.class,
                String.class);

        long totalDL = 0L;
        try {
            totalDL = new JSONObject(countAggregationResults.getUniqueMappedResult()).getLong("total");
        } catch (Exception ignored) {

        }
        return totalDL;
    }

    @Override
    public Long toCaoSaiKhongXacDinhDuocHanhViViPham(Integer namThongKe_Int) {
        //Tố cáo sai, không xác định được hành vi vi phạm --->  T_XuLyDonThu.TinhTrangXuLyDonThu = 09 (tình trạng xử lý của đơn thư là không đủ điều kiện thụ lý) và T_VuViecDonThu.PhanTichKetQuaKNTC = 06 (kết quả phân tích vụ việc là tố cáo sai)
        List<AggregationOperation> countOperationList = new ArrayList<>();
        LookupOperation lookupOperation = LookupOperation.newLookup().from(DBConstant.T_VU_VIEC_DON_THU)
                .localField("VuViecDonThu.MaDinhDanh").foreignField("MaDinhDanh").as("VuViecDonThuMapping");
        countOperationList.add(lookupOperation);
        if (Validator.isNotNull(namThongKe_Int)) {
            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.set(namThongKe_Int, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.set(namThongKe_Int + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            countOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        c = Criteria.where("VuViecDonThuMapping.PhanTichKetQuaKNTC.MaMuc").is(PhanTichKetQuaKNTC.Loai.ToCaoSai.getMaMuc());
        aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new com.fds.flex.context.model.Service.TaiNguyenHeThong());

            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }
            if (!listPhanQuyen.isEmpty())
                countOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        CountOperation countOperation = Aggregation.count().as("total");
        countOperationList.add(countOperation);
        Aggregation countAgg = Aggregation.newAggregation(countOperationList);

        AggregationResults<String> countAggregationResults = mongoTemplate.aggregate(countAgg, XuLyDonThu.class,
                String.class);

        long totalDL = 0L;
        try {
            totalDL = new JSONObject(countAggregationResults.getUniqueMappedResult()).getLong("total");
        } catch (Exception ignored) {

        }
        return totalDL;
    }

    @Override
    public Long toCaoNacDanh(Integer namThongKe_Int) {
        //Tố cáo nặc danh ---> T_XuLyDonThu.TinhTrangXuLyDonThu = 09 (tình trạng xử lý của đơn thư là không đủ điều kiện thụ lý) và T_XuLyDonThu.TenNguoiDaiDien = NULL
        List<AggregationOperation> countOperationList = new ArrayList<>();
        if (Validator.isNotNull(namThongKe_Int)) {
            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.set(namThongKe_Int, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.set(namThongKe_Int + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            countOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        c = Criteria.where("TenNguoiDaiDien").exists(false);
        aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new com.fds.flex.context.model.Service.TaiNguyenHeThong());

            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();


            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }
            if (!listPhanQuyen.isEmpty())
                countOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        CountOperation countOperation = Aggregation.count().as("total");
        countOperationList.add(countOperation);
        Aggregation countAgg = Aggregation.newAggregation(countOperationList);

        AggregationResults<String> countAggregationResults = mongoTemplate.aggregate(countAgg, XuLyDonThu.class,
                String.class);

        long totalDL = 0L;
        try {
            totalDL = new JSONObject(countAggregationResults.getUniqueMappedResult()).getLong("total");
        } catch (Exception ignored) {

        }
        return totalDL;
    }

    @Override
    public Long khongXacDinhDuocNguoiVietDonToCao(Integer namThongKe_Int) {
        //Không xác định được người viết đơn tố cáo ---> Bằng Tổng những T_XuLyDonThu có C_TinhTrangXuLyDonThu=09 trừ đi số  đơn thư "Tố cáo sai, không xác định được hành vi vi phạm" và "Tố cáo nặc danh"
        List<AggregationOperation> countOperationList = new ArrayList<>();
        if (Validator.isNotNull(namThongKe_Int)) {
            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.set(namThongKe_Int, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.set(namThongKe_Int + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            countOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(TinhTrangXuLyDonThu.Loai.KhongDuDieuKienThuLy.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        aggregationOperation = Aggregation.match(c);
        countOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new com.fds.flex.context.model.Service.TaiNguyenHeThong());

            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();


            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }
            if (!listPhanQuyen.isEmpty())
                countOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        CountOperation countOperation = Aggregation.count().as("total");
        countOperationList.add(countOperation);
        Aggregation countAgg = Aggregation.newAggregation(countOperationList);

        AggregationResults<String> countAggregationResults = mongoTemplate.aggregate(countAgg, XuLyDonThu.class,
                String.class);

        long totalDL = 0L;
        try {
            totalDL = new JSONObject(Objects.requireNonNull(countAggregationResults.getUniqueMappedResult())).getLong("total");
        } catch (Exception ignored) {

        }
        return totalDL;
    }
}
