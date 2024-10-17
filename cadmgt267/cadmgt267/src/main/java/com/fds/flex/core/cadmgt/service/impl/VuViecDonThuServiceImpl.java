package com.fds.flex.core.cadmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.DateTimeUtils;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.context.model.Service.TaiNguyenHeThong;
import com.fds.flex.context.model.User;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.constant.LogConstant;
import com.fds.flex.core.cadmgt.entity.C_Model.LoaiVuViecDonThu;
import com.fds.flex.core.cadmgt.entity.Query_Model.VuViecDonThuQueryModel;
import com.fds.flex.core.cadmgt.entity.T_Model.VuViecDonThu;
import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import com.fds.flex.core.cadmgt.repository.VuViecDonThuRepository;
import com.fds.flex.core.cadmgt.service.CacheService;
import com.fds.flex.core.cadmgt.service.VuViecDonThuService;
import com.fds.flex.core.cadmgt.util.CADMgtUtil;
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
public class VuViecDonThuServiceImpl implements VuViecDonThuService {
    private static final String _KHIEU_NAI = "khieunai";
    private static final String _TO_CAO = "tocao";
    private static final String _PHAN_ANH_KIEN_NGHI = "phananhkiennghi";
    private static final String _TO_GIAC_TOI_PHAM = "togiactoipham";
    @Autowired
    private VuViecDonThuRepository vuViecDonThuRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Cacheable(value = DBConstant.T_VU_VIEC_DON_THU, key = "#id", condition = "#result != null && #result.isPresent()")
    @Override
    public Optional<VuViecDonThu> findById(String id) {
        log.info(LogConstant.findById, VuViecDonThu.class.getSimpleName(), id);
        return vuViecDonThuRepository.findById(id);
    }

    @Override
    @CacheEvict(value = DBConstant.T_VU_VIEC_DON_THU, key = "#vuViecDonThu.primKey", condition = "#vuViecDonThu.primKey != null")
    public void deleteVuViecDonThu(VuViecDonThu vuViecDonThu) {
        log.info(LogConstant.deleteById, VuViecDonThu.class.getSimpleName(), vuViecDonThu.getPrimKey());
        vuViecDonThuRepository.delete(vuViecDonThu);
    }

    @Override
    @CacheEvict(value = DBConstant.T_VU_VIEC_DON_THU, allEntries = true)
    public VuViecDonThu updateVuViecDonThu(VuViecDonThu vuViecDonThu) {
        log.info(LogConstant.updateById, VuViecDonThu.class.getSimpleName(), vuViecDonThu.getPrimKey());

        vuViecDonThu = vuViecDonThuRepository.save(vuViecDonThu);
        return vuViecDonThu;
    }

