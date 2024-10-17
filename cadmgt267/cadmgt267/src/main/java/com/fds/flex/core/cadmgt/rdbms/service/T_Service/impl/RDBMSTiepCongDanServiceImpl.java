package com.fds.flex.core.cadmgt.rdbms.service.T_Service.impl;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.context.model.User;
import com.fds.flex.core.cadmgt.entity.Query_Model.TiepCongDanQueryModel;
import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSTiepCongDan;
import com.fds.flex.core.cadmgt.rdbms.repository.T_Repository.RDBMSTiepCongDanRepository;
import com.fds.flex.core.cadmgt.rdbms.service.T_Service.RDBMSTiepCongDanService;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Service
public class RDBMSTiepCongDanServiceImpl implements RDBMSTiepCongDanService {

    //@Autowired
    private RDBMSTiepCongDanRepository rdbmsTiepCongDanRepository;
    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    @Cacheable(value = "TiepCongDan", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<RDBMSTiepCongDan> findById(String id) {
        return rdbmsTiepCongDanRepository.findById(Long.valueOf(id));
    }

    @Override
    @CacheEvict(value = "TiepCongDan", allEntries = true)
    public void deleteTiepCongDan(RDBMSTiepCongDan rdbmsTiepCongDan) {
        rdbmsTiepCongDanRepository.delete(rdbmsTiepCongDan);
    }

    @Override
    @CacheEvict(value = "TiepCongDan", allEntries = true)
    public RDBMSTiepCongDan updateTiepCongDan(RDBMSTiepCongDan rdbmsTiepCongDan) {
        return rdbmsTiepCongDanRepository.saveAndFlush(rdbmsTiepCongDan);
    }

    @Override
    @CacheEvict(value = "CaNhanToChuc", allEntries = true)
    public Map<String, RDBMSTiepCongDan> update(Map<String, RDBMSTiepCongDan> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsTiepCongDanRepository.delete(v);
            } else {
                rdbmsTiepCongDanRepository.saveAndFlush(v);
            }
        });
        return map;
    }

    @Override
    public Page<RDBMSTiepCongDan> filter(TiepCongDanQueryModel tiepCongDanQueryModel, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RDBMSTiepCongDan> tiepCongDanCriteriaQuery = criteriaBuilder.createQuery(RDBMSTiepCongDan.class);
        Root<RDBMSTiepCongDan> root = tiepCongDanCriteriaQuery.from(RDBMSTiepCongDan.class);
        List<Predicate> conditionList = new ArrayList<>();

        // Keyword search
        if (Validator.isNotNull(tiepCongDanQueryModel.getKeyword())) {
            String keywordPattern = "%" + tiepCongDanQueryModel.getKeyword() + "%";
            List<Predicate> orPredicates = new ArrayList<>();

            orPredicates.add(criteriaBuilder.like(root.get("nguoiDaiDien"), keywordPattern));
            orPredicates.add(criteriaBuilder.like(criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("nguoiDuocTiep"), criteriaBuilder.literal("$.TenGoi")), keywordPattern));
            orPredicates.add(criteriaBuilder.like(criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("giayToChungNhan"), criteriaBuilder.literal("$.SoGiay")), keywordPattern));
            orPredicates.add(criteriaBuilder.like(criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("vuViecDonThu"), criteriaBuilder.literal("$.NoiDungVuViec")), keywordPattern));

            conditionList.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
        }

        // Date range for NgayTiepCongDan
        if (Validator.isNotNull(tiepCongDanQueryModel.getNgayTiepCongDan_TuNgay())) {
            LocalDate fromDate = DateTimeUtils.stringToLocalDate(tiepCongDanQueryModel.getNgayTiepCongDan_TuNgay(), DateTimeUtils._VN_DATE_FORMAT);
            conditionList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ngayTiepCongDan"), fromDate.atStartOfDay()));
        }
        if (Validator.isNotNull(tiepCongDanQueryModel.getNgayTiepCongDan_DenNgay())) {
            LocalDate toDate = DateTimeUtils.stringToLocalDate(tiepCongDanQueryModel.getNgayTiepCongDan_DenNgay(), DateTimeUtils._VN_DATE_FORMAT);
            conditionList.add(criteriaBuilder.lessThanOrEqualTo(root.get("ngayTiepCongDan"), toDate.atTime(LocalTime.MAX)));
        }

        // NguoiTiepCongDan
        if (Validator.isNotNull(tiepCongDanQueryModel.getNguoiTiepCongDan_MaDinhDanh())) {
            conditionList.add(criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("nguoiTiepCongDan"), criteriaBuilder.literal("$.MaDinhDanh")),
                    tiepCongDanQueryModel.getNguoiTiepCongDan_MaDinhDanh()));
        }

        // CheDoTiepCongDan
        if (Validator.isNotNull(tiepCongDanQueryModel.getCheDoTiepCongDan_MaMuc()) && tiepCongDanQueryModel.getCheDoTiepCongDan_MaMuc().length > 0) {
            conditionList.add(criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("cheDoTiepCongDan"), criteriaBuilder.literal("$.MaMuc"))
                    .in((Object[]) tiepCongDanQueryModel.getCheDoTiepCongDan_MaMuc()));
        }

        // NguoiDuocTiep
        if (Validator.isNotNull(tiepCongDanQueryModel.getNguoiDuocTiep_TenGoi())) {
            String tenGoiPattern = "%" + tiepCongDanQueryModel.getNguoiDuocTiep_TenGoi() + "%";
            conditionList.add(criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("nguoiDuocTiep"), criteriaBuilder.literal("$.TenGoi")),
                    tenGoiPattern));
        }

        // KetQuaTiepCongDan
        if (Validator.isNotNull(tiepCongDanQueryModel.getKetQuaTiepCongDan_MaMuc()) && tiepCongDanQueryModel.getKetQuaTiepCongDan_MaMuc().length > 0) {
            conditionList.add(criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("ketQuaTiepCongDan"), criteriaBuilder.literal("$.MaMuc"))
                    .in((Object[]) tiepCongDanQueryModel.getKetQuaTiepCongDan_MaMuc()));
        }

        // TinhTrangTiepCongDan
        if (Validator.isNotNull(tiepCongDanQueryModel.getTinhTrangTiepCongDan_MaMuc())) {
            conditionList.add(criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("tinhTrangTiepCongDan"), criteriaBuilder.literal("$.MaMuc")),
                    tiepCongDanQueryModel.getTinhTrangTiepCongDan_MaMuc()));
        }

        // LoaiVuViecDonThu
        if (Validator.isNotNull(tiepCongDanQueryModel.getLoaiVuViecDonThu_MaMuc())) {
            conditionList.add(criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("vuViecDonThu"), criteriaBuilder.literal("$.LoaiVuViecDonThu.MaMuc")),
                    tiepCongDanQueryModel.getLoaiVuViecDonThu_MaMuc()));
        }

        // DoiTuongVuViec
        if (Validator.isNotNull(tiepCongDanQueryModel.getDoiTuongVuViec_TenGoi())) {
            String doiTuongVuViecPattern = "%" + tiepCongDanQueryModel.getDoiTuongVuViec_TenGoi() + "%";
            conditionList.add(criteriaBuilder.like(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("vuViecDonThu"), criteriaBuilder.literal("$.DoiTuongVuViec.TenGoi")),
                    doiTuongVuViecPattern));
        }

        // DiaBanXuLy
        if (Validator.isNotNull(tiepCongDanQueryModel.getVuViecDonThu_DiaBanXuLy_MaMuc())) {
            conditionList.add(criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("vuViecDonThu"), criteriaBuilder.literal("$.DiaBanXuLy.MaMuc")),
                    tiepCongDanQueryModel.getVuViecDonThu_DiaBanXuLy_MaMuc()));
        }

        // TrangThaiDuLieu
        if (Validator.isNotNull(tiepCongDanQueryModel.getTrangThaiDuLieu_MaMuc()) && tiepCongDanQueryModel.getTrangThaiDuLieu_MaMuc().length > 0) {
            conditionList.add(criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("trangThaiDuLieu"), criteriaBuilder.literal("$.MaMuc"))
                    .in((Object[]) tiepCongDanQueryModel.getTrangThaiDuLieu_MaMuc()));
        }

        // Phân quyền
        if (!CADMgtUtil.checkSuperAdmin() && ServiceContextHolder.getContext().getService().getTaiNguyenHeThong().containsKey(DBConstant.T_TIEP_CONG_DAN)) {
            TaiNguyenHeThong taiNguyenHeThong = ServiceContextHolder.getContext().getService().getTaiNguyenHeThong().getOrDefault(DBConstant.T_TIEP_CONG_DAN, new TaiNguyenHeThong());
            User user = UserContextHolder.getContext().getUser();
            List<Predicate> listPhanQuyen = new ArrayList<>();

            if (taiNguyenHeThong.isHanCheBanGhi()) {
                listPhanQuyen.add(criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("chiaSeTaiKhoan"), criteriaBuilder.literal("$.DanhTinhDienTu.MaDinhDanh"))
                        .in(user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(criteriaBuilder.equal(
                        criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("nguoiTaoLap"), criteriaBuilder.literal("$.MaDinhDanh")),
                        user.getDanhTinhDienTu().getMaDinhDanh()));
                listPhanQuyen.add(criteriaBuilder.equal(
                        criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("chuSoHuu"), criteriaBuilder.literal("$.MaDinhDanh")),
                        user.getDanhTinhDienTu().getMaDinhDanh()));
            }

            if (taiNguyenHeThong.isHanChePhanVung()) {
                listPhanQuyen.add(criteriaBuilder.function("JSON_EXTRACT", String.class, root.get("phanVungDuLieu"), criteriaBuilder.literal("$.MaMuc"))
                        .in(user.getQuyenSuDung().getMaPhanVungDuLieuTruyCapLst()));
            }

            if (!listPhanQuyen.isEmpty()) {
                conditionList.add(criteriaBuilder.or(listPhanQuyen.toArray(new Predicate[0])));
            }
        }

        if (!conditionList.isEmpty()) {
            tiepCongDanCriteriaQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        }

        // Sorting
        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String property = StringUtil.lowerFirstChar(order.getProperty());
            Order jpaOrder = order.isAscending() ? criteriaBuilder.asc(root.get(property)) : criteriaBuilder.desc(root.get(property));
            orders.add(jpaOrder);
        }
        tiepCongDanCriteriaQuery.orderBy(orders);

        // Pagination
        TypedQuery<RDBMSTiepCongDan> query = entityManager.createQuery(tiepCongDanCriteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Count query for pagination
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(RDBMSTiepCongDan.class)));
        countQuery.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])));
        long total = entityManager.createQuery(countQuery).getSingleResult();

        List<RDBMSTiepCongDan> tiepCongDanList = query.getResultList();

        // Additional processing (e.g., XuLyDonThu)
        // Note: This part might need to be adjusted based on your specific requirements and data model
        String[] maDinhDanhTCD = tiepCongDanList.stream().map(RDBMSTiepCongDan::getMaDinhDanh).toArray(String[]::new);
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

        return PageableExecutionUtils.getPage(tiepCongDanList, pageable, () -> total);
    }
    @Override
    public List<RDBMSTiepCongDan> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc) {
        return Collections.emptyList();
    }

    @Override
    public Optional<RDBMSTiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc) {
        return Optional.empty();
    }

    @Override
    public List<RDBMSTiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc) {
        return Collections.emptyList();
    }

    @Override
    public List<RDBMSTiepCongDan> findByVuViecDonThuMaDinhDanhAndTrangThaiDuLieu(String vuViecDonThu_MaDinhDanh, String trangThaiDuLieu_MaMuc) {
        return Collections.emptyList();
    }

    @Override
    public Map<String, Long> thongKeTinhTrangTiepCongDan(String cheDoTiepCongDan_MaMuc) {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Long> thongKeKetQuaTiepCongDan(String cheDoTiepCongDan_MaMuc) {
        return Collections.emptyMap();
    }

    @Override
    public Long countSoThuTuTiep(String regex) {
        return 0;
    }

    @Override
    public Optional<RDBMSTiepCongDan> findBySoThuTuTiepAndTrangThaiDuLieu(String soThuTuTiep, String trangThaiDuLieu_MaMuc) {
        return Optional.empty();
    }
}
