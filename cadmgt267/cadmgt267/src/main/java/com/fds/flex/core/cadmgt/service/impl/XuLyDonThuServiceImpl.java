package com.fds.flex.core.cadmgt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.DateTimeUtils;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.context.model.Service.TaiNguyenHeThong;
import com.fds.flex.context.model.User;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.constant.LogConstant;
import com.fds.flex.core.cadmgt.entity.Data_Model.KetQuaXuLyDonModel;
import com.fds.flex.core.cadmgt.entity.Extra_Model.XuLyDonThu_VuViecDonThu;
import com.fds.flex.core.cadmgt.entity.Query_Model.XuLyDonThuQueryModel;
import com.fds.flex.core.cadmgt.entity.T_Model.VuViecDonThu;
import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import com.fds.flex.core.cadmgt.repository.XuLyDonThuRepository;
import com.fds.flex.core.cadmgt.service.CacheService;
import com.fds.flex.core.cadmgt.service.XuLyDonThuService;
import com.fds.flex.core.cadmgt.util.CADMgtUtil;
import com.fds.flex.core.cadmgt.util.CustomProjectAggregationOperation;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@Slf4j
public class XuLyDonThuServiceImpl implements XuLyDonThuService {
    @Autowired
    private XuLyDonThuRepository xuLyDonThuRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Cacheable(value = DBConstant.T_XU_LY_DON_THU, key = "#id", condition = "#result != null && #result.isPresent()")
    @Override
    public Optional<XuLyDonThu> findById(String id) {
        log.info(LogConstant.findById, XuLyDonThu.class.getSimpleName(), id);
        return xuLyDonThuRepository.findById(id);
    }

    @Override
    @CacheEvict(value = DBConstant.T_XU_LY_DON_THU, allEntries = true)
    public void deleteXuLyDonThu(XuLyDonThu xuLyDonThu) {
        log.info(LogConstant.deleteById, XuLyDonThu.class.getSimpleName(), xuLyDonThu.getPrimKey());
        xuLyDonThuRepository.delete(xuLyDonThu);
        cacheManager.getCacheNames().forEach(name -> Objects.requireNonNull(cacheManager.getCache(name)).clear());
    }

    @Override
    @CacheEvict(value = DBConstant.T_XU_LY_DON_THU, allEntries = true)
    public XuLyDonThu updateXuLyDonThu(XuLyDonThu xuLyDonThu) {
        log.info(LogConstant.updateById, XuLyDonThu.class.getSimpleName(), xuLyDonThu.getPrimKey());

        cacheManager.getCacheNames().forEach(name -> Objects.requireNonNull(cacheManager.getCache(name)).clear());
        return xuLyDonThu;
    }

