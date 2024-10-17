package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.DateTimeUtils;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.context.model.Service.TaiNguyenHeThong;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.Query_Model.XuLyVPHCQueryModel;
import com.fds.flex.core.inspectionmgt.entity.T_Model.XuLyVPHC;
import com.fds.flex.core.inspectionmgt.repository.XuLyVPHCRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.XuLyVPHCService;
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
public class XuLyVPHCServiceImpl implements XuLyVPHCService {
    @Autowired
    private XuLyVPHCRepository xuLyVPHCRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Override
    @Cacheable(value = "XuLyVPHC", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<XuLyVPHC> findById(String id) {
        log.info(LogConstant.findById, XuLyVPHC.class.getSimpleName(), id);
        return xuLyVPHCRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "XuLyVPHC", allEntries = true)
    public void deleteXuLyVPHC(XuLyVPHC xuLyVPHC) {
        log.info(LogConstant.deleteById, XuLyVPHC.class.getSimpleName(), xuLyVPHC.getPrimKey());
        xuLyVPHCRepository.delete(xuLyVPHC);
    }

    @Override
    @CacheEvict(value = "XuLyVPHC", allEntries = true)
    public XuLyVPHC updateXuLyVPHC(XuLyVPHC xuLyVPHC) {
        log.info(LogConstant.updateById, XuLyVPHC.class.getSimpleName(), xuLyVPHC.getPrimKey());
        return xuLyVPHCRepository.save(xuLyVPHC);
    }

    @Override
    public List<XuLyVPHC> findBySoBienBanVPHCAndTrangThaiDuLieu(String soBienBanVPHC) {
        return xuLyVPHCRepository.findBySoBienBanVPHCAndTrangThaiDuLieu(soBienBanVPHC,
                TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
    }

    @Override
    public List<XuLyVPHC> findBySoQuyetDinhAndTrangThaiDuLieu(String soQuyetDinh) {
        return xuLyVPHCRepository.findBySoQuyetDinhAndTrangThaiDuLieu(soQuyetDinh,
                TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
    }

    @Override
    @CacheEvict(value = "XuLyVPHC", allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, XuLyVPHC> update(Map<String, XuLyVPHC> map) {
        log.info(LogConstant.updateByMap, XuLyVPHC.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                xuLyVPHCRepository.delete(v);
            } else {
                xuLyVPHCRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<XuLyVPHC> filter(XuLyVPHCQueryModel xuLyVPHCQueryModel, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, XuLyVPHC.class.getSimpleName());

        Query query = new Query().with(pageable);

        if (!InspectionUtil.checkSuperAdmin()
                && !InspectionUtil.checkAdmin()
                && UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().containsKey(DBConstant.T_XU_LY_VPHC)) {
            List<String> lst = UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().getOrDefault(DBConstant.T_XU_LY_VPHC, new ArrayList<>());
            if (!lst.isEmpty()) {
                query.fields().exclude(lst.toArray(new String[0]));
            }
        }
        query.fields().exclude("MaPhienBan", "NguonThamChieu", "NhatKiSuaDoi",
                "LienKetURL", "MaDinhDanhThayThe"
                , "ChuSoHuu");

        List<Criteria> criteria = new ArrayList<>();
        if (Validator.isNotNull(xuLyVPHCQueryModel.getKeyword())) {

            String keyword = xuLyVPHCQueryModel.getKeyword();

            List<Criteria> subCriterias = new ArrayList<>();
            Criteria c = Criteria.where("SoQuyetDinh")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("SoBienBanVPHC")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("DoiTuongVPHC.TenGoi")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("DoiTuongVPHC.GiayToChungNhan.SoGiay")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("DoiTuongVPHC.LoaiDoiTuongCNTC.TenMuc")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("CoQuanQuanLy.TenGoi")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("CoQuanQuyetDinh.TenGoi")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("DonViChuTri.TenGoi")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("CanBoTheoDoi.HoVaTen")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("NoiDungVPHC.HanhViVPHC.TenMuc")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("HinhThucXuLyChinh.TenMuc")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("NoiDungLyDoXuLy")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("TruongHopPhatTien.TongSoTienPhat")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("TruongHopPhatTien.NoiThuTienPhat")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("SoBienBanVPHC")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("TrangThaiXuLyVPHC.TenMuc")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);

            criteria.add(new Criteria().orOperator(subCriterias));
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getCoQuanQuanLy())) {
            Criteria c = Criteria.where("CoQuanQuanLy.MaDinhDanh").is(xuLyVPHCQueryModel.getCoQuanQuanLy());
            criteria.add(c);
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayLapBienBan())) {

            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayLapBienBan(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca_to.set(Calendar.MILLISECOND, 999);
            Criteria c = Criteria.where("NgayLapBienBan").gte(ca_from.getTimeInMillis()).lte(ca_to.getTimeInMillis());
            criteria.add(c);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayLapBienBan_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayLapBienBan_TuNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            Criteria c = Criteria.where("NgayLapBienBan").gte(ca.getTimeInMillis());
            criteria.add(c);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayLapBienBan_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayLapBienBan_DenNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            Criteria c = Criteria.where("NgayLapBienBan").lte(ca.getTimeInMillis());
            criteria.add(c);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayQuyetDinh())) {
            List<Criteria> subCriterias = new ArrayList<>();

            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayQuyetDinh(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            Criteria c = Criteria.where("NgayQuyetDinh").gte(ca.getTimeInMillis());
            subCriterias.add(c);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            c = Criteria.where("NgayQuyetDinh").lte(ca.getTimeInMillis());
            subCriterias.add(c);
            criteria.add(new Criteria().andOperator(subCriterias));
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayQuyetDinh_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayQuyetDinh_TuNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            Criteria c = Criteria.where("NgayQuyetDinh").gte(ca.getTimeInMillis());
            criteria.add(c);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayQuyetDinh_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayQuyetDinh_DenNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);

            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            Criteria c = Criteria.where("NgayQuyetDinh").lte(ca.getTimeInMillis());
            criteria.add(c);
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getSoBienBanVPHC())) {
            Criteria c = Criteria.where("SoBienBanVPHC").is(xuLyVPHCQueryModel.getSoBienBanVPHC());
            criteria.add(c);
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getCoQuanQuyetDinh())) {
            Criteria c = Criteria.where("CoQuanQuyetDinh.MaDinhDanh").is(xuLyVPHCQueryModel.getCoQuanQuyetDinh());
            criteria.add(c);
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayKetThuc())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayKetThuc(),
                    DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca_to.set(Calendar.MILLISECOND, 999);
            Criteria c = Criteria.where("NgayKetThuc").gte(ca_from.getTimeInMillis()).lte(ca_to.getTimeInMillis());
            criteria.add(c);
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayKetThuc_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayKetThuc_TuNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            Criteria c = Criteria.where("NgayKetThuc").gte(ca.getTimeInMillis());
            criteria.add(c);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getNgayKetThuc_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyVPHCQueryModel.getNgayKetThuc_DenNgay(),
                    DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            Criteria c = Criteria.where("NgayKetThuc").lte(ca.getTimeInMillis());
            criteria.add(c);
        }

