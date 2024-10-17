package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.DateTimeUtils;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.context.model.Service.TaiNguyenHeThong;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiTheoDoi;
import com.fds.flex.core.inspectionmgt.entity.Query_Model.ThongBaoKetLuanQueryModel;
import com.fds.flex.core.inspectionmgt.entity.T_Model.ThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.publisher.PublisherEvent;
import com.fds.flex.core.inspectionmgt.repository.ThongBaoKetLuanRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.ThongBaoKetLuanService;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@Slf4j
public class ThongBaoKetLuanServiceImpl implements ThongBaoKetLuanService {
    @Autowired
    private ThongBaoKetLuanRepository thongBaoKetLuanRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Override
    @Cacheable(value = "ThongBaoKetLuan", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<ThongBaoKetLuan> findById(String id) {
        log.info(LogConstant.findById, ThongBaoKetLuan.class.getSimpleName(), id);
        return thongBaoKetLuanRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "ThongBaoKetLuan", allEntries = true)
    public void deleteThongBaoKetLuan(ThongBaoKetLuan thongBaoKetLuan) {
        log.info(LogConstant.deleteById, ThongBaoKetLuan.class.getSimpleName(), thongBaoKetLuan.getPrimKey());
        thongBaoKetLuanRepository.delete(thongBaoKetLuan);
    }

    @Override
    public Map<String, Long> thongKeTrangThaiKetLuanTKT(String[] loaiThongBaoKetLuan_MaMuc, Integer namThongKe, String[] trangThaiTheoDoiArr) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        if (Validator.isNotNull(loaiThongBaoKetLuan_MaMuc) && loaiThongBaoKetLuan_MaMuc.length > 0) {
            List<String> listThongBaoKetLuan = Arrays.asList(loaiThongBaoKetLuan_MaMuc);
            Criteria c = Criteria.where("LoaiThongBaoKetLuan.MaMuc")
                    .in(listThongBaoKetLuan);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        if (Validator.isNotNull(trangThaiTheoDoiArr) && trangThaiTheoDoiArr.length > 0) {
            List<String> listTrangThaiTheoDoi = Arrays.asList(trangThaiTheoDoiArr);
            Criteria c = Criteria.where("TrangThaiTheoDoi.MaMuc")
                    .in(listTrangThaiTheoDoi);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c = Criteria.where("NgayTheoDoi").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }


        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân Quyền
        if (!InspectionUtil.checkSuperAdmin()) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_THONG_BAO_KET_LUAN, new TaiNguyenHeThong());

            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanBanHanh.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViChuTri.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }

