package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.TimeZoneUtil;
import com.fds.flex.context.model.Service.TaiNguyenHeThong;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.Query_Model.KhaoSatNamTinhHinhQueryModel;
import com.fds.flex.core.inspectionmgt.entity.T_Model.KhaoSatNamTinhHinh;
import com.fds.flex.core.inspectionmgt.repository.KhaoSatNamTinhHinhRepository;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.service.KhaoSatNamTinhHinhService;
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

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@Slf4j
public class KhaoSatNamTinhHinhServiceImpl implements KhaoSatNamTinhHinhService {
    @Autowired
    private KhaoSatNamTinhHinhRepository khaoSatNamTinhHinhRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Override
    @Cacheable(value = "KhaoSatNamTinhHinh", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<KhaoSatNamTinhHinh> findById(String id) {
        log.info(LogConstant.findById, KhaoSatNamTinhHinh.class.getSimpleName(), id);
        return khaoSatNamTinhHinhRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "KhaoSatNamTinhHinh", allEntries = true)
    public void deleteKhaoSatNamTinhHinh(KhaoSatNamTinhHinh khaoSatNamTinhHinh) {
        log.info(LogConstant.deleteById, KhaoSatNamTinhHinh.class.getSimpleName(), khaoSatNamTinhHinh.getPrimKey());
        khaoSatNamTinhHinhRepository.delete(khaoSatNamTinhHinh);
    }

    @Override
    @CacheEvict(value = "KhaoSatNamTinhHinh", allEntries = true)
    public KhaoSatNamTinhHinh updateKhaoSatNamTinhHinh(KhaoSatNamTinhHinh khaoSatNamTinhHinh) {
        log.info(LogConstant.updateById, KhaoSatNamTinhHinh.class.getSimpleName(), khaoSatNamTinhHinh.getPrimKey());
        return khaoSatNamTinhHinhRepository.save(khaoSatNamTinhHinh);
    }


    @Override
    @CacheEvict(value = "KhaoSatNamTinhHinh", allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, KhaoSatNamTinhHinh> update(Map<String, KhaoSatNamTinhHinh> map) {
        log.info(LogConstant.updateByMap, KhaoSatNamTinhHinh.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                khaoSatNamTinhHinhRepository.delete(v);
            } else {
                khaoSatNamTinhHinhRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<KhaoSatNamTinhHinh> filter(KhaoSatNamTinhHinhQueryModel khaoSatNamTinhHinhQueryModel, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, KhaoSatNamTinhHinh.class.getSimpleName());

        Query query = new Query().with(pageable);

        if (!InspectionUtil.checkSuperAdmin()
                && !InspectionUtil.checkAdmin()
                && UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().containsKey(DBConstant.T_KHAO_SAT_NAM_TINH_HINH)) {
            List<String> lst = UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().getOrDefault(DBConstant.T_KHAO_SAT_NAM_TINH_HINH, new ArrayList<>());
            if (!lst.isEmpty()) {
                query.fields().exclude(lst.toArray(new String[0]));
            }
        }
        query.fields().exclude("MaPhienBan", "NguonThamChieu", "NhatKiSuaDoi",
                "LienKetURL", "MaDinhDanhThayThe"
                , "ChuSoHuu");

        List<Criteria> criteria = new ArrayList<>();
        if (Validator.isNotNull(khaoSatNamTinhHinhQueryModel.getKeyword())) {
            String keyword = khaoSatNamTinhHinhQueryModel.getKeyword();
            List<Criteria> subCriterias = new ArrayList<>();
            Criteria c = Criteria.where("TieuDeKhaoSat")
                    .regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            criteria.add(new Criteria().orOperator(subCriterias));
        }

        if (Validator.isNotNull(khaoSatNamTinhHinhQueryModel.getNhiemVuCongViec_NamKeHoach())) {
            Criteria c = Criteria.where("NhiemVuCongViec.NamKeHoach").is(Integer.parseInt(khaoSatNamTinhHinhQueryModel.getNhiemVuCongViec_NamKeHoach()));
            criteria.add(c);
        }

        if (Validator.isNotNull(khaoSatNamTinhHinhQueryModel.getNhiemVuCongViec_NamVanBan())) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(Integer.parseInt(khaoSatNamTinhHinhQueryModel.getNhiemVuCongViec_NamVanBan()), 0, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(Integer.parseInt(khaoSatNamTinhHinhQueryModel.getNhiemVuCongViec_NamVanBan()) + 1, 0, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c = Criteria.where("NhiemVuCongViec.NgayVanBan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());
            criteria.add(c);
        }

        if (Validator.isNotNull(khaoSatNamTinhHinhQueryModel.getLoaiCheDoThanhTra_MaMuc())) {
            Criteria c = Criteria.where("LoaiCheDoThanhTra.MaMuc").is(khaoSatNamTinhHinhQueryModel.getLoaiCheDoThanhTra_MaMuc());
            criteria.add(c);
        }

        if (Validator.isNotNull(khaoSatNamTinhHinhQueryModel.getCoQuanThucHien_MaDinhDanh())) {
            Criteria c = Criteria.where("CoQuanThucHien.MaDinhDanh").is(khaoSatNamTinhHinhQueryModel.getCoQuanThucHien_MaDinhDanh());
            criteria.add(c);
        }

        if (Validator.isNotNull(khaoSatNamTinhHinhQueryModel.getDonViThucHien_MaDinhDanh())) {
            Criteria c = Criteria.where("DonViThucHien.MaDinhDanh").is(khaoSatNamTinhHinhQueryModel.getDonViThucHien_MaDinhDanh());
            criteria.add(c);
        }

        if (Validator.isNotNull(khaoSatNamTinhHinhQueryModel.getTrangThaiKhaoSat_MaMuc())) {
            Criteria c = Criteria.where("TrangThaiKhaoSat.MaMuc").is(khaoSatNamTinhHinhQueryModel.getTrangThaiKhaoSat_MaMuc());
            criteria.add(c);
        }


        if (Validator.isNotNull(khaoSatNamTinhHinhQueryModel.getTrangThaiDuLieu_MaMuc())
                && khaoSatNamTinhHinhQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc")
                    .in(Arrays.asList(khaoSatNamTinhHinhQueryModel.getTrangThaiDuLieu_MaMuc()));
            criteria.add(c);
        }
        //Phân Quyền
        if (!InspectionUtil.checkSuperAdmin()) {
            User user = UserContextHolder.getContext().getUser();
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_KHAO_SAT_NAM_TINH_HINH, new TaiNguyenHeThong());

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
        log.debug(LogConstant.finishGenerateQueryFilter, KhaoSatNamTinhHinh.class.getSimpleName(), new JSONObject(query));

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, KhaoSatNamTinhHinh.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, KhaoSatNamTinhHinh.class));
    }

    @Override
    public List<KhaoSatNamTinhHinh> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return khaoSatNamTinhHinhRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<KhaoSatNamTinhHinh> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc) {
        return khaoSatNamTinhHinhRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<KhaoSatNamTinhHinh> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return khaoSatNamTinhHinhRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Map<String, Long> thongKeTrangThaiKhaoSat(String loaiCheDoThanhTra_MaMuc, String nhiemVuCongViec_NamKeHoach, String namVanBan) {

        List<AggregationOperation> searchOperationList = new ArrayList<>();

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

        if (Validator.isNotNull(namVanBan)) {
            Calendar ca_from = Calendar.getInstance();
            ca_from.setTimeZone(TimeZoneUtil.UTC);
            ca_from.set(Integer.parseInt(namVanBan), 0, 1, 0, 0, 0);
            ca_from.set(Calendar.MILLISECOND, 0);

            Calendar ca_to = Calendar.getInstance();
            ca_to.setTimeZone(TimeZoneUtil.UTC);
            ca_to.set(Integer.parseInt(namVanBan) + 1, 0, 1, 0, 0, 0);
            ca_to.set(Calendar.MILLISECOND, 0);

            Criteria c = Criteria.where("NhiemVuCongViec.NgayVanBan").gte(ca_from.getTimeInMillis()).lt(ca_to.getTimeInMillis());
            AggregationOperation aggregationOperation = Aggregation.match(c);
            searchOperationList.add(aggregationOperation);
        }

        Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc").is(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
        AggregationOperation aggregationOperation = Aggregation.match(c);
        searchOperationList.add(aggregationOperation);

        //Phân quyền
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_KHAO_SAT_NAM_TINH_HINH)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_KHAO_SAT_NAM_TINH_HINH, new TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            if (taiNguyenHeThong.isHanCheBanGhi() && Validator.isNotNull(user.getDanhTinhDienTu().getDoiTuongXacThuc())) {
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));
            }
            if (!listPhanQuyen.isEmpty())
                searchOperationList.add(Aggregation.match(new Criteria().orOperator(listPhanQuyen)));
        }


        searchOperationList.add(Aggregation.group("TrangThaiKhaoSat.MaMuc").count().as(Constant._SO_LUONG));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, Constant._ID));
        searchOperationList.add(sort);

        Aggregation agg = newAggregation(searchOperationList);
        AggregationResults<Document> resultCountDB = mongoTemplate.aggregate(agg, KhaoSatNamTinhHinh.class,
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
}