        if (Validator.isNotNull(xuLyVPHCQueryModel.getTrangThaiXuLyVPHC_MaMuc())
                && xuLyVPHCQueryModel.getTrangThaiXuLyVPHC_MaMuc().length > 0) {
            Criteria c = Criteria.where("TrangThaiXuLyVPHC.MaMuc")
                    .in(Arrays.asList(xuLyVPHCQueryModel.getTrangThaiXuLyVPHC_MaMuc()));
            criteria.add(c);
        }
        if (Validator.isNotNull(xuLyVPHCQueryModel.getTrangThaiDuLieu_MaMuc())
                && xuLyVPHCQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc")
                    .in(Arrays.asList(xuLyVPHCQueryModel.getTrangThaiDuLieu_MaMuc()));
            criteria.add(c);
        }
        //Phân Quyền
        if (!InspectionUtil.checkSuperAdmin()) {
            User user = UserContextHolder.getContext().getUser();
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_VPHC, new TaiNguyenHeThong());

            List<Criteria> subCriterias = new ArrayList<>();
            if (taiNguyenHeThong.isHanChePhanVung())
                subCriterias.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi())
                subCriterias.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
            if (!subCriterias.isEmpty())
                criteria.add(new Criteria().orOperator(subCriterias));
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }
        log.debug(LogConstant.finishGenerateQueryFilter, XuLyVPHC.class.getSimpleName(), new JSONObject(query));

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, XuLyVPHC.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, XuLyVPHC.class));
    }

    @Override
    public List<XuLyVPHC> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return xuLyVPHCRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<XuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc) {
        return xuLyVPHCRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<XuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return xuLyVPHCRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Map<String, Long> thongKeTrangThaiXuLyVPHC(String[] trangThaiXulyVPHC) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();


        if (Validator.isNotNull(trangThaiXulyVPHC) && trangThaiXulyVPHC.length > 0) {
            Criteria c = Criteria.where("TrangThaiXuLyVPHC.MaMuc").in(Arrays.asList(trangThaiXulyVPHC));
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        //Phân Quyền
        if (!InspectionUtil.checkSuperAdmin()) {
            User user = UserContextHolder.getContext().getUser();
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_VPHC, new TaiNguyenHeThong());

            List<Criteria> subCriterias = new ArrayList<>();
            if (taiNguyenHeThong.isHanChePhanVung())
                subCriterias.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi())
                subCriterias.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
            if (!subCriterias.isEmpty()) {
                AggregationOperation aggregationOperation = Aggregation.match(new Criteria().orOperator(subCriterias));
                searchOperationList.add(aggregationOperation);
            }
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        searchOperationList.add(Aggregation.group("TrangThaiXuLyVPHC.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, "_id"));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyVPHC.class, Document.class);

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
}