    @Override
    @CacheEvict(value = DBConstant.T_XU_LY_DON_THU, allEntries = true)
    public Map<String, XuLyDonThu> update(Map<String, XuLyDonThu> map) {
        log.info(LogConstant.updateByMap, XuLyDonThu.class.getSimpleName(), map.toString());

        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                xuLyDonThuRepository.delete(v);
            } else {
                xuLyDonThuRepository.save(v);
            }
        });
        cacheManager.getCacheNames().forEach(name -> Objects.requireNonNull(cacheManager.getCache(name)).clear());

        return map;
    }

    @Override
    public Page<XuLyDonThu> filter(XuLyDonThuQueryModel xuLyDonThuQueryModel, Pageable pageable) {

        Query query = new Query().with(pageable);

        if (!CADMgtUtil.checkSuperAdmin() && !CADMgtUtil.checkAdmin() && UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            List<String> lst = UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().getOrDefault(DBConstant.T_XU_LY_DON_THU, new ArrayList<>());
            if (!lst.isEmpty()) {
                query.fields().exclude(lst.toArray(new String[0]));
            }
        }
        query.fields().exclude("MaPhienBan", "NguonThamChieu", "NhatKiSuaDoi",
                "LienKetURL", "MaDinhDanhThayThe"
                , "ChuSoHuu");

        List<Criteria> criteria = new ArrayList<>();
        if (Validator.isNotNull(xuLyDonThuQueryModel.getKeyword())) {
            String keyword = xuLyDonThuQueryModel.getKeyword();
            List<Criteria> subCriterias = new ArrayList<>();
            subCriterias.add(Criteria.where("TenNguoiDaiDien").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            subCriterias.add(Criteria.where("GiayToChungNhan.SoGiay").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            subCriterias.add(Criteria.where("VuViecDonThu.NoiDungVuViec").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            criteria.add(new Criteria().orOperator(subCriterias));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getTieuDeDonThu())) {
            criteria.add(Criteria.where("TieuDeDonThu").regex(toLikeKeyword(xuLyDonThuQueryModel.getTieuDeDonThu()), Constant.INSENSITIVE));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getNgayTiepNhan_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyDonThuQueryModel.getNgayTiepNhan_TuNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            criteria.add(Criteria.where("NgayTiepNhan").gte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getNgayTiepNhan_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyDonThuQueryModel.getNgayTiepNhan_DenNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            criteria.add(Criteria.where("NgayTiepNhan").lte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getHinhThucNhanDonThu_MaMuc())) {
            criteria.add(Criteria.where("HinhThucNhanDonThu.MaMuc").is(xuLyDonThuQueryModel.getHinhThucNhanDonThu_MaMuc()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getTiepCongDan_MaDinhDanh())) {
            criteria.add(Criteria.where("TiepCongDan.MaDinhDanh").is(xuLyDonThuQueryModel.getTiepCongDan_MaDinhDanh()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getVuViecDonThu_MaDinhDanh())) {
            criteria.add(Criteria.where("VuViecDonThu.MaDinhDanh").is(xuLyDonThuQueryModel.getVuViecDonThu_MaDinhDanh()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getTrangThaiGiaiQuyetDon_MaMuc())) {
            criteria.add(Criteria.where("TrangThaiGiaiQuyetDon.MaMuc").is(xuLyDonThuQueryModel.getTrangThaiGiaiQuyetDon_MaMuc()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getVuViecDonThu_NoiDungVuViec())) {
            criteria.add(Criteria.where("VuViecDonThu.NoiDungVuViec").regex(toLikeKeyword(xuLyDonThuQueryModel.getVuViecDonThu_NoiDungVuViec()), Constant.INSENSITIVE));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getLoaiDoiTuongDungTen_MaMuc())) {
            criteria.add(Criteria.where("LoaiDoiTuongDungTen.MaMuc").is(xuLyDonThuQueryModel.getLoaiDoiTuongDungTen_MaMuc()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getLoaiVuViecDonThu_MaMuc())) {
            criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(xuLyDonThuQueryModel.getLoaiVuViecDonThu_MaMuc()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getLinhVucChuyenNganh_MaMuc())) {
            criteria.add(Criteria.where("VuViecDonThu.LinhVucChuyenNganh.MaMuc").is(xuLyDonThuQueryModel.getLinhVucChuyenNganh_MaMuc()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getCoQuanQuanLy_MaDinhDanh())) {
            criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(xuLyDonThuQueryModel.getCoQuanQuanLy_MaDinhDanh()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getDonViTheoDoi_MaDinhDanh())) {
            criteria.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(xuLyDonThuQueryModel.getDonViTheoDoi_MaDinhDanh()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getCanBoXuLy_MaDinhDanh())) {
            criteria.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(xuLyDonThuQueryModel.getCanBoXuLy_MaDinhDanh()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getTinhTrangXuLyDonThu_MaMuc())
                && xuLyDonThuQueryModel.getTinhTrangXuLyDonThu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TinhTrangXuLyDonThu.MaMuc")
                    .in(Arrays.asList(xuLyDonThuQueryModel.getTinhTrangXuLyDonThu_MaMuc()));
            criteria.add(c);
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getTrangThaiDuLieu_MaMuc())
                && xuLyDonThuQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc")
                    .in(Arrays.asList(xuLyDonThuQueryModel.getTrangThaiDuLieu_MaMuc()));
            criteria.add(c);
        }
        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();
            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
                    listPhanQuyen.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("CanBoGiaiQuyet.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                } else if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CO_QUAN_DON_VI)
                        || user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_PHONG_BAN)) {
                    listPhanQuyen.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                    listPhanQuyen.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh()));
                }
            }
            if (!listPhanQuyen.isEmpty()) {
                criteria.add(new Criteria().orOperator(listPhanQuyen));
            }
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        List<XuLyDonThu> xuLyDonThuList = mongoTemplate.find(query, XuLyDonThu.class);
        if (xuLyDonThuList.isEmpty()) {
            return PageableExecutionUtils.getPage(xuLyDonThuList, pageable,
                    () -> cacheService.countByQuery(query.toString(), query, XuLyDonThu.class));
        }
        List<String> mdd_VVDT = xuLyDonThuList.stream().filter(e -> Validator.isNotNull(e.getVuViecDonThu()) && Validator.isNotNull(e.getVuViecDonThu().getMaDinhDanh())).map(XuLyDonThu::getVuViecDonThu).collect(Collectors.toList()).stream().map(XuLyDonThu_VuViecDonThu::getMaDinhDanh).collect(Collectors.toList());
        if (mdd_VVDT.isEmpty()) {
            return PageableExecutionUtils.getPage(xuLyDonThuList, pageable,
                    () -> cacheService.countByQuery(query.toString(), query, XuLyDonThu.class));
        }

        //Query VVDT
        Query queryVVDT = new Query();
        queryVVDT.addCriteria(new Criteria().andOperator(Criteria.where("TrangThaiDuLieu.MaMuc").in(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()),
                Criteria.where("MaDinhDanh").in(mdd_VVDT)));
        List<VuViecDonThu> vuViecDonThuList = mongoTemplate.find(queryVVDT, VuViecDonThu.class);
        if (vuViecDonThuList.isEmpty()) {
            return PageableExecutionUtils.getPage(xuLyDonThuList, pageable,
                    () -> cacheService.countByQuery(query.toString(), query, XuLyDonThu.class));
        }
        Map<String, VuViecDonThu> map_VVDT = vuViecDonThuList.stream().collect(Collectors.toMap(VuViecDonThu::getMaDinhDanh, item -> item));
        for (XuLyDonThu xuLyDonThu : xuLyDonThuList) {
            if (Validator.isNotNull(xuLyDonThu.getVuViecDonThu()) && Validator.isNotNull(xuLyDonThu.getVuViecDonThu().getMaDinhDanh()) && map_VVDT.containsKey(xuLyDonThu.getVuViecDonThu().getMaDinhDanh())) {
                VuViecDonThu vuViecDonThu = map_VVDT.getOrDefault(xuLyDonThu.getVuViecDonThu().getMaDinhDanh(), null);
                if (Validator.isNotNull(vuViecDonThu)) {
                    if (Validator.isNotNull(vuViecDonThu.getPhanTichKetQuaKNTC())) {
                        XuLyDonThu_VuViecDonThu.PhanTichKetQuaKNTC phanTichKetQuaKNTC = new XuLyDonThu_VuViecDonThu.PhanTichKetQuaKNTC();
                        phanTichKetQuaKNTC.setMaMuc(vuViecDonThu.getPhanTichKetQuaKNTC().getMaMuc());
                        phanTichKetQuaKNTC.setTenMuc(vuViecDonThu.getPhanTichKetQuaKNTC().getTenMuc());
                        xuLyDonThu.getVuViecDonThu().setPhanTichKetQuaKNTC(phanTichKetQuaKNTC);
                    }
                    if (Validator.isNotNull(vuViecDonThu.getLoaiPhucTapKeoDai())) {
                        XuLyDonThu_VuViecDonThu.LoaiPhucTapKeoDai loaiPhucTapKeoDai = new XuLyDonThu_VuViecDonThu.LoaiPhucTapKeoDai();
                        loaiPhucTapKeoDai.setMaMuc(vuViecDonThu.getLoaiPhucTapKeoDai().getMaMuc());
                        loaiPhucTapKeoDai.setTenMuc(vuViecDonThu.getLoaiPhucTapKeoDai().getTenMuc());
                        xuLyDonThu.getVuViecDonThu().setLoaiPhucTapKeoDai(loaiPhucTapKeoDai);
                    }
                    if (Validator.isNotNull(vuViecDonThu.getKhoiKienHanhChinh())) {
                        XuLyDonThu_VuViecDonThu.KhoiKienHanhChinh khoiKienHanhChinh = new XuLyDonThu_VuViecDonThu.KhoiKienHanhChinh();
                        khoiKienHanhChinh.setMaMuc(vuViecDonThu.getKhoiKienHanhChinh().getMaMuc());
                        khoiKienHanhChinh.setTenMuc(vuViecDonThu.getKhoiKienHanhChinh().getTenMuc());
                        xuLyDonThu.getVuViecDonThu().setKhoiKienHanhChinh(khoiKienHanhChinh);
                    }
                }
            }
        }
        return PageableExecutionUtils.getPage(xuLyDonThuList, pageable,
                () -> cacheService.countByQuery(query.toString(), query, XuLyDonThu.class));
    }

    @Override
    public List<XuLyDonThu> filterWithoutPageable(XuLyDonThuQueryModel xuLyDonThuQueryModel) {

        Query query = new Query();

        if (!CADMgtUtil.checkSuperAdmin() && !CADMgtUtil.checkAdmin() && UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            List<String> lst = UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().getOrDefault(DBConstant.T_XU_LY_DON_THU, new ArrayList<>());
            if (!lst.isEmpty()) {
                query.fields().exclude(lst.toArray(new String[0]));
            }
        }
        query.fields().exclude("MaPhienBan", "NguonThamChieu", "NhatKiSuaDoi",
                "LienKetURL", "MaDinhDanhThayThe"
                , "ChuSoHuu");

        List<Criteria> criteria = new ArrayList<>();
        if (Validator.isNotNull(xuLyDonThuQueryModel.getKeyword())) {
            String keyword = xuLyDonThuQueryModel.getKeyword();
            List<Criteria> subCriterias = new ArrayList<>();
            subCriterias.add(Criteria.where("TenNguoiDaiDien").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            subCriterias.add(Criteria.where("GiayToChungNhan.SoGiay").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            subCriterias.add(Criteria.where("VuViecDonThu.NoiDungVuViec").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            criteria.add(new Criteria().orOperator(subCriterias));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getTieuDeDonThu())) {
            criteria.add(Criteria.where("TieuDeDonThu").regex(toLikeKeyword(xuLyDonThuQueryModel.getTieuDeDonThu()), Constant.INSENSITIVE));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getNgayTiepNhan_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyDonThuQueryModel.getNgayTiepNhan_TuNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            criteria.add(Criteria.where("NgayTiepNhan").gte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getNgayTiepNhan_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(xuLyDonThuQueryModel.getNgayTiepNhan_DenNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            criteria.add(Criteria.where("NgayTiepNhan").lte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getHinhThucNhanDonThu_MaMuc())) {
            criteria.add(Criteria.where("HinhThucNhanDonThu.MaMuc").is(xuLyDonThuQueryModel.getHinhThucNhanDonThu_MaMuc()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getTiepCongDan_MaDinhDanh())) {
            criteria.add(Criteria.where("TiepCongDan.MaDinhDanh").is(xuLyDonThuQueryModel.getTiepCongDan_MaDinhDanh()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getVuViecDonThu_MaDinhDanh())) {
            criteria.add(Criteria.where("VuViecDonThu.MaDinhDanh").is(xuLyDonThuQueryModel.getVuViecDonThu_MaDinhDanh()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getVuViecDonThu_NoiDungVuViec())) {
            criteria.add(Criteria.where("VuViecDonThu.NoiDungVuViec").regex(toLikeKeyword(xuLyDonThuQueryModel.getVuViecDonThu_NoiDungVuViec()), Constant.INSENSITIVE));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getLoaiDoiTuongDungTen_MaMuc())) {
            criteria.add(Criteria.where("LoaiDoiTuongDungTen.MaMuc").is(xuLyDonThuQueryModel.getLoaiDoiTuongDungTen_MaMuc()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getLoaiVuViecDonThu_MaMuc())) {
            criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(xuLyDonThuQueryModel.getLoaiVuViecDonThu_MaMuc()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getLinhVucChuyenNganh_MaMuc())) {
            criteria.add(Criteria.where("VuViecDonThu.LinhVucChuyenNganh.MaMuc").is(xuLyDonThuQueryModel.getLinhVucChuyenNganh_MaMuc()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getCoQuanQuanLy_MaDinhDanh())) {
            criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(xuLyDonThuQueryModel.getCoQuanQuanLy_MaDinhDanh()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getDonViTheoDoi_MaDinhDanh())) {
            criteria.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(xuLyDonThuQueryModel.getDonViTheoDoi_MaDinhDanh()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getCanBoXuLy_MaDinhDanh())) {
            criteria.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(xuLyDonThuQueryModel.getCanBoXuLy_MaDinhDanh()));
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getTinhTrangXuLyDonThu_MaMuc())
                && xuLyDonThuQueryModel.getTinhTrangXuLyDonThu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TinhTrangXuLyDonThu.MaMuc")
                    .in(Arrays.asList(xuLyDonThuQueryModel.getTinhTrangXuLyDonThu_MaMuc()));
            criteria.add(c);
        }
        if (Validator.isNotNull(xuLyDonThuQueryModel.getTrangThaiDuLieu_MaMuc())
                && xuLyDonThuQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc")
                    .in(Arrays.asList(xuLyDonThuQueryModel.getTrangThaiDuLieu_MaMuc()));
            criteria.add(c);
        }
        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
            if (!listPhanQuyen.isEmpty()) {
                criteria.add(new Criteria().orOperator(listPhanQuyen));
            }
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        List<XuLyDonThu> xuLyDonThuList = mongoTemplate.find(query, XuLyDonThu.class);
        if (xuLyDonThuList.isEmpty()) {
            return xuLyDonThuList;
        }
        List<String> mdd_VVDT = xuLyDonThuList.stream().filter(e -> Validator.isNotNull(e.getVuViecDonThu()) && Validator.isNotNull(e.getVuViecDonThu().getMaDinhDanh())).map(XuLyDonThu::getVuViecDonThu).collect(Collectors.toList()).stream().map(XuLyDonThu_VuViecDonThu::getMaDinhDanh).collect(Collectors.toList());
        if (mdd_VVDT.isEmpty()) {
            return xuLyDonThuList;
        }

        //Query VVDT
        Query queryVVDT = new Query();
        queryVVDT.addCriteria(new Criteria().andOperator(Criteria.where("TrangThaiDuLieu.MaMuc").in(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc()),
                Criteria.where("MaDinhDanh").in(mdd_VVDT)));
        List<VuViecDonThu> vuViecDonThuList = mongoTemplate.find(queryVVDT, VuViecDonThu.class);
        if (vuViecDonThuList.isEmpty()) {
            return xuLyDonThuList;
        }
        Map<String, VuViecDonThu> map_VVDT = vuViecDonThuList.stream().collect(Collectors.toMap(VuViecDonThu::getMaDinhDanh, item -> item));
        for (XuLyDonThu xuLyDonThu : xuLyDonThuList) {
            if (Validator.isNotNull(xuLyDonThu.getVuViecDonThu()) && Validator.isNotNull(xuLyDonThu.getVuViecDonThu().getMaDinhDanh()) && map_VVDT.containsKey(xuLyDonThu.getVuViecDonThu().getMaDinhDanh())) {
                VuViecDonThu vuViecDonThu = map_VVDT.getOrDefault(xuLyDonThu.getVuViecDonThu().getMaDinhDanh(), null);
                if (Validator.isNotNull(vuViecDonThu)) {
                    if (Validator.isNotNull(vuViecDonThu.getPhanTichKetQuaKNTC())) {
                        XuLyDonThu_VuViecDonThu.PhanTichKetQuaKNTC phanTichKetQuaKNTC = new XuLyDonThu_VuViecDonThu.PhanTichKetQuaKNTC();
                        phanTichKetQuaKNTC.setMaMuc(vuViecDonThu.getPhanTichKetQuaKNTC().getMaMuc());
                        phanTichKetQuaKNTC.setTenMuc(vuViecDonThu.getPhanTichKetQuaKNTC().getTenMuc());
                        xuLyDonThu.getVuViecDonThu().setPhanTichKetQuaKNTC(phanTichKetQuaKNTC);
                    }
                    if (Validator.isNotNull(vuViecDonThu.getLoaiPhucTapKeoDai())) {
                        XuLyDonThu_VuViecDonThu.LoaiPhucTapKeoDai loaiPhucTapKeoDai = new XuLyDonThu_VuViecDonThu.LoaiPhucTapKeoDai();
                        loaiPhucTapKeoDai.setMaMuc(vuViecDonThu.getLoaiPhucTapKeoDai().getMaMuc());
                        loaiPhucTapKeoDai.setTenMuc(vuViecDonThu.getLoaiPhucTapKeoDai().getTenMuc());
                        xuLyDonThu.getVuViecDonThu().setLoaiPhucTapKeoDai(loaiPhucTapKeoDai);
                    }
                    if (Validator.isNotNull(vuViecDonThu.getKhoiKienHanhChinh())) {
                        XuLyDonThu_VuViecDonThu.KhoiKienHanhChinh khoiKienHanhChinh = new XuLyDonThu_VuViecDonThu.KhoiKienHanhChinh();
                        khoiKienHanhChinh.setMaMuc(vuViecDonThu.getKhoiKienHanhChinh().getMaMuc());
                        khoiKienHanhChinh.setTenMuc(vuViecDonThu.getKhoiKienHanhChinh().getTenMuc());
                        xuLyDonThu.getVuViecDonThu().setKhoiKienHanhChinh(khoiKienHanhChinh);
                    }
                }
            }
        }
        return xuLyDonThuList;
    }

    @Override
    public List<XuLyDonThu> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return xuLyDonThuRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<XuLyDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc) {
        return xuLyDonThuRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<XuLyDonThu> findByMaDonThuAndTrangThaiDuLieu(String maDonThu, String trangThaiDuLieu_MaMuc) {
        return xuLyDonThuRepository.findByMaDonThuAndTrangThaiDuLieu(maDonThu, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<XuLyDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return xuLyDonThuRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Map<String, Long> thongKeTinhTrangXuLyDonThu(Integer namThongKe) {

        List<AggregationOperation> searchOperationList = new ArrayList<>();

        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());
            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            searchOperationList.add(aggregationOperation);
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }


        searchOperationList.add(Aggregation.group("TinhTrangXuLyDonThu.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
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
    public Map<String, Long> thongKeTrangThaiGiaiQuyetDon() {

        List<AggregationOperation> searchOperationList = new ArrayList<>();

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }


        searchOperationList.add(Aggregation.group("TrangThaiGiaiQuyetDon.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
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
    public Map<String, Long> thongKeLoaiVuViecDonThu(Integer namThongKe) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());
            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            searchOperationList.add(aggregationOperation);
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(Aggregation.group("VuViecDonThu.LoaiVuViecDonThu.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
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
    public Map<String, Long> thongKePhamViThamQuyen(Integer namThongKe) {
        //ThongKeDonViThamQuyen
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);
            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            searchOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);


        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(Aggregation.group("VuViecDonThu.CoQuanThamQuyen.MaDinhDanh").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
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
    public Map<String, Long> thongKeDonTrungNoiDung(Integer namThongKe) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        if (Validator.isNotNull(namThongKe)) {

            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            searchOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(Aggregation.group("VuViecDonThu.MaDinhDanh").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
                Document.class);
        Map<String, Long> hashMap = new HashMap<>();
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj) && obj.has(Constant._ID) && !Validator.isBlank(obj.getString(Constant._ID))) {
                    hashMap.put(obj.getString(Constant._ID), obj.optLong(Constant._SO_LUONG, 0L));
                }
            }
        }
        return hashMap;
    }

    @Override
    public Map<String, Long> thongKeCoQuanCoThamQuyen(Integer namThongKe, String coQuanThamQuyen, String tinhTrangXuLyDonThu) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        if (Validator.isNotNull(namThongKe)) {

            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);
            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            searchOperationList.add(aggregationOperation);
        }
        if (Validator.isNotNull(coQuanThamQuyen)) {
            Criteria c = Criteria.where("VuViecDonThu.CoQuanThamQuyen.MaDinhDanh").is(coQuanThamQuyen);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }
        if (Validator.isNotNull(tinhTrangXuLyDonThu)) {
            Criteria c = Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(tinhTrangXuLyDonThu);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(Aggregation.group("VuViecDonThu.LoaiVuViecDonThu.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
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
    public Map<String, Long> thongKeCoQuanKhongCoThamQuyen(Integer namThongKe, String coQuanThamQuyen) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            searchOperationList.add(aggregationOperation);
        }
        if (Validator.isNotNull(coQuanThamQuyen)) {
            Criteria c = Criteria.where("VuViecDonThu.CoQuanThamQuyen.MaDinhDanh").ne(coQuanThamQuyen);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(Aggregation.group("TinhTrangXuLyDonThu.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
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
    public Map<String, Long> thongKeKetQuaKiemTraDieuKienThuLy(Integer namThongKe, String[] tinhTrangXuLyDonThu) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();


        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());
            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            searchOperationList.add(aggregationOperation);
        }
        if (Validator.isNotNull(tinhTrangXuLyDonThu) && tinhTrangXuLyDonThu.length > 0) {
            Criteria c = Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(tinhTrangXuLyDonThu));
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }
        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        searchOperationList.add(Aggregation.group("TinhTrangXuLyDonThu.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
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
    public List<KetQuaXuLyDonModel> thongKeKetQuaXuLyDon(Integer namThongKe, String tinhTrangXuLyDonThu) {
        String query = "{ \"$group\": { \"_id\": { \"CoQuanThamQuyen\":\"$VuViecDonThu.CoQuanThamQuyen.MaDinhDanh\", \"LoaiVuViecDonThu\":\"$VuViecDonThu.LoaiVuViecDonThu.MaMuc\" }, \"SoLuong\": { \"$sum\": 1 } } }";
        List<AggregationOperation> searchOperationList = new ArrayList<>();
        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());
            AggregationOperation aggregationOperation = Aggregation.match(c_THTH);
            searchOperationList.add(aggregationOperation);
        }
        if (Validator.isNotNull(tinhTrangXuLyDonThu)) {
            Criteria c = Criteria.where("TinhTrangXuLyDonThu.MaMuc").is(tinhTrangXuLyDonThu);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }
        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(new CustomProjectAggregationOperation(query));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, XuLyDonThu.class,
                Document.class);
        List<KetQuaXuLyDonModel> resultKQXL = new ArrayList<>();
        JSONObject result = new JSONObject(resultCountDB.getRawResults());
        JSONArray results = result.getJSONArray(Constant._RESULTS);
        if (!results.isEmpty()) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.optJSONObject(i);
                if (Validator.isNotNull(obj)) {
                    try {
                        KetQuaXuLyDonModel ketQuaXuLyDonModel = new ObjectMapper().readValue(obj.toString(), KetQuaXuLyDonModel.class);
                        if (Validator.isNotNull(ketQuaXuLyDonModel.get_id().getCoQuanThamQuyen()) && Validator.isNotNull(ketQuaXuLyDonModel.get_id().getLoaiVuViecDonThu())) {
                            resultKQXL.add(ketQuaXuLyDonModel);
                        }
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
                }
            }
        }
        return resultKQXL;
    }

    @Override
    public Long countDonThuNhacDanh(Integer namThongKe, boolean isDanh) {
        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();

        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c_THTH = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());
            criteria.add(c_THTH);
        }
        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        criteria.add(c);
        c = Criteria.where("TenNguoiDaiDien").exists(isDanh);
        criteria.add(c);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_XU_LY_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_XU_LY_DON_THU, new TaiNguyenHeThong());

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
            if (!listPhanQuyen.isEmpty()) {
                criteria.add(new Criteria().orOperator(listPhanQuyen));
            }
        }


        query.addCriteria(new Criteria().andOperator(criteria));

        return mongoTemplate.count(query, XuLyDonThu.class);
    }

    @Override
    public List<XuLyDonThu> findByVuViecDonThu(String maDinhDanh, String trangThaiDuLieu_MaMuc) {
        return xuLyDonThuRepository.findByVuViecDonThu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Long countMaDonThu(String regex) {
        return xuLyDonThuRepository.countMaDonThu(regex);
    }

    @Override
    public Optional<XuLyDonThu> findByTiepCongDanAndTrangThaiDuLieu(String maDinhDanhTiepCongDan, String trangThaiDuLieu_MaMuc) {
        return xuLyDonThuRepository.findByTiepCongDanAndTrangThaiDuLieu(maDinhDanhTiepCongDan, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<XuLyDonThu> findByTiepCongDanAndTrangThaiDuLieu(String[] maDinhDanhTiepCongDan, String trangThaiDuLieu_MaMuc) {
        return xuLyDonThuRepository.findByTiepCongDanAndTrangThaiDuLieu(maDinhDanhTiepCongDan, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}
