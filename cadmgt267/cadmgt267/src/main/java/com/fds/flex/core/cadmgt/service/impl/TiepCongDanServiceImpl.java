package com.fds.flex.core.cadmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.DateTimeUtils;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.context.model.Service.TaiNguyenHeThong;
import com.fds.flex.context.model.User;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.core.cadmgt.constant.LogConstant;
import com.fds.flex.core.cadmgt.entity.Extra_Model.TiepCongDan_XuLyDonThu;
import com.fds.flex.core.cadmgt.entity.Query_Model.TiepCongDanQueryModel;
import com.fds.flex.core.cadmgt.entity.T_Model.TiepCongDan;
import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import com.fds.flex.core.cadmgt.repository.TiepCongDanRepository;
import com.fds.flex.core.cadmgt.service.CacheService;
import com.fds.flex.core.cadmgt.service.TiepCongDanService;
import com.fds.flex.core.cadmgt.service.XuLyDonThuService;
import com.fds.flex.core.cadmgt.util.CADMgtUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@Slf4j
public class TiepCongDanServiceImpl implements TiepCongDanService {
    @Autowired
    private TiepCongDanRepository tiepCongDanRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private XuLyDonThuService xuLyDonThuService;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = DBConstant.T_TIEP_CONG_DAN, key = "#id", condition = "#result != null && #result.isPresent()")
    @Override
    public Optional<TiepCongDan> findById(String id) {
        log.info(LogConstant.findById, TiepCongDan.class.getSimpleName(), id);
        return tiepCongDanRepository.findById(id);
    }

    @Override
    @CacheEvict(value = DBConstant.T_TIEP_CONG_DAN, allEntries = true)
    public void deleteTiepCongDan(TiepCongDan tiepCongDan) {
        log.info(LogConstant.deleteById, TiepCongDan.class.getSimpleName(), tiepCongDan.getPrimKey());
        tiepCongDanRepository.delete(tiepCongDan);
    }

    @Override
    @CacheEvict(value = DBConstant.T_TIEP_CONG_DAN, allEntries = true)
    public TiepCongDan updateTiepCongDan(TiepCongDan tiepCongDan) {
        log.info(LogConstant.updateById, TiepCongDan.class.getSimpleName(), tiepCongDan.getPrimKey());

        return tiepCongDanRepository.save(tiepCongDan);
    }

    @Override
    @CacheEvict(value = DBConstant.T_TIEP_CONG_DAN, allEntries = true)
    public Map<String, TiepCongDan> update(Map<String, TiepCongDan> map) {
        log.info(LogConstant.updateByMap, TiepCongDan.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                tiepCongDanRepository.delete(v);
            } else {
                tiepCongDanRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<TiepCongDan> filter(TiepCongDanQueryModel tiepCongDanQueryModel, Pageable pageable) {
        Query query = new Query().with(pageable);
        if (!CADMgtUtil.checkSuperAdmin() && !CADMgtUtil.checkAdmin() && UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().containsKey(DBConstant.T_TIEP_CONG_DAN)) {
            List<String> lst = UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().getOrDefault(DBConstant.T_TIEP_CONG_DAN, new ArrayList<>());
            if (!lst.isEmpty()) {
                query.fields().exclude(lst.toArray(new String[0]));
            }
        }
        query.fields().exclude("MaPhienBan", "NguonThamChieu", "NhatKiSuaDoi",
                "LienKetURL", "MaDinhDanhThayThe"
                , "ChuSoHuu");
        List<Criteria> criteria = new ArrayList<>();
        if (Validator.isNotNull(tiepCongDanQueryModel.getKeyword())) {
            String keyword = tiepCongDanQueryModel.getKeyword();
            List<Criteria> subCriterias = new ArrayList<>();
            subCriterias.add(Criteria.where("NguoiDaiDien").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            subCriterias.add(Criteria.where("NguoiDuocTiep.TenGoi").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            subCriterias.add(Criteria.where("GiayToChungNhan.SoGiay").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            subCriterias.add(Criteria.where("VuViecDonThu.NoiDungVuViec").regex(toLikeKeyword(keyword), Constant.INSENSITIVE));
            criteria.add(new Criteria().orOperator(subCriterias));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getNgayTiepCongDan_TuNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(tiepCongDanQueryModel.getNgayTiepCongDan_TuNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
            ca.set(Calendar.MILLISECOND, 0);
            criteria.add(Criteria.where("NgayTiepCongDan").gte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getNgayTiepCongDan_DenNgay())) {
            LocalDate date = DateTimeUtils.stringToLocalDate(tiepCongDanQueryModel.getNgayTiepCongDan_DenNgay(), DateTimeUtils._VN_DATE_FORMAT);
            Calendar ca = Calendar.getInstance();
            ca.setTimeZone(TimeZoneUtil.UTC);
            ca.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 23, 59, 59);
            ca.set(Calendar.MILLISECOND, 999);
            criteria.add(Criteria.where("NgayTiepCongDan").lte(ca.getTimeInMillis()));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getNguoiTiepCongDan_MaDinhDanh())) {
            criteria.add(Criteria.where("NguoiTiepCongDan.MaDinhDanh").is(tiepCongDanQueryModel.getNguoiTiepCongDan_MaDinhDanh()));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getCheDoTiepCongDan_MaMuc())
                && tiepCongDanQueryModel.getCheDoTiepCongDan_MaMuc().length > 0) {
            criteria.add(Criteria.where("CheDoTiepCongDan.MaMuc").in(Arrays.asList(tiepCongDanQueryModel.getCheDoTiepCongDan_MaMuc())));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getNguoiDuocTiep_TenGoi())) {
            criteria.add(Criteria.where("NguoiDuocTiep.TenGoi").regex(toLikeKeyword(tiepCongDanQueryModel.getNguoiDuocTiep_TenGoi()), Constant.INSENSITIVE));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getKetQuaTiepCongDan_MaMuc())
                && tiepCongDanQueryModel.getKetQuaTiepCongDan_MaMuc().length > 0) {
            criteria.add(Criteria.where("KetQuaTiepCongDan.MaMuc").in(Arrays.asList(tiepCongDanQueryModel.getKetQuaTiepCongDan_MaMuc())));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getTinhTrangTiepCongDan_MaMuc())) {
            criteria.add(Criteria.where("TinhTrangTiepCongDan.MaMuc").is(tiepCongDanQueryModel.getTinhTrangTiepCongDan_MaMuc()));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getLoaiVuViecDonThu_MaMuc())) {
            criteria.add(Criteria.where("VuViecDonThu.LoaiVuViecDonThu.MaMuc").is(tiepCongDanQueryModel.getLoaiVuViecDonThu_MaMuc()));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getDoiTuongVuViec_TenGoi())) {
            criteria.add(Criteria.where("VuViecDonThu.DoiTuongVuViec.TenGoi").regex(toLikeKeyword(tiepCongDanQueryModel.getDoiTuongVuViec_TenGoi()), Constant.INSENSITIVE));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getVuViecDonThu_DiaBanXuLy_MaMuc())) {
            criteria.add(Criteria.where("VuViecDonThu.DiaBanXuLy.MaMuc").is(tiepCongDanQueryModel.getVuViecDonThu_DiaBanXuLy_MaMuc()));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getTrangThaiDuLieu_MaMuc())
                && tiepCongDanQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc")
                    .in(Arrays.asList(tiepCongDanQueryModel.getTrangThaiDuLieu_MaMuc()));
            criteria.add(c);
        }

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_TIEP_CONG_DAN)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_TIEP_CONG_DAN, new TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();
            if (taiNguyenHeThong.isHanCheBanGhi()) {
                listPhanQuyen.add(Criteria.where("ChiaSeVaiTro.VaiTroSuDung.MaMuc").in(user.getQuyenSuDung().getMaVaiTroLst()));
                listPhanQuyen.add(Criteria.where("ChiaSeTaiKhoan.DanhTinhDienTu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("ChuSoHuu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
            }
            if (taiNguyenHeThong.isHanChePhanVung()) {
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            }
            if (!listPhanQuyen.isEmpty()) {
                criteria.add(new Criteria().orOperator(listPhanQuyen));
            }
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }
        log.info(query.toString());

        Page<TiepCongDan> tiepCongDanPage = PageableExecutionUtils.getPage(mongoTemplate.find(query, TiepCongDan.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, TiepCongDan.class));
        List<TiepCongDan> tiepCongDanList = mongoTemplate.find(query, TiepCongDan.class);
        String[] maDinhDanhTCD = tiepCongDanList.stream().map(TiepCongDan::getMaDinhDanh).toArray(String[]::new);
        Map<String, XuLyDonThu> mapXLDT = xuLyDonThuService.findByTiepCongDanAndTrangThaiDuLieu(maDinhDanhTCD, TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())
                .stream().collect(Collectors.toMap(XuLyDonThu::getMaDinhDanh, item -> item));
        tiepCongDanList.forEach(tiepCongDan -> {
            if (mapXLDT.containsKey(tiepCongDan.getMaDinhDanh())) {
                XuLyDonThu xuLyDonThu = mapXLDT.get(tiepCongDan.getMaDinhDanh());
                TiepCongDan_XuLyDonThu tiepCongDan_xuLyDonThu = new TiepCongDan_XuLyDonThu();
                modelMapper.map(xuLyDonThu, tiepCongDan_xuLyDonThu);
                tiepCongDan.setXuLyDonThu(tiepCongDan_xuLyDonThu);
            }
        });

        return tiepCongDanPage;
    }

    @Override
    public List<TiepCongDan> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return tiepCongDanRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<TiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc) {
        return tiepCongDanRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<TiepCongDan> findByVuViecDonThuMaDinhDanhAndTrangThaiDuLieu(String vuViecDonThu_MaDinhDanh, String trangThaiDuLieu_MaMuc) {
        return tiepCongDanRepository.findByVuViecDonThuMaDinhDanhAndTrangThaiDuLieu(vuViecDonThu_MaDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Map<String, Long> thongKeTinhTrangTiepCongDan(String cheDoTiepCongDan_MaMuc) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        if (Validator.isNotNull(cheDoTiepCongDan_MaMuc)) {
            String[] cheDo = StringUtil.split(cheDoTiepCongDan_MaMuc);
            if (Validator.isNotNull(cheDo)
                    && cheDo.length > 0) {
                c = Criteria.where("CheDoTiepCongDan.MaMuc").in(Arrays.asList(cheDo));
                aggregationOperation = Aggregation.match(c);
                searchOperationList.add(aggregationOperation);
            }
        }

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_TIEP_CONG_DAN)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_TIEP_CONG_DAN, new TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();
            if (taiNguyenHeThong.isHanCheBanGhi()) {
                listPhanQuyen.add(Criteria.where("ChiaSeVaiTro.VaiTroSuDung.MaMuc").in(user.getQuyenSuDung().getMaVaiTroLst()));
                listPhanQuyen.add(Criteria.where("ChiaSeTaiKhoan.DanhTinhDienTu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("ChuSoHuu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
            }
            if (taiNguyenHeThong.isHanChePhanVung()) {
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            }
            if (!listPhanQuyen.isEmpty()) {
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
            }
        }

        searchOperationList.add(Aggregation.group("TinhTrangTiepCongDan.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, TiepCongDan.class,
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
    public Map<String, Long> thongKeKetQuaTiepCongDan(String cheDoTiepCongDan_MaMuc) {
        List<AggregationOperation> searchOperationList = new ArrayList<>();

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        if (Validator.isNotNull(cheDoTiepCongDan_MaMuc)) {
            String[] cheDo = StringUtil.split(cheDoTiepCongDan_MaMuc);
            if (Validator.isNotNull(cheDo)
                    && cheDo.length > 0) {
                c = Criteria.where("CheDoTiepCongDan.MaMuc").in(Arrays.asList(cheDo));
                aggregationOperation = Aggregation.match(c);
                searchOperationList.add(aggregationOperation);
            }
        }

        //Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_TIEP_CONG_DAN)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_TIEP_CONG_DAN, new TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();
            if (taiNguyenHeThong.isHanCheBanGhi()) {
                listPhanQuyen.add(Criteria.where("ChiaSeVaiTro.VaiTroSuDung.MaMuc").in(user.getQuyenSuDung().getMaVaiTroLst()));
                listPhanQuyen.add(Criteria.where("ChiaSeTaiKhoan.DanhTinhDienTu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(Criteria.where("ChuSoHuu.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
            }
            if (taiNguyenHeThong.isHanChePhanVung()) {
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            }
            if (!listPhanQuyen.isEmpty()) {
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
            }
        }

        searchOperationList.add(Aggregation.group("KetQuaTiepCongDan.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, TiepCongDan.class,
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
    public Long countSoThuTuTiep(String regex) {
        return tiepCongDanRepository.countSoThuTuTiep(regex);
    }

    @Override
    public Optional<TiepCongDan> findBySoThuTuTiepAndTrangThaiDuLieu(String soThuTuTiep, String trangThaiDuLieu_MaMuc) {
        return tiepCongDanRepository.findBySoThuTuTiepAndTrangThaiDuLieu(soThuTuTiep, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<TiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return tiepCongDanRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    private String toLikeKeyword(String source) {
        return source.replaceAll("\\*", ".*");
    }


}
