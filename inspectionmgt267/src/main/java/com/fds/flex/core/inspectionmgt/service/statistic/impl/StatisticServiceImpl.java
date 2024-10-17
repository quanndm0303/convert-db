package com.fds.flex.core.inspectionmgt.service.statistic.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.DateTimeUtils;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.entity.C_Model.ChucDanhDoan;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.core.inspectionmgt.entity.Data_Model.HoatDongThanhTraUnwindLichCongTacDoan;
import com.fds.flex.core.inspectionmgt.entity.S_Model.ThanhVienDoan;
import com.fds.flex.core.inspectionmgt.entity.Statistic_Model.HDTT_LichCongTacDoan_Statistic;
import com.fds.flex.core.inspectionmgt.service.statistic.StatisticService;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<HDTT_LichCongTacDoan_Statistic> thongKeLichCongTacDoan(String ngayCongTac) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        searchOperationList.add(Aggregation.unwind("LichCongTacDoan"));

        if (Validator.isNotNull(ngayCongTac)) {
            LocalDate date = DateTimeUtils.stringToLocalDate(ngayCongTac,
                    DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);

            Criteria c = Criteria.where("LichCongTacDoan.NgayBatDau").lte(ca.getTimeInMillis());
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);

            c = Criteria.where("LichCongTacDoan.NgayKetThuc").gte(ca.getTimeInMillis());
            aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        //Phân quyền
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_HOAT_DONG_THANH_TRA)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_HOAT_DONG_THANH_TRA, new com.fds.flex.context.model.Service.TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi()) {
                listPhanQuyen.add(Criteria.where("ChiaSeVaiTro.VaiTroSuDung.MaMuc").in(user.getQuyenSuDung().getMaVaiTroLst()));
                listPhanQuyen.add(Criteria.where("ChiaSeTaiKhoan.DanhTinhDienTu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("ChuSoHuu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("ThanhVienDoan.CanBo.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanBanHanh.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViChuTri.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }

            if (!listPhanQuyen.isEmpty())
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        Aggregation searchAgg = Aggregation.newAggregation(searchOperationList);

        AggregationResults<HoatDongThanhTraUnwindLichCongTacDoan> searchAggregationResults = mongoTemplate.aggregate(searchAgg,
                DBConstant.T_HOAT_DONG_THANH_TRA, HoatDongThanhTraUnwindLichCongTacDoan.class);

        List<HDTT_LichCongTacDoan_Statistic> result = new ArrayList<>();

        for (HoatDongThanhTraUnwindLichCongTacDoan hoatDongThanhTraUnwindLichCongTacDoan : searchAggregationResults.getMappedResults()) {
            Map<String, String> id_Ten = new HashMap<>();
            HDTT_LichCongTacDoan_Statistic lichCongTacDoan = new HDTT_LichCongTacDoan_Statistic();
            lichCongTacDoan.setTenHoatDong(hoatDongThanhTraUnwindLichCongTacDoan.getTenHoatDong());

            String truongDoan = StringPool.BLANK;
            for (ThanhVienDoan thanhVienDoan : hoatDongThanhTraUnwindLichCongTacDoan.getThanhVienDoan()) {
                id_Ten.put(thanhVienDoan.getIdThanhVien(), thanhVienDoan.getCanBo().getHoVaTen());
                if (thanhVienDoan.getChucDanhDoan().getMaMuc().equals(ChucDanhDoan.ChucDanh.TruongDoan.getMaMuc())) {
                    truongDoan += Validator.isBlank(truongDoan) ? thanhVienDoan.getCanBo().getHoVaTen() : StringPool.COMMA + StringPool.SPACE + thanhVienDoan.getCanBo().getHoVaTen();
                }
            }
            lichCongTacDoan.setTruongDoan(truongDoan);

            String canBoThamGia = StringPool.BLANK;
            for (String idThanhVien : hoatDongThanhTraUnwindLichCongTacDoan.getLichCongTacDoan().getIdThanhVien()) {
                if (id_Ten.containsKey(idThanhVien)) {
                    canBoThamGia += Validator.isBlank(canBoThamGia) ? id_Ten.get(idThanhVien) : StringPool.COMMA + StringPool.SPACE + id_Ten.get(idThanhVien);
                }
            }
            lichCongTacDoan.setCanBoThamGia(canBoThamGia);
            lichCongTacDoan.setNoiDungCongTac(hoatDongThanhTraUnwindLichCongTacDoan.getLichCongTacDoan().getNoiDungCongTac());
            result.add(lichCongTacDoan);
        }
        return result;
    }

    @Override
    public List<String> thongKeLichCongTacDoanTheoThang(String namThongKe, String tuNgay, String denNgay) {

        int namThongKe_Int = LocalDate.now().getYear();
        if (Validator.isNotNull(namThongKe)) {
            namThongKe_Int = Integer.parseInt(namThongKe);
        }

        List<AggregationOperation> searchOperationList = new ArrayList<>();
        searchOperationList.add(Aggregation.unwind("LichCongTacDoan"));

        List<Criteria> criteriaList = new ArrayList<>();
        //Truoc nam
        Calendar ca = Calendar.getInstance();
        ca.setTimeZone(TimeZoneUtil.UTC);
        ca.set(namThongKe_Int, Calendar.JANUARY, 1, 0, 0, 0);
        ca.set(Calendar.MILLISECOND, 0);
        Criteria c1 = Criteria.where("LichCongTacDoan.NgayBatDau").lte(ca.getTimeInMillis());
        Criteria c2 = Criteria.where("LichCongTacDoan.NgayKetThuc").gte(ca.getTimeInMillis());
        criteriaList.add(new Criteria().andOperator(c1, c2));
        //Trong nam
        ca.set(namThongKe_Int, Calendar.JANUARY, 1, 0, 0, 0);
        ca.set(Calendar.MILLISECOND, 0);
        c1 = Criteria.where("LichCongTacDoan.NgayBatDau").gte(ca.getTimeInMillis());
        ca.set(namThongKe_Int + 1, Calendar.JANUARY, 1, 0, 0, 0);
        ca.set(Calendar.MILLISECOND, 0);
        c2 = Criteria.where("LichCongTacDoan.NgayKetThuc").lt(ca.getTimeInMillis());
        criteriaList.add(new Criteria().andOperator(c1, c2));
        //Nam sau
        ca.set(namThongKe_Int + 1, Calendar.JANUARY, 1, 0, 0, 0);
        ca.set(Calendar.MILLISECOND, 0);
        c1 = Criteria.where("LichCongTacDoan.NgayBatDau").lt(ca.getTimeInMillis());
        ca.set(namThongKe_Int + 1, Calendar.JANUARY, 1, 0, 0, 0);
        ca.set(Calendar.MILLISECOND, 0);
        c2 = Criteria.where("LichCongTacDoan.NgayKetThuc").gte(ca.getTimeInMillis());
        criteriaList.add(new Criteria().andOperator(c1, c2));

        AggregationOperation aggregationOperation = Aggregation.match(new Criteria().orOperator(criteriaList));
        searchOperationList.add(aggregationOperation);

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_HOAT_DONG_THANH_TRA)) {
            com.fds.flex.context.model.Service.TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_HOAT_DONG_THANH_TRA, new com.fds.flex.context.model.Service.TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi()) {
                listPhanQuyen.add(Criteria.where("ChiaSeVaiTro.VaiTroSuDung.MaMuc").in(user.getQuyenSuDung().getMaVaiTroLst()));
                listPhanQuyen.add(Criteria.where("ChiaSeTaiKhoan.DanhTinhDienTu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("ChuSoHuu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("ThanhVienDoan.CanBo.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanBanHanh.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViChuTri.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }

            if (!listPhanQuyen.isEmpty())
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        Aggregation searchAgg = Aggregation.newAggregation(searchOperationList);

        AggregationResults<HoatDongThanhTraUnwindLichCongTacDoan> searchAggregationResults = mongoTemplate.aggregate(searchAgg,
                DBConstant.T_HOAT_DONG_THANH_TRA, HoatDongThanhTraUnwindLichCongTacDoan.class);

        List<String> dateHaveLich = new ArrayList<>();
        Date tuNgay_Date = DateTimeUtils.stringToDate(tuNgay, DateTimeUtils._VN_DATE_FORMAT);
        Date denNgay_Date = DateTimeUtils.stringToDate(denNgay, DateTimeUtils._VN_DATE_FORMAT);
        for (HoatDongThanhTraUnwindLichCongTacDoan lichCongTacDoan : searchAggregationResults.getMappedResults()) {
            if (Validator.isNotNull(lichCongTacDoan.getLichCongTacDoan().getNgayBatDau()) && Validator.isNotNull(lichCongTacDoan.getLichCongTacDoan().getNgayKetThuc())) {
                List<String> rangeDate = DateTimeUtils.generateDateRange(new Date(lichCongTacDoan.getLichCongTacDoan().getNgayBatDau()), new Date(lichCongTacDoan.getLichCongTacDoan().getNgayKetThuc()), DateTimeUtils._VN_DATE_FORMAT);
                for (String dateString : rangeDate) {
                    if (!dateHaveLich.contains(dateString)) {
                        Date hienTai = DateTimeUtils.stringToDate(dateString, DateTimeUtils._VN_DATE_FORMAT);
                        if (hienTai.compareTo(tuNgay_Date) >= 0 && hienTai.compareTo(denNgay_Date) <= 0) {
                            dateHaveLich.add(dateString);
                        }
                    }
                }
            }

        }

        return dateHaveLich;

    }
}
