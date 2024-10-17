package com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.exception.ForbiddenException;
import com.fds.flex.common.ultility.MessageUtil;
import com.fds.flex.common.ultility.ResponseUtil;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.context.model.Service;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.constant.Constant;
import com.fds.flex.core.inspectionmgt.constant.NotificationConstant;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.*;
import com.fds.flex.core.inspectionmgt.entity.S_Model.LichCongTacDoan;
import com.fds.flex.core.inspectionmgt.entity.S_Model.TepDuLieu;
import com.fds.flex.core.inspectionmgt.entity.S_Model.ThanhVienDoan;
import com.fds.flex.core.inspectionmgt.rdbms.converter.*;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseModel;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Table(name = DBConstant.T_HOAT_DONG_THANH_TRA)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSHoatDongThanhTra extends RDBMSBaseModel<RDBMSHoatDongThanhTra> {
    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Column(name = "MaDinhDanh", nullable = false)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("TenHoatDong")
    @Column(name = "TenHoatDong", nullable = false, length = 1024)
    public String tenHoatDong = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("LoaiHoatDongThanhTra")
    @Column(name = "LoaiHoatDongThanhTra", nullable = false, columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_LoaiHoatDongThanhTraConverter.class)
    public HoatDongThanhTra_LoaiHoatDongThanhTra loaiHoatDongThanhTra = new HoatDongThanhTra_LoaiHoatDongThanhTra();

    @JsonInclude
    @JsonProperty("LoaiCheDoThanhTra")
    @Column(name = "LoaiCheDoThanhTra", nullable = false, columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_LoaiCheDoThanhTraConverter.class)
    public HoatDongThanhTra_LoaiCheDoThanhTra loaiCheDoThanhTra = new HoatDongThanhTra_LoaiCheDoThanhTra();

    @JsonInclude
    @JsonProperty("NhiemVuCongViec")
    @Column(name = "NhiemVuCongViec", columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_NhiemVuCongViecConverter.class)
    public HoatDongThanhTra_NhiemVuCongViec nhiemVuCongViec = null;

    @JsonInclude
    @JsonProperty("SoQuyetDinh")
    @Column(name = "SoQuyetDinh", nullable = false)
    public String soQuyetDinh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("NgayQuyetDinh")
    @Column(name = "NgayQuyetDinh")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayQuyetDinh = null;

    @JsonInclude
    @JsonProperty("CoQuanBanHanh")
    @Column(name = "CoQuanBanHanh", columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_CoQuanBanHanhConverter.class)
    public HoatDongThanhTra_CoQuanBanHanh coQuanBanHanh = null;

    @JsonInclude
    @JsonProperty("TepDuLieu")
    @Column(name = "TepDuLieu", columnDefinition = "json")
    @Convert(converter = TepDuLieuListConverter.class)
    public List<TepDuLieu> tepDuLieu = new ArrayList<>();

    @JsonInclude
    @JsonProperty("GiayToLuuTruSo")
    @Column(name = "GiayToLuuTruSo", columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_GiayToLuuTruSoConverter.class)
    public HoatDongThanhTra_GiayToLuuTruSo giayToLuuTruSo = null;

    @JsonInclude
    @JsonProperty("CoQuanQuanLy")
    @Column(name = "CoQuanQuanLy", nullable = false, columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_CoQuanQuanLyConverter.class)
    public HoatDongThanhTra_CoQuanQuanLy coQuanQuanLy = new HoatDongThanhTra_CoQuanQuanLy();

    @JsonInclude
    @JsonProperty("DonViChuTri")
    @Column(name = "DonViChuTri", columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_DonViChuTriConverter.class)
    public HoatDongThanhTra_DonViChuTri donViChuTri = null;

    @JsonInclude
    @JsonProperty("CanBoTheoDoi")
    @Column(name = "CanBoTheoDoi", nullable = false, columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_CanBoTheoDoiConverter.class)
    public List<HoatDongThanhTra_CanBoTheoDoi> canBoTheoDoi = new ArrayList<>();

    @JsonInclude
    @JsonProperty("LinhVucChuyenNganh")
    @Column(name = "LinhVucChuyenNganh", columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_LinhVucChuyenNganhConverter.class)
    public HoatDongThanhTra_LinhVucChuyenNganh linhVucChuyenNganh = null;

    @JsonInclude
    @JsonProperty("NoiDungHoatDong")
    @Column(name = "NoiDungHoatDong", nullable = false, length = 2048)
    public String noiDungHoatDong = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("ThoiHanThucHien")
    @Column(name = "ThoiHanThucHien")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanThucHien = null;

    @JsonInclude
    @JsonProperty("GiaHanThucHien")
    @Column(name = "GiaHanThucHien")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long giaHanThucHien = null;

    @JsonInclude
    @JsonProperty("DoiTuongThanhTra")
    @Column(name = "DoiTuongThanhTra", nullable = false, columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_DoiTuongThanhTraConverter.class)
    public List<HoatDongThanhTra_DoiTuongThanhTra> doiTuongThanhTra = new ArrayList<>();

    @JsonInclude
    @JsonProperty("ThanhVienDoan")
    @Column(name = "ThanhVienDoan", nullable = false, columnDefinition = "json")
    @Convert(converter = ThanhVienDoanConverter.class)
    public List<ThanhVienDoan> thanhVienDoan = new ArrayList<>();

    @JsonInclude
    @JsonProperty("LichCongTacDoan")
    @Column(name = "LichCongTacDoan", nullable = false, columnDefinition = "json")
    @Convert(converter = LichCongTacDoanConverter.class)
    public List<LichCongTacDoan> lichCongTacDoan = new ArrayList<>();

    @JsonInclude
    @JsonProperty("TrangThaiHoatDongThanhTra")
    @Column(name = "TrangThaiHoatDongThanhTra", nullable = false, columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_TrangThaiHoatDongThanhTraConverter.class)
    public HoatDongThanhTra_TrangThaiHoatDongThanhTra trangThaiHoatDongThanhTra = new HoatDongThanhTra_TrangThaiHoatDongThanhTra();

    @JsonInclude
    @JsonProperty("NgayKetThuc")
    @Column(name = "NgayKetThuc")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayKetThuc = null;

    @JsonInclude
    @JsonProperty("HoSoNghiepVu")
    @Column(name = "HoSoNghiepVu", columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_HoSoNghiepVuConverter.class)
    public HoatDongThanhTra_HoSoNghiepVu hoSoNghiepVu = null;

    @JsonInclude
    @JsonProperty("HuongDanChuyenDe")
    @Column(name = "HuongDanChuyenDe", columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_HuongDanChuyenDeConverter.class)
    public HoatDongThanhTra_HuongDanChuyenDe huongDanChuyenDe = null;

    @JsonInclude
    @JsonProperty("HoSoBaoCao")
    @Column(name = "HoSoBaoCao", nullable = false, columnDefinition = "json")
    @Convert(converter = HoatDongThanhTra_HoSoBaoCaoConverter.class)
    public List<HoatDongThanhTra_HoSoBaoCao> hoSoBaoCao = new ArrayList<>();

    @Transient
    @JsonInclude
    @JsonProperty("CapNhatDuLieu")
    public Boolean capNhatDuLieu = false;

    public RDBMSHoatDongThanhTra() {
        super(false);
        super.setType(getType());
    }

    public RDBMSHoatDongThanhTra(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());
    }

    @Override
    public String getType() {
        return DBConstant.T_HOAT_DONG_THANH_TRA;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.T_HOAT_DONG_THANH_TRA);
    }

    @Override
    public String getPrimKey() {
        if (Validator.isNull(super.getPrimKey())) {
            String primKey = String.valueOf(this.getId());
            super.setPrimKey(primKey);
            return primKey;
        }
        return super.getPrimKey();
    }

    public boolean isCapNhatDuLieu() {
        if (InspectionUtil.checkSuperAdmin() || InspectionUtil.checkAdmin()) {
            return true;
        }
        User user = UserContextHolder.getContext().getUser();
        if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
            if (this.getThanhVienDoan().isEmpty())
                return false;
            for (ThanhVienDoan thanhVienDoan : this.getThanhVienDoan()) {
                if (thanhVienDoan.getCanBo().getMaDinhDanh().equals(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh())) {
                    return true;
                }
            }
        } else {
            return false;
        }

        return false;
    }


}
