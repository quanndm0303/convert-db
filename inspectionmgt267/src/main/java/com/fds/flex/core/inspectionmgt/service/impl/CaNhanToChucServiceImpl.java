package com.fds.flex.core.inspectionmgt.service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.context.model.Service.TaiNguyenHeThong;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.LogConstant;
import com.fds.flex.core.inspectionmgt.entity.Query_Model.CaNhanToChucQueryModel;
import com.fds.flex.core.inspectionmgt.entity.T_Model.CaNhanToChuc;
import com.fds.flex.core.inspectionmgt.repository.CaNhanToChucRepository;
import com.fds.flex.core.inspectionmgt.service.CaNhanToChucService;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class CaNhanToChucServiceImpl implements CaNhanToChucService {

    public static final String _LOAIDANHSACH_DOITUONGBITHANHTRAKIEMTRA = "1";
    public static final String _LOAIDANHSACH_DOITUONGBIKIENNGHIXULY = "2";
    public static final String _LOAIDANHSACH_DOITUONGBIKHIEUNAITOCAO = "3";
    public static final String _LOAIDANHSACH_NGUOIKHIEUNAITOCAO = "4";
    @Autowired
    private CaNhanToChucRepository caNhanToChucRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CacheService cacheService;

    @Override
    @Cacheable(value = "CaNhanToChuc", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<CaNhanToChuc> findById(String id) {
        log.info(LogConstant.findById, CaNhanToChuc.class.getSimpleName(), id);
        return caNhanToChucRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "CaNhanToChuc", allEntries = true)
    public void deleteCaNhanToChuc(CaNhanToChuc caNhanToChuc) {
        log.info(LogConstant.deleteById, CaNhanToChuc.class.getSimpleName(), caNhanToChuc.getPrimKey());
        caNhanToChucRepository.delete(caNhanToChuc);
    }

    @Override
    @CacheEvict(value = "CaNhanToChuc", allEntries = true)
    public CaNhanToChuc updateCaNhanToChuc(CaNhanToChuc caNhanToChuc) {
        log.info(LogConstant.updateById, CaNhanToChuc.class.getSimpleName(), caNhanToChuc.getPrimKey());
        return caNhanToChucRepository.save(caNhanToChuc);
    }

    @Override
    @CacheEvict(value = "CaNhanToChuc", allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, CaNhanToChuc> update(Map<String, CaNhanToChuc> map) {
        log.info(LogConstant.updateByMap, CaNhanToChuc.class.getSimpleName(), map.toString());
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                caNhanToChucRepository.delete(v);
            } else {
                caNhanToChucRepository.save(v);
            }
        });

        return map;
    }

    @Override
    public Page<CaNhanToChuc> filter(CaNhanToChucQueryModel caNhanToChucQueryModel, Pageable pageable) {
        log.info(LogConstant.startGenerateQueryFilter, CaNhanToChuc.class.getSimpleName());
        Query query = new Query().with(pageable);

        if (!InspectionUtil.checkSuperAdmin()
                && !InspectionUtil.checkAdmin()
                && UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().containsKey(DBConstant.T_CA_NHAN_TO_CHUC)) {
            List<String> lst = UserContextHolder.getContext().getUser().getQuyenSuDung().getHanCheTruongDuLieuMap().getOrDefault(DBConstant.T_CA_NHAN_TO_CHUC, new ArrayList<>());
            if (!lst.isEmpty()) {
                query.fields().exclude(lst.toArray(new String[0]));
            }
        }
        query.fields().exclude("MaPhienBan", "NguonThamChieu", "NhatKiSuaDoi",
                "LienKetURL", "MaDinhDanhThayThe"
                , "ChuSoHuu");

        List<Criteria> criteria = new ArrayList<>();
        if (Validator.isNotNull(caNhanToChucQueryModel.getKeyword())) {

            String keyword = caNhanToChucQueryModel.getKeyword();

            List<Criteria> subCriterias = new ArrayList<>();
            Criteria c = Criteria.where("TenGoi").regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);
            c = Criteria.where("DanhBaLienLac.SoDienThoai").regex(InspectionUtil.toLikeKeyword(keyword), Constant.INSENSITIVE);
            subCriterias.add(c);

            criteria.add(new Criteria().orOperator(subCriterias));
        }
        if(Validator.isNotNull(caNhanToChucQueryModel.getLoaiDanhSach())){
            switch (caNhanToChucQueryModel.getLoaiDanhSach()){
                case _LOAIDANHSACH_DOITUONGBITHANHTRAKIEMTRA:
                    Criteria c = new Criteria().andOperator(Criteria.where("HoatDongThanhTraKiemTra").exists(true), Criteria.where("HoatDongThanhTraKiemTra").ne(Collections.emptyList()));
                    criteria.add(c);
                    break;
                case _LOAIDANHSACH_DOITUONGBIKIENNGHIXULY:
                    c = new Criteria().andOperator(Criteria.where("ThongBaoKetLuan").exists(true), Criteria.where("ThongBaoKetLuan").ne(Collections.emptyList()));
                    criteria.add(c);
                    break;
                case _LOAIDANHSACH_DOITUONGBIKHIEUNAITOCAO:
                    c = new Criteria().andOperator(Criteria.where("VuViecDonThu").exists(true), Criteria.where("VuViecDonThu").ne(Collections.emptyList()));
                    criteria.add(c);
                    break;
                case _LOAIDANHSACH_NGUOIKHIEUNAITOCAO:
                    c = new Criteria().andOperator(Criteria.where("XuLyDonThu").exists(true), Criteria.where("XuLyDonThu").ne(Collections.emptyList()));
                    criteria.add(c);
                    break;
            }
        }

        if (Validator.isNotNull(caNhanToChucQueryModel.getLoaiDoiTuongCNTC())) {
            Criteria c = Criteria.where("LoaiDoiTuongCNTC.MaMuc").is(caNhanToChucQueryModel.getLoaiDoiTuongCNTC());
            criteria.add(c);
        }

        if (Validator.isNotNull(caNhanToChucQueryModel.getDiaChi_TinhThanh_MaMuc())) {
            Criteria c = Criteria.where("DiaChi.TinhThanh.MaMuc").is(caNhanToChucQueryModel.getDiaChi_TinhThanh_MaMuc());
            criteria.add(c);
        }

        if (Validator.isNotNull(caNhanToChucQueryModel.getDiaChi_HuyenQuan_MaMuc())) {
            Criteria c = Criteria.where("DiaChi.HuyenQuan.MaMuc").is(caNhanToChucQueryModel.getDiaChi_HuyenQuan_MaMuc());
            criteria.add(c);
        }

        if (Validator.isNotNull(caNhanToChucQueryModel.getDiaChi_XaPhuong_MaMuc())) {
            Criteria c = Criteria.where("DiaChi.XaPhuong.MaMuc").is(caNhanToChucQueryModel.getDiaChi_XaPhuong_MaMuc());
            criteria.add(c);
        }

        if (Validator.isNotNull(caNhanToChucQueryModel.getTuCachPhapLy_MaMuc())) {
            Criteria c = Criteria.where("TuCachPhapLy.MaMuc").is(caNhanToChucQueryModel.getTuCachPhapLy_MaMuc());
            criteria.add(c);
        }

        if (Validator.isNotNull(caNhanToChucQueryModel.getMaDinhDanh())) {
            Criteria c = Criteria.where("MaDinhDanh").is(caNhanToChucQueryModel.getMaDinhDanh());
            criteria.add(c);
        }

        if (Validator.isNotNull(caNhanToChucQueryModel.getSoGiay())) {
            Criteria c = Criteria.where("GiayToChungNhan.SoGiay").is(caNhanToChucQueryModel.getSoGiay());
            criteria.add(c);
        }

        if (Validator.isNotNull(caNhanToChucQueryModel.getTrangThaiDuLieu_MaMuc())
                && caNhanToChucQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            Criteria c = Criteria.where("TrangThaiDuLieu.MaMuc")
                    .in(Arrays.asList(caNhanToChucQueryModel.getTrangThaiDuLieu_MaMuc()));
            criteria.add(c);
        }
        //Phân quyền
        if (!InspectionUtil.checkSuperAdmin() && ServiceContextHolder.getContext()
                .getService().getTaiNguyenHeThong().containsKey(DBConstant.T_CA_NHAN_TO_CHUC)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext()
                    .getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_CA_NHAN_TO_CHUC, new TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Criteria> listPhanQuyen = new ArrayList<>();
            if (taiNguyenHeThong.isHanCheBanGhi())
                listPhanQuyen.add(Criteria.where("NguoiTaoLap.MaDinhDanh").is(user.getDanhTinhDienTu().getMaDinhDanh()));

            if (taiNguyenHeThong.isHanChePhanVung())
                listPhanQuyen.add(Criteria.where("PhanVungDuLieu.MaMuc").in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));

            if (!listPhanQuyen.isEmpty())
                criteria.add(new Criteria().orOperator(listPhanQuyen));

        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }
        log.debug(LogConstant.finishGenerateQueryFilter, CaNhanToChuc.class.getSimpleName(), new JSONObject(query));

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, CaNhanToChuc.class), pageable,
                () -> cacheService.countByQuery(query.toString(), query, CaNhanToChuc.class));
    }

    @Override
    public List<CaNhanToChuc> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return caNhanToChucRepository.findByUuidAndTrangThaiDuLieu(uuid, trangThaiDuLieu_MaMuc);
    }

    @Override
    public Optional<CaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc) {
        return caNhanToChucRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<CaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return caNhanToChucRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }

    @Override
    public List<CaNhanToChuc> findByMaDinhDanhAndTrangThaiDuLieu(String[] maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return caNhanToChucRepository.findByMaDinhDanhAndTrangThaiDuLieu(maDinhDanh, trangThaiDuLieu_MaMuc);
    }
}