            if (!listPhanQuyen.isEmpty()) {
                aggregationOperation = Aggregation.match(new Criteria().orOperator(listPhanQuyen));
                searchOperationList.add(aggregationOperation);
            }
        }

        searchOperationList.add(Aggregation.group("TrangThaiTheoDoi.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);

        Map<String, Long> hashMap = new HashMap<>();

        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    hashMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return hashMap;
    }

    @Override
    public Map<String, Long> thongKeLoaiDeXuatKienNghi(String loaiThongBaoKetLuan_MaMuc, Integer namThongKe) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        searchOperationList.add(Aggregation.unwind("DeXuatKienNghi"));

        if (Validator.isNotNull(loaiThongBaoKetLuan_MaMuc)) {
            Criteria c = Criteria.where("LoaiThongBaoKetLuan.MaMuc")
                    .in(Arrays.asList(StringUtil.split(loaiThongBaoKetLuan_MaMuc)));
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);
            Criteria c = Criteria.where("NgayTheoDoi").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân Quyền
        if (!InspectionUtil.checkSuperAdmin()) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_THONG_BAO_KET_LUAN, new TaiNguyenHeThong());

            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanBanHanh.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViChuTri.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }


            if (!listPhanQuyen.isEmpty()) {
                aggregationOperation = Aggregation.match(new Criteria().orOperator(listPhanQuyen));
                searchOperationList.add(aggregationOperation);
            }
        }

        searchOperationList.add(Aggregation.group("DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, ThongBaoKetLuan.class,
                Document.class);

        Map<String, Long> hashMap = new HashMap<>();
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    hashMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return hashMap;
    }

    @Override
    @CacheEvict(value = "ThongBaoKetLuan", allEntries = true)
    public ThongBaoKetLuan updateThongBaoKetLuan(ThongBaoKetLuan thongBaoKetLuan) {
        log.info(LogConstant.updateById, ThongBaoKetLuan.class.getSimpleName(), thongBaoKetLuan.getPrimKey());
        thongBaoKetLuan = thongBaoKetLuanRepository.save(thongBaoKetLuan);

        if (thongBaoKetLuan.getTrangThaiDuLieu().getMaMuc().equals(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())) {
            PublisherEvent thongBaoKetLuanEvent = new PublisherEvent("updateThongBaoKetLuan", thongBaoKetLuan);
            applicationEventPublisher.publishEvent(thongBaoKetLuanEvent);
        }
        return thongBaoKetLuan;
    }

    @Override
    @CacheEvict(value = "ThongBaoKetLuan", allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, ThongBaoKetLuan> update(Map<String, ThongBaoKetLuan> map) {
        log.info(LogConstant.updateByMap, ThongBaoKetLuan.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                thongBaoKetLuanRepository.delete(v);
            } else {
                thongBaoKetLuanRepository.save(v);
            }
        });
        if (map.containsKey(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())) {
            PublisherEvent thongBaoKetLuanEvent = new PublisherEvent("updateThongBaoKetLuan", map.get(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
            applicationEventPublisher.publishEvent(thongBaoKetLuanEvent);
        }

        return map;
    }

    @Override

    public Page<ThongBaoKetLuan> filter(ThongBaoKetLuanQueryModel thongBaoKetLuanQueryModel, Pageable pageable) {

        Query query = new Query().with(pageable);

        if (!InspectionUtil.checkSuperAdmin()
                && !InspectionUtil.checkAdmin()
                && UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().containsKey(DBConstant.T_THONG_BAO_KET_LUAN)) {
            List<String> lst = UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().getOrDefault(DBConstant.T_THONG_BAO_KET_LUAN, new ArrayList<>());
            if (!lst.isEmpty()) {
                query.fields().exclude(lst.toArray(new String[0]));
            }
        }

        query.fields().exclude("MaPhienBan", "NguonThamChieu", "NhatKiSuaDoi",
                "LienKetURL", "MaDinhDanhThayThe",
                "ChuSoHuu");
        List<Criteria> criteria = new ArrayList<>();

        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getKeyword())) {

            String keyword = thongBaoKetLuanQueryModel.getKeyword();

            List<Criteria> subCriterias = new ArrayList<>();
            Criteria c = Criteria.where("SoHieuVanBan")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("TrichYeuVanBan").regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);

            criteria.add(new Criteria().orOperator(subCriterias));
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getDoiTuongKetLuan_MaDinhDanh())) {
            criteria.add(Criteria.where("DoiTuongKetLuan.MaDinhDanh").is(thongBaoKetLuanQueryModel.getDoiTuongKetLuan_MaDinhDanh()));
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getDonViChuTri_MaDinhDanh())) {
            criteria.add(Criteria.where("DonViChuTri.MaDinhDanh")
                    .is(thongBaoKetLuanQueryModel.getDonViChuTri_MaDinhDanh()));
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getCoQuanBanHanh_MaDinhDanh())) {
            criteria.add(Criteria.where("CoQuanBanHanh.MaDinhDanh")
                    .is(thongBaoKetLuanQueryModel.getCoQuanBanHanh_MaDinhDanh()));
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getNgayVanBan_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(thongBaoKetLuanQueryModel.getNgayVanBan_TuNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            criteria.add(Criteria.where("NgayVanBan").gte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getNgayVanBan_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(thongBaoKetLuanQueryModel.getNgayVanBan_DenNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            criteria.add(Criteria.where("NgayVanBan").lte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getNgayVanBan())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(thongBaoKetLuanQueryModel.getNgayVanBan(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            Calendar ca2 = Calendar.getInstance();
            ca2.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            ca2.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca2.set(Calendar.MILLISECOND, 999);
            criteria.add(Criteria.where("NgayVanBan").gte(ca.getTimeInMillis()).lte(ca2.getTimeInMillis()));
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getCanBoTheoDoi_MaDinhDanh())) {
            criteria.add(Criteria.where("CanBoTheoDoi.MaDinhDanh")
                    .is(thongBaoKetLuanQueryModel.getCanBoTheoDoi_MaDinhDanh()));
        }
        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getLoaiThongBaoKetLuan_MaMuc())
                && thongBaoKetLuanQueryModel.getLoaiThongBaoKetLuan_MaMuc().length > 0) {
            List<String> loaiThongBaoKetLuanList = Arrays.asList(thongBaoKetLuanQueryModel.getLoaiThongBaoKetLuan_MaMuc());
            criteria.add(Criteria.where("LoaiThongBaoKetLuan.MaMuc").in(loaiThongBaoKetLuanList));
        }

        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getTrangThaiTheoDoi_MaMuc())
                && thongBaoKetLuanQueryModel.getTrangThaiTheoDoi_MaMuc().length > 0) {
            criteria.add(Criteria.where("TrangThaiTheoDoi.MaMuc")
                    .in(Arrays.asList(thongBaoKetLuanQueryModel.getTrangThaiTheoDoi_MaMuc())));
        }

        if (Validator.isNotNull(thongBaoKetLuanQueryModel.getTrangThaiDuLieu_MaMuc())
                && thongBaoKetLuanQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            criteria.add(Criteria.where("TrangThaiDuLieu.MaMuc")
                    .in(Arrays.asList(thongBaoKetLuanQueryModel.getTrangThaiDuLieu_MaMuc())));
        }
        //Phân Quyền
        if (!InspectionUtil.checkSuperAdmin()) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_THONG_BAO_KET_LUAN, new TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("ChiaSeTaiKhoan.DanhTinhDienTu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("ChiaSeVaiTro.VaiTroSuDung.MaMuc").in(user.getQuyenSuDung().getMaVaiTroLst()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanBanHanh.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViChuTri.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }
            if (!listPhanQuyen.isEmpty()) {
                criteria.add(new Criteria().orOperator(listPhanQuyen));
            }
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, ThongBaoKetLuan.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, ThongBaoKetLuan.class));

    }

    @Override
    public List<ThongBaoKetLuan> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return thongBaoKetLuanRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<ThongBaoKetLuan> findByDTKT_TTDL(String doanThanhKiemTra, String trangThaiDuLieu_MaMuc) {
        return thongBaoKetLuanRepository.findByDTKT_TTDL(doanThanhKiemTra, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<ThongBaoKetLuan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh,
                                                                        String trangThaiDuLieu_MaMuc) {
        return thongBaoKetLuanRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<ThongBaoKetLuan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return thongBaoKetLuanRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }
}