    @Override
    @CacheEvict(value = DBConstant.T_VU_VIEC_DON_THU, allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, VuViecDonThu> update(Map<String, VuViecDonThu> map) {
        log.info(LogConstant.updateByMap, VuViecDonThu.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                vuViecDonThuRepository.delete(v);
            } else {
                vuViecDonThuRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<VuViecDonThu> filter(VuViecDonThuQueryModel vuViecDonThuQueryModel, Pageable pageable) {
        Query query = new Query().with(pageable);

        if (!CADMgtUtil.checkSuperAdmin() && !CADMgtUtil.checkAdmin() && UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            List<String> lst = UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new ArrayList<>());
            if (!lst.isEmpty()) {
                query.fields().exclude(lst.toArray(new String[0]));
            }
        }
        query.fields().exclude("MaPhienBan", "NguonThamChieu", "NhatKiSuaDoi",
                "LienKetURL", "MaDinhDanhThayThe", "ChuSoHuu");

        List<Criteria> criteria = new ArrayList<>();
        if (Validator.isNotNull(vuViecDonThuQueryModel.getKeyword())) {
            String keyword = vuViecDonThuQueryModel.getKeyword();
            List<Criteria> subCriterias = new ArrayList<>();
            subCriterias.add(Criteria.where("NoiDungVuViec").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            subCriterias.add(Criteria.where("DoiTuongVuViec.TenGoi").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            subCriterias.add(Criteria.where("NguoiDuocTiep.GiayToChungNhan.SoGiay").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            criteria.add(new Criteria().orOperator(subCriterias));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getLoaiVuViecDonThu_MaMuc())) {
            criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(vuViecDonThuQueryModel.getLoaiVuViecDonThu_MaMuc()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getTinhTrangXuLyVuViec_MaMuc())) {
            criteria.add(Criteria.where("TinhTrangXuLyVuViec.MaMuc").is(vuViecDonThuQueryModel.getTinhTrangXuLyVuViec_MaMuc()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getNgayTiepNhan_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(vuViecDonThuQueryModel.getNgayTiepNhan_TuNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            criteria.add(Criteria.where("NgayTiepNhan").gte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getNgayTiepNhan_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(vuViecDonThuQueryModel.getNgayTiepNhan_DenNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            criteria.add(Criteria.where("NgayTiepNhan").lte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getDoiTuongVuViec_LoaiDoiTuongCNTC_MaMuc())) {
            criteria.add(Criteria.where("DoiTuongVuViec.LoaiDoiTuongCNTC.MaMuc").is(vuViecDonThuQueryModel.getDoiTuongVuViec_LoaiDoiTuongCNTC_MaMuc()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getPhanTichKetQuaKNTC_MaMuc())) {
            criteria.add(Criteria.where("PhanTichKetQuaKNTC.MaMuc").is(vuViecDonThuQueryModel.getPhanTichKetQuaKNTC_MaMuc()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getKienNghiXuLyKNTC_MaMuc())) {
            criteria.add(Criteria.where("KienNghiXuLyKNTC.MaMuc").is(vuViecDonThuQueryModel.getKienNghiXuLyKNTC_MaMuc()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getHoSoNghiepVu_SoDangKy())) {
            criteria.add(Criteria.where("HoSoNghiepVu.SoDangKy").is(vuViecDonThuQueryModel.getHoSoNghiepVu_SoDangKy()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getHoSoNghiepVu_TrangThaiHoSoNghiepVu_MaMuc())) {
            criteria.add(Criteria.where("HoSoNghiepVu.TrangThaiHoSoNghiepVu.MaMuc").is(vuViecDonThuQueryModel.getHoSoNghiepVu_TrangThaiHoSoNghiepVu_MaMuc()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getDoiTuongVuViec_MaDinhDanh())) {
            criteria.add(Criteria.where("DoiTuongVuViec.MaDinhDanh").is(vuViecDonThuQueryModel.getDoiTuongVuViec_MaDinhDanh()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getLinhVucChuyenNganh_MaMuc())) {
            criteria.add(Criteria.where("LinhVucChuyenNganh.MaMuc").is(vuViecDonThuQueryModel.getLinhVucChuyenNganh_MaMuc()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getCoQuanQuanLy_MaDinhDanh())) {
            criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(vuViecDonThuQueryModel.getCoQuanQuanLy_MaDinhDanh()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getDonViTheoDoi_MaDinhDanh())) {
            criteria.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(vuViecDonThuQueryModel.getDonViTheoDoi_MaDinhDanh()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getCanBoXuLy_MaDinhDanh())) {
            criteria.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(vuViecDonThuQueryModel.getCanBoXuLy_MaDinhDanh()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getXuLyDonThu_MaDinhDanh())) {
            criteria.add(Criteria.where("XuLyDonThu.MaDinhDanh").is(vuViecDonThuQueryModel.getXuLyDonThu_MaDinhDanh()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getLoaiDanhSach())) {
            if (vuViecDonThuQueryModel.getLoaiDanhSach().equalsIgnoreCase(_KHIEU_NAI)) {
                criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
            } else if (vuViecDonThuQueryModel.getLoaiDanhSach().equalsIgnoreCase(_TO_CAO)) {
                criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.ToCao.getMaMuc()));
            } else if (vuViecDonThuQueryModel.getLoaiDanhSach().equalsIgnoreCase(_PHAN_ANH_KIEN_NGHI)) {
                criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));
            } else if (vuViecDonThuQueryModel.getLoaiDanhSach().equalsIgnoreCase(_TO_GIAC_TOI_PHAM)) {
                criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.ToGiacToiPham.getMaMuc()));
            }
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getTrangThaiDuLieu_MaMuc())
                && vuViecDonThuQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc")
                    .in(Arrays.asList(vuViecDonThuQueryModel.getTrangThaiDuLieu_MaMuc()));
            criteria.add(c);
        }
        if (!CADMgtUtil.checkSuperAdmin()) {
            criteria.add(new Criteria().orOperator(
                    Criteria.where("DonViTheoDoi.MaDinhDanh").in(UserContextHolder.getContext().getUser().getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()),
                    Criteria.where("DonViTheoDoi").exists(false)
            ));
        }
        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new TaiNguyenHeThong());

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

            if (!listPhanQuyen.isEmpty()) {
                criteria.add(new Criteria().orOperator(listPhanQuyen));
            }
        }


        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, VuViecDonThu.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, VuViecDonThu.class));
    }

    @Override
    public List<VuViecDonThu> filterWithoutPageable(VuViecDonThuQueryModel vuViecDonThuQueryModel) {
        Query query = new Query();

        if (!CADMgtUtil.checkSuperAdmin() && !CADMgtUtil.checkAdmin() && UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            List<String> lst = UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new ArrayList<>());
            if (!lst.isEmpty()) {
                query.fields().exclude(lst.toArray(new String[0]));
            }
        }
        query.fields().exclude("MaPhienBan", "NguonThamChieu", "NhatKiSuaDoi",
                "LienKetURL", "MaDinhDanhThayThe"
                , "ChuSoHuu");

        List<Criteria> criteria = new ArrayList<>();
        if (Validator.isNotNull(vuViecDonThuQueryModel.getKeyword())) {
            String keyword = vuViecDonThuQueryModel.getKeyword();
            List<Criteria> subCriterias = new ArrayList<>();
            subCriterias.add(Criteria.where("NoiDungVuViec").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            criteria.add(new Criteria().orOperator(subCriterias));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getLoaiVuViecDonThu_MaMuc())) {
            criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(vuViecDonThuQueryModel.getLoaiVuViecDonThu_MaMuc()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getTinhTrangXuLyVuViec_MaMuc())) {
            criteria.add(Criteria.where("TinhTrangXuLyVuViec.MaMuc").is(vuViecDonThuQueryModel.getTinhTrangXuLyVuViec_MaMuc()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getNgayTiepNhan_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(vuViecDonThuQueryModel.getNgayTiepNhan_TuNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            criteria.add(Criteria.where("NgayTiepNhan").gte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getNgayTiepNhan_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(vuViecDonThuQueryModel.getNgayTiepNhan_DenNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            criteria.add(Criteria.where("NgayTiepNhan").lte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getDoiTuongVuViec_MaDinhDanh())) {
            criteria.add(Criteria.where("DoiTuongVuViec.MaDinhDanh").is(vuViecDonThuQueryModel.getDoiTuongVuViec_MaDinhDanh()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getLinhVucChuyenNganh_MaMuc())) {
            criteria.add(Criteria.where("LinhVucChuyenNganh.MaMuc").is(vuViecDonThuQueryModel.getLinhVucChuyenNganh_MaMuc()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getCoQuanQuanLy_MaDinhDanh())) {
            criteria.add(Criteria.where("CoQuanQuanLy.MaDinhDanh").is(vuViecDonThuQueryModel.getCoQuanQuanLy_MaDinhDanh()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getDonViTheoDoi_MaDinhDanh())) {
            criteria.add(Criteria.where("DonViTheoDoi.MaDinhDanh").is(vuViecDonThuQueryModel.getDonViTheoDoi_MaDinhDanh()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getCanBoXuLy_MaDinhDanh())) {
            criteria.add(Criteria.where("CanBoXuLy.MaDinhDanh").is(vuViecDonThuQueryModel.getCanBoXuLy_MaDinhDanh()));
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getLoaiDanhSach())) {
            if (vuViecDonThuQueryModel.getLoaiDanhSach().equalsIgnoreCase(_KHIEU_NAI)) {
                criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
            } else if (vuViecDonThuQueryModel.getLoaiDanhSach().equalsIgnoreCase(_TO_CAO)) {
                criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.ToCao.getMaMuc()));
            } else if (vuViecDonThuQueryModel.getLoaiDanhSach().equalsIgnoreCase(_PHAN_ANH_KIEN_NGHI)) {
                criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));
            } else if (vuViecDonThuQueryModel.getLoaiDanhSach().equalsIgnoreCase(_TO_GIAC_TOI_PHAM)) {
                criteria.add(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.ToGiacToiPham.getMaMuc()));
            }
        }
        if (Validator.isNotNull(vuViecDonThuQueryModel.getTrangThaiDuLieu_MaMuc())
                && vuViecDonThuQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc")
                    .in(Arrays.asList(vuViecDonThuQueryModel.getTrangThaiDuLieu_MaMuc()));
            criteria.add(c);
        }
        if (!CADMgtUtil.checkSuperAdmin()) {
            criteria.add(new Criteria().orOperator(
                    Criteria.where("DonViTheoDoi.MaDinhDanh").in(UserContextHolder.getContext().getUser().getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()),
                    Criteria.where("DonViTheoDoi").exists(false)
            ));
        }
        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new TaiNguyenHeThong());

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

            if (!listPhanQuyen.isEmpty()) {
                criteria.add(new Criteria().orOperator(listPhanQuyen));
            }
        }


        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        return mongoTemplate.find(query, VuViecDonThu.class);
    }

    @Override
    public List<VuViecDonThu> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return vuViecDonThuRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    @Cacheable(value = DBConstant.T_VU_VIEC_DON_THU, key = "{#maDinhDanh, #trangThaiDuLieu_MaMuc}")
    public Optional<VuViecDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh,
                                                                     String trangThaiDuLieu_MaMuc) {
        return vuViecDonThuRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<VuViecDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return vuViecDonThuRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<VuViecDonThu> findByHSNV_TTDL(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return vuViecDonThuRepository.findByHSNV_TTDL(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Map<String, Long> thongKeTinhTrangXuLyVuViec(String loaiDanhSach, String[] tinhTrangXuLyVuViec, Integer namThongKe_Int, String loaiVuViec) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();


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
            searchOperationList.add(aggregationOperation);
        }

        if (!CADMgtUtil.checkSuperAdmin()) {
            AggregationOperation aggregationOperation = Aggregation.match(
                    new Criteria().orOperator(
                            Criteria.where("DonViTheoDoi.MaDinhDanh").in(UserContextHolder.getContext().getUser().getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()),
                            Criteria.where("DonViTheoDoi").exists(false)
                    ));
            searchOperationList.add(aggregationOperation);
        }

        if (Validator.isNotNull(loaiDanhSach)) {
            if (loaiDanhSach.equalsIgnoreCase(_KHIEU_NAI)) {
                AggregationOperation aggregationOperation = Aggregation.match(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()));
                searchOperationList.add(aggregationOperation);
            } else if (loaiDanhSach.equalsIgnoreCase(_TO_CAO)) {
                AggregationOperation aggregationOperation = Aggregation.match(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.ToCao.getMaMuc()));
                searchOperationList.add(aggregationOperation);
            } else if (loaiDanhSach.equalsIgnoreCase(_TO_GIAC_TOI_PHAM)) {
                AggregationOperation aggregationOperation = Aggregation.match(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.ToGiacToiPham.getMaMuc()));
                searchOperationList.add(aggregationOperation);
            } else if (loaiDanhSach.equalsIgnoreCase(_PHAN_ANH_KIEN_NGHI)) {
                AggregationOperation aggregationOperation = Aggregation.match(Criteria.where("LoaiVuViecDonThu.MaMuc").is(LoaiVuViecDonThu.Loai.PhanAnhKienNghi.getMaMuc()));
                searchOperationList.add(aggregationOperation);
            }
        }

        if (Validator.isNotNull(loaiVuViec)) {
            Criteria c = Criteria.where("LoaiVuViecDonThu.MaMuc").is(loaiVuViec);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }


        searchOperationList.add(Aggregation.group("TinhTrangXuLyVuViec.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
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
    public Map<String, Long> thongKeXuLyTinBanDau(Integer namThongKe, String[] tinhTrangXuLyDonThu, String loaiVuViecDonThu) {
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
        if (Validator.isNotNull(loaiVuViecDonThu)) {
            Criteria c = Criteria.where("LoaiVuViecDonThu.MaMuc").is(loaiVuViecDonThu);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }
        if (Validator.isNotNull(tinhTrangXuLyDonThu) && tinhTrangXuLyDonThu.length > 0) {
            Criteria c = Criteria.where("TinhTrangXuLyDonThu.MaMuc").in(Arrays.asList(tinhTrangXuLyDonThu));
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(Aggregation.group("TinhTrangXuLyDonThu.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
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

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new TaiNguyenHeThong());

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
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }


        searchOperationList.add(Aggregation.group("LoaiVuViecDonThu.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, VuViecDonThu.class,
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
    public Map<String, Long> thongKeQuyetDinhGiaiQuyetKhieuNai(Integer namThongKe, String loaiVuViecDonThu) {
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

            Criteria c = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        if (Validator.isNotNull(loaiVuViecDonThu)) {
            Criteria c = Criteria.where("LoaiVuViecDonThu.MaMuc").is(loaiVuViecDonThu);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);

        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(Aggregation.group("PhanTichKetQuaKNTC.MaMuc").count().as(Constant._SO_LUONG));
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
    public Map<String, Long> thongKeTheoKienNghiXuLyKNTC(Integer namThongKe, String loaiVuViecDonThu) {
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

        if (Validator.isNotNull(loaiVuViecDonThu)) {
            Criteria c = Criteria.where("LoaiVuViecDonThu.MaMuc").is(loaiVuViecDonThu);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(Aggregation.group("KienNghiXuLyKNTC.MaMuc").count().as(Constant._SO_LUONG));
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
    public Map<String, Long> thongKeKienNghiXuLy(Integer namThongKe, String loaiVuViecDonThu) {
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

            Criteria c = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        if (Validator.isNotNull(loaiVuViecDonThu)) {
            Criteria c = Criteria.where("LoaiVuViecDonThu.MaMuc").is(loaiVuViecDonThu);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        searchOperationList.add(Aggregation.group("KienNghiXuLyKNTC.MaMuc").count().as(Constant._SO_LUONG));
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
                    total = obj.optLong(Constant._SO_LUONG, 0L);
                }
            }
        }
        hashMap.put(Constant._ALL, total);
        return hashMap;
    }

    @Override
    public Map<String, Long> thongKeTheoPhanTichKetQuaKNTC(Integer namThongKe, String loaiVuViecDonThu) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();


        if (Validator.isNotNull(namThongKe)) {
            Calendar ca_from = Calendar.getInstance();
            Calendar ca_to = Calendar.getInstance();
            ca_from.set(namThongKe, Calendar.JANUARY, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);
            ca_to.set(namThongKe + 1, Calendar.JANUARY, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c = Criteria.where("NgayTiepNhan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());

            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        if (Validator.isNotNull(loaiVuViecDonThu)) {
            Criteria c = Criteria.where("LoaiVuViecDonThu.MaMuc").is(loaiVuViecDonThu);
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);

        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_VU_VIEC_DON_THU)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_VU_VIEC_DON_THU, new TaiNguyenHeThong());

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
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }

        searchOperationList.add(Aggregation.group("PhanTichKetQuaKNTC.MaMuc").count().as(Constant._SO_LUONG));
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


    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }
}
