package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.context.model.Service.TaiNguyenHeThong;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.C_Model.ChucDanhDoan;
import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiCheDoThanhTra;
import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiHoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiHoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.entity.Query_Model.HoatDongThanhTraQueryModel;
import com.fds.flex.core.inspectionmgt.entity.T_Model.HoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.publisher.PublisherEvent;
import com.fds.flex.core.inspectionmgt.repository.HoatDongThanhTraRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.HoatDongThanhTraService;
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
import org.springframework.context.annotation.Primary;
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

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@Slf4j
@Primary
public class HoatDongThanhTraServiceImpl implements HoatDongThanhTraService {
    @Autowired
    private HoatDongThanhTraRepository hoatDongThanhTraRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Cacheable(value = "HoatDongThanhTra", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<HoatDongThanhTra> findById(String id) {
        log.info(LogConstant.findById, HoatDongThanhTra.class.getSimpleName(), id);
        return hoatDongThanhTraRepository.findById(id);
    }

    @Override
    public Map<String, Long> thongKeTrangThaiHoatDongThanhTra(String[] loaiHoatDongThanhTra_MaMuc, Integer namThongKe, String loaiCheDoThanhTra_MaMuc, String nhiemVuCongViec_NamKeHoach) {

        List<AggregationOperation> searchOperationList = new ArrayList<>();

        if (Validator.isNotNull(loaiHoatDongThanhTra_MaMuc) && loaiHoatDongThanhTra_MaMuc.length > 0) {
            List<String> loaiHoatDongThanhTraList = Arrays.asList(loaiHoatDongThanhTra_MaMuc);
            Criteria c = Criteria.where("LoaiHoatDongThanhTra.MaMuc")
                    .in(loaiHoatDongThanhTraList);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
            List<String> trangThaiHoatDongThanhTraList = new ArrayList<>();
            for (String loaiHoatDongThanhTra : loaiHoatDongThanhTraList) {
                if (InspectionUtil.gioiHanTrangThaiHoatDongThanhTra.containsKey(loaiHoatDongThanhTra)) {
                    trangThaiHoatDongThanhTraList.addAll(InspectionUtil.gioiHanTrangThaiHoatDongThanhTra.getOrDefault(loaiHoatDongThanhTra, new ArrayList<>()));
                }
            }
            trangThaiHoatDongThanhTraList = trangThaiHoatDongThanhTraList.stream().distinct().collect(Collectors.toList());
            if (!trangThaiHoatDongThanhTraList.isEmpty()) {
                c = Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(trangThaiHoatDongThanhTraList);
                aggregationOperation = Aggregation.match(c);
                searchOperationList.add(aggregationOperation);
            }
        }

        if (Validator.isNotNull(loaiCheDoThanhTra_MaMuc)) {
            Criteria c = Criteria.where("LoaiCheDoThanhTra.MaMuc").is(loaiCheDoThanhTra_MaMuc);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        if (Validator.isNotNull(nhiemVuCongViec_NamKeHoach)) {
            Criteria c = Criteria.where("NhiemVuCongViec.NamKeHoach").is(Integer.parseInt(nhiemVuCongViec_NamKeHoach));
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }
        if (Validator.isNotNull(namThongKe)) {
            List<Criteria> criteriaList = new ArrayList<>();

            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);

            Calendar ca2 = Calendar.getInstance();
            ca2.setTimeZone(TimeZoneUtil.UTC);
            ca2.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca2.set(Calendar.MILLISECOND, 0);

            criteriaList.add(Criteria.where("ThoiHanThucHien").gte(ca.getTimeInMillis()).lt(ca2.getTimeInMillis()));
            criteriaList.add(Criteria.where("GiaHanThucHien").gte(ca.getTimeInMillis()).lt(ca2.getTimeInMillis()));

            AggregationOperation aggregationOperation = Aggregation.match(new Criteria().orOperator(criteriaList));
            searchOperationList.add(aggregationOperation);
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_HOAT_DONG_THANH_TRA)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_HOAT_DONG_THANH_TRA, new TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
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


        searchOperationList.add(Aggregation.group("TrangThaiHoatDongThanhTra.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, HoatDongThanhTra.class,
                Document.class);

        Map<String, Long> hashMap = new HashMap<>();
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        if (!results.isEmpty()) {
            if (Validator.isNotNull(loaiCheDoThanhTra_MaMuc)
                    && loaiCheDoThanhTra_MaMuc.equals(LoaiCheDoThanhTra.Loai.DotXuat.getMaMuc())
                    && Validator.isNotNull(loaiHoatDongThanhTra_MaMuc)
                    && (Arrays.asList(loaiHoatDongThanhTra_MaMuc).contains(LoaiHoatDongThanhTra.Loai.ThanhTraHanhChinh.getMaMuc())
                    || (Arrays.asList(loaiHoatDongThanhTra_MaMuc).contains(LoaiHoatDongThanhTra.Loai.ThanhTraChuyenNganh.getMaMuc()))
                    || (Arrays.asList(loaiHoatDongThanhTra_MaMuc).contains(LoaiHoatDongThanhTra.Loai.KiemTraTrachNhiemThuTruong.getMaMuc()))
                    || (Arrays.asList(loaiHoatDongThanhTra_MaMuc).contains(LoaiHoatDongThanhTra.Loai.KiemTraPhongChongThamNhung.getMaMuc()))
            )) {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject obj = results.optJSONObject(i);
                    if (Validator.isNotNull(obj) && obj.has(Constant._ID) && !obj.getString(Constant._ID).equals(TrangThaiHoatDongThanhTra.TrangThai.ChuaTrienKhai.getMaMuc())) {
                        hashMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                    }
                }
            } else {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject obj = results.optJSONObject(i);
                    if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                        hashMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                    }
                }
            }
        }
        return hashMap;
    }

    @Override
    public Map<String, Long> thongKeLoaiHoatDongThanhTra(String[] loaiHoatDongThanhTra_MaMuc, Integer namThongKe) {

        List<AggregationOperation> searchOperationList = new ArrayList<>();

        if (Validator.isNotNull(loaiHoatDongThanhTra_MaMuc) && loaiHoatDongThanhTra_MaMuc.length > 0) {
            Criteria c = Criteria.where("LoaiHoatDongThanhTra.MaMuc").in(Arrays.asList(loaiHoatDongThanhTra_MaMuc));
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        if (Validator.isNotNull(namThongKe)) {
            List<Criteria> criteriaList = new ArrayList<>();
            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            criteriaList.add(Criteria.where("ThoiHanThucHien").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis()));
            criteriaList.add(Criteria.where("GiaHanThucHien").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis()));

            AggregationOperation aggregationOperation = Aggregation.match(new Criteria().orOperator(criteriaList));
            searchOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_HOAT_DONG_THANH_TRA)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_HOAT_DONG_THANH_TRA, new TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (Validator.isNotNull(taiNguyenHeThong)) {
                if (taiNguyenHeThong.isHanChePhanVung())
                    listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
                if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                    listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
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
            }
            if (!listPhanQuyen.isEmpty())
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(Aggregation.group("LoaiHoatDongThanhTra.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, HoatDongThanhTra.class,
                Document.class);

        long total = 0L;

        Map<String, Long> hashMap = new HashMap<>();

        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID)) {
                    hashMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                    total += obj.optLong(Constant._SO_LUONG, 0L);
                }
            }
        }
        hashMap.put(Constant._ALL, total);
        return hashMap;
    }

    @Override
    @CacheEvict(value = "HoatDongThanhTra", allEntries = true)
    public void deleteHoatDongThanhTra(HoatDongThanhTra hoatDongThanhTra) {
        log.info(LogConstant.deleteById, HoatDongThanhTra.class.getSimpleName(), hoatDongThanhTra.getPrimKey());
        hoatDongThanhTraRepository.delete(hoatDongThanhTra);
    }

    @Override
    @CacheEvict(value = "HoatDongThanhTra", allEntries = true)
    public HoatDongThanhTra updateHoatDongThanhTra(HoatDongThanhTra hoatDongThanhTra) {
        log.info(LogConstant.updateById, HoatDongThanhTra.class.getSimpleName(), hoatDongThanhTra.getPrimKey());
        hoatDongThanhTra = hoatDongThanhTraRepository.save(hoatDongThanhTra);

        if (hoatDongThanhTra.getTrangThaiDuLieu().getMaMuc().equals(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())) {
            PublisherEvent hoatDongThanhTraEvent = new PublisherEvent(DBConstant.T_HOAT_DONG_THANH_TRA, hoatDongThanhTra);
            applicationEventPublisher.publishEvent(hoatDongThanhTraEvent);
            hoatDongThanhTraEvent = new PublisherEvent("updateHoatDongThanhTra", hoatDongThanhTra);
            applicationEventPublisher.publishEvent(hoatDongThanhTraEvent);
        }
        return hoatDongThanhTra;
    }

    @Override
    @CacheEvict(value = "HoatDongThanhTra", allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, HoatDongThanhTra> update(Map<String, HoatDongThanhTra> map) {
        log.info(LogConstant.updateByMap, HoatDongThanhTra.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                hoatDongThanhTraRepository.delete(v);
            } else {
                hoatDongThanhTraRepository.save(v);
            }
        });
        if (map.containsKey(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())) {
            PublisherEvent hoatDongThanhTraEvent = new PublisherEvent(DBConstant.T_HOAT_DONG_THANH_TRA, map.get(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
            applicationEventPublisher.publishEvent(hoatDongThanhTraEvent);
            hoatDongThanhTraEvent = new PublisherEvent("updateHoatDongThanhTra", map.get(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()));
            applicationEventPublisher.publishEvent(hoatDongThanhTraEvent);
        }
        return map;
    }

    @Override
    public Page<HoatDongThanhTra> filter(HoatDongThanhTraQueryModel hoatDongThanhTraQueryModel, Pageable pageable) {

        Query query = new Query().with(pageable);

        if (!InspectionUtil.checkSuperAdmin()
                && !InspectionUtil.checkAdmin()
                && UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().containsKey(DBConstant.T_HOAT_DONG_THANH_TRA)) {
            List<String> lst = UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().getOrDefault(DBConstant.T_HOAT_DONG_THANH_TRA, new ArrayList<>());
            if (!lst.isEmpty()) {
                query.fields().exclude(lst.toArray(new String[0]));
            }
        }
        query.fields().exclude("MaPhienBan", "NguonThamChieu", "NhatKiSuaDoi",
                "LienKetURL", "MaDinhDanhThayThe"
                , "ChuSoHuu");

        List<Criteria> criteria = new ArrayList<>();


        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getKeyword())) {

            String keyword = hoatDongThanhTraQueryModel.getKeyword();

            List<Criteria> subCriterias = new ArrayList<>();
            Criteria c = Criteria.where("SoQuyetDinh")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("TenGoi").regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("TenHoatDong").regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);

            criteria.add(new Criteria().orOperator(subCriterias));
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getLoaiCheDoThanhTra_MaMuc())) {
            Criteria c = Criteria.where("LoaiCheDoThanhTra.MaMuc").is(hoatDongThanhTraQueryModel.getLoaiCheDoThanhTra_MaMuc());
            criteria.add(c);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getNhiemVuCongViec_NamKeHoach())) {
            Criteria c = Criteria.where("NhiemVuCongViec.NamKeHoach").is(Integer.parseInt(hoatDongThanhTraQueryModel.getNhiemVuCongViec_NamKeHoach()));
            criteria.add(c);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getLoaiHoatDongThanhTra_MaMuc())
                && hoatDongThanhTraQueryModel.getLoaiHoatDongThanhTra_MaMuc().length > 0) {
            List<String> loaiHoatDongThanhTraList = Arrays.asList(hoatDongThanhTraQueryModel.getLoaiHoatDongThanhTra_MaMuc());
            Criteria c = Criteria.where("LoaiHoatDongThanhTra.MaMuc")
                    .in(loaiHoatDongThanhTraList);
            criteria.add(c);
            List<String> trangThaiHoatDongThanhTraList = new ArrayList<>();
            for (String loaiHoatDongThanhTra : loaiHoatDongThanhTraList) {
                if (InspectionUtil.gioiHanTrangThaiHoatDongThanhTra.containsKey(loaiHoatDongThanhTra)) {
                    trangThaiHoatDongThanhTraList.addAll(InspectionUtil.gioiHanTrangThaiHoatDongThanhTra.getOrDefault(loaiHoatDongThanhTra, new ArrayList<>()));
                }
            }
            trangThaiHoatDongThanhTraList = trangThaiHoatDongThanhTraList.stream().distinct().collect(Collectors.toList());
            if (!trangThaiHoatDongThanhTraList.isEmpty()) {
                c = Criteria.where("TrangThaiHoatDongThanhTra.MaMuc").in(trangThaiHoatDongThanhTraList);
                criteria.add(c);
            }
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getHoSoNghiepVu_SoDangKy())) {
            Criteria c = Criteria.where("HoSoNghiepVu.SoDangKy").is(hoatDongThanhTraQueryModel.getHoSoNghiepVu_SoDangKy());
            criteria.add(c);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getHoSoNghiepVu_TrangThaiHoSoNghiepVu_MaMuc())) {
            Criteria c = Criteria.where("HoSoNghiepVu.TrangThaiHoSoNghiepVu.MaMuc").is(hoatDongThanhTraQueryModel.getHoSoNghiepVu_TrangThaiHoSoNghiepVu_MaMuc());
            criteria.add(c);
        }
        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getTrangThaiHoatDongThanhTra_MaMuc())) {
            Criteria c = Criteria.where("TrangThaiHoatDongThanhTra.MaMuc")
                    .is(hoatDongThanhTraQueryModel.getTrangThaiHoatDongThanhTra_MaMuc());
            criteria.add(c);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getCoQuanBanHanh())) {
            Criteria c = Criteria.where("CoQuanBanHanh.MaDinhDanh").is(hoatDongThanhTraQueryModel.getCoQuanBanHanh());
            criteria.add(c);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getNhiemVuCongViec_NamKeHoach())) {
            Criteria c = Criteria.where("NhiemVuCongViec.NamKeHoach").is(Integer.parseInt(hoatDongThanhTraQueryModel.getNhiemVuCongViec_NamKeHoach()));
            criteria.add(c);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getTruongDoan())) {
            Criteria c = Criteria.where("ThanhVienDoan").elemMatch(Criteria.where("CanBo.HoVaTen")
                    .regex(InspectionUtil.toLikeKeyword(hoatDongThanhTraQueryModel.getTruongDoan()),
                            Constant.INSENSITIVE).and("ChucDanhDoan.MaMuc")
                    .is(ChucDanhDoan.ChucDanh.TruongDoan.getMaMuc()));
            criteria.add(c);
        }

        if (Validator.isNotNull(hoatDongThanhTraQueryModel.getTrangThaiDuLieu_MaMuc())
                && hoatDongThanhTraQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc")
                    .in(Arrays.asList(hoatDongThanhTraQueryModel.getTrangThaiDuLieu_MaMuc()));
            criteria.add(c);
        }

        //Phân quyền
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_HOAT_DONG_THANH_TRA)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_HOAT_DONG_THANH_TRA, new TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (Validator.isNotNull(taiNguyenHeThong)) {
                if (taiNguyenHeThong.isHanChePhanVung())
                    listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
                if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                    listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
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
            }
            if (!listPhanQuyen.isEmpty()) {
                criteria.add(new Criteria().orOperator(listPhanQuyen));
            }
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, HoatDongThanhTra.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, HoatDongThanhTra.class));
    }

    @Override
    public List<HoatDongThanhTra> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return hoatDongThanhTraRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<HoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh,
                                                                         String trangThaiDuLieu_MaMuc) {
        return hoatDongThanhTraRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<HoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh,
                                                                     String[] trangThaiDuLieu_MaMuc) {
        return hoatDongThanhTraRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<HoatDongThanhTra> findByHSNVMaDinhDanhAndTrangThaiDuLieu(String hsnv_MaDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return hoatDongThanhTraRepository.findByHSNVMaDinhDanhAndTrangThaiDuLieu(hsnv_MaDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<HoatDongThanhTra> findByNVCVMaDinhDanhAndTrangThaiDuLieu(String nvcv_MaDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return hoatDongThanhTraRepository.findByNVCVMaDinhDanhAndTrangThaiDuLieu(nvcv_MaDinhDanh, trangThaiDuLieu_MaMuc);
    }
}
