package com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.*;
import com.fds.flex.core.inspectionmgt.entity.S_Model.*;
import com.fds.flex.core.inspectionmgt.rdbms.converter.*;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = DBConstant.T_XU_LY_VPHC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSXuLyVPHC extends RDBMSBaseModel<RDBMSXuLyVPHC> {

    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Column(name = "MaDinhDanh")
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("CoQuanQuanLy")
    @Column(name = "CoQuanQuanLy", nullable = false, columnDefinition = "json")
    @Convert(converter = XuLyVPHC_CoQuanQuanLyConverter.class)
    public XuLyVPHC_CoQuanQuanLy coQuanQuanLy = new XuLyVPHC_CoQuanQuanLy();

    @JsonInclude
    @JsonProperty("DonViChuTri")
    @Column(name = "DonViChuTri", columnDefinition = "json")
    @Convert(converter = XuLyVPHC_DonViChuTriConverter.class)
    public XuLyVPHC_DonViChuTri donViChuTri = null;

    @JsonInclude
    @JsonProperty("CanBoTheoDoi")
    @Column(name = "CanBoTheoDoi", nullable = false, columnDefinition = "json")
    @Convert(converter = XuLyVPHC_CanBoTheoDoiConverter.class)
    public List<XuLyVPHC_CanBoTheoDoi> canBoTheoDoi = new ArrayList<>();

    @JsonInclude
    @JsonProperty("NgayLapBienBan")
    @Column(name = "NgayLapBienBan")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayLapBienBan = null;

    @JsonInclude
    @JsonProperty("SoBienBanVPHC")
    @Column(name = "SoBienBanVPHC")
    public String soBienBanVPHC = null;

    @JsonInclude
    @JsonProperty("TiepNhanVuViec")
    @Column(name = "TiepNhanVuViec", columnDefinition = "json")
    @Convert(converter = TiepNhanVuViecConverter.class)
    public TiepNhanVuViec tiepNhanVuViec = null;

    @JsonInclude
    @JsonProperty("DoiTuongVPHC")
    @Column(name = "DoiTuongVPHC", nullable = false, columnDefinition = "json")
    @Convert(converter = XuLyVPHC_DoiTuongVPHCConverter.class)
    public XuLyVPHC_DoiTuongVPHC doiTuongVPHC = new XuLyVPHC_DoiTuongVPHC();

    @JsonInclude
    @JsonProperty("NoiDungVPHC")
    @Column(name = "NoiDungVPHC", nullable = false, columnDefinition = "json")
    @Convert(converter = NoiDungVPHCConverter.class)
    public List<NoiDungVPHC> noiDungVPHC = new ArrayList<>();

    @JsonInclude
    @JsonProperty("TinhTietTangNang")
    @Column(name = "TinhTietTangNang", length = 1024)
    public String tinhTietTangNang = null;

    @JsonInclude
    @JsonProperty("TinhTietGiamNhe")
    @Column(name = "TinhTietGiamNhe", length = 1024)
    public String tinhTietGiamNhe = null;

    @JsonInclude
    @JsonProperty("SoQuyetDinh")
    @Column(name = "SoQuyetDinh")
    public String soQuyetDinh = null;

    @JsonInclude
    @JsonProperty("NgayQuyetDinh")
    @Column(name = "NgayQuyetDinh")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayQuyetDinh = null;

    @JsonInclude
    @JsonProperty("NgayHieuLuc")
    @Column(name = "NgayHieuLuc")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayHieuLuc = null;

    @JsonInclude
    @JsonProperty("CoQuanQuyetDinh")
    @Column(name = "CoQuanQuyetDinh", columnDefinition = "json")
    @Convert(converter = XuLyVPHC_CoQuanQuyetDinhConverter.class)
    public XuLyVPHC_CoQuanQuyetDinh coQuanQuyetDinh = null;

    @JsonInclude
    @JsonProperty("HinhThucXuLyChinh")
    @Column(name = "HinhThucXuLyChinh", columnDefinition = "json")
    @Convert(converter = XuLyVPHC_HinhThucXuLyChinhConverter.class)
    public XuLyVPHC_HinhThucXuLyChinh hinhThucXuLyChinh = new XuLyVPHC_HinhThucXuLyChinh();

    @JsonInclude
    @JsonProperty("NoiDungLyDoXuLy")
    @Column(name = "NoiDungLyDoXuLy", nullable = false, length = 2048)
    public String noiDungLyDoXuLy = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("GiayToTamGiu")
    @Column(name = "GiayToTamGiu", nullable = false)
    public String giayToTamGiu = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("BienPhapNganChanDamBao")
    @Column(name = "BienPhapNganChanDamBao", nullable = false, length = 4096)
    public String bienPhapNganChanDamBao = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("NgayNhanQuyetDinh")
    @Column(name = "NgayNhanQuyetDinh")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayNhanQuyetDinh = null;

    @JsonInclude
    @JsonProperty("TruongHopPhatTien")
    @Column(name = "TruongHopPhatTien", columnDefinition = "json")
    @Convert(converter = TruongHopPhatTienConverter.class)
    public TruongHopPhatTien truongHopPhatTien = null;

    @JsonInclude
    @JsonProperty("XuLyPhatBoSung")
    @Column(name = "XuLyPhatBoSung", nullable = false, columnDefinition = "json")
    @Convert(converter = XuLyPhatBoSungConverter.class)
    public List<XuLyPhatBoSung> xuLyPhatBoSung = new ArrayList<>();

    @JsonInclude
    @JsonProperty("KhacPhucHauQua")
    @Column(name = "KhacPhucHauQua", nullable = false, columnDefinition = "json")
    @Convert(converter = KhacPhucHauQuaConverter.class)
    public List<KhacPhucHauQua> khacPhucHauQua = new ArrayList<>();

    @JsonInclude
    @JsonProperty("ThoiHanChapHanh")
    @Column(name = "ThoiHanChapHanh")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanChapHanh = null;

    @JsonInclude
    @JsonProperty("CuongCheThiHanh")
    @Column(name = "CuongCheThiHanh", nullable = false, columnDefinition = "json")
    @Convert(converter = CuongCheThiHanhConverter.class)
    public List<CuongCheThiHanh> cuongCheThiHanh = new ArrayList<>();

    @JsonInclude
    @JsonProperty("TamDinhChiThiHanh")
    @Column(name = "TamDinhChiThiHanh", nullable = false, columnDefinition = "json")
    @Convert(converter = TamDinhChiThiHanhConverter.class)
    public List<TamDinhChiThiHanh> tamDinhChiThiHanh = new ArrayList<>();

    @JsonInclude
    @JsonProperty("KhieuNaiKhoiKien")
    @Column(name = "KhieuNaiKhoiKien", nullable = false, columnDefinition = "json")
    @Convert(converter = KhieuNaiKhoiKienConverter.class)
    public List<KhieuNaiKhoiKien> khieuNaiKhoiKien = new ArrayList<>();

    @JsonInclude
    @JsonProperty("ChuyenCoQuanThiHanh")
    @Column(name = "ChuyenCoQuanThiHanh", columnDefinition = "json")
    @Convert(converter = XuLyVPHC_ChuyenCoQuanThiHanhConverter.class)
    public XuLyVPHC_ChuyenCoQuanThiHanh chuyenCoQuanThiHanh = null;

    @JsonInclude
    @JsonProperty("ChuyenCoQuanDieuTra")
    @Column(name = "ChuyenCoQuanDieuTra", columnDefinition = "json")
    @Convert(converter = ChuyenCoQuanDieuTraConverter.class)
    public ChuyenCoQuanDieuTra chuyenCoQuanDieuTra = null;

    @JsonInclude
    @JsonProperty("TrangThaiXuLyVPHC")
    @Column(name = "TrangThaiXuLyVPHC", nullable = false, columnDefinition = "json")
    @Convert(converter = XuLyVPHC_TrangThaiXuLyVPHCConverter.class)
    public XuLyVPHC_TrangThaiXuLyVPHC trangThaiXuLyVPHC = new XuLyVPHC_TrangThaiXuLyVPHC();

    @JsonInclude
    @JsonProperty("NgayKetThuc")
    @Column(name = "NgayKetThuc")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayKetThuc = null;

    @JsonInclude
    @JsonProperty("LyDoHuyThiHanh")
    @Column(name = "LyDoHuyThiHanh", nullable = false)
    public String lyDoHuyThiHanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("ThanhPhanGiayTo")
    @Column(name = "ThanhPhanGiayTo", nullable = false, columnDefinition = "json")
    @Convert(converter = ThanhPhanGiayToConverter.class)
    public List<ThanhPhanGiayTo> thanhPhanGiayTo = new ArrayList<>();

    public RDBMSXuLyVPHC() {
        super(false);
        super.setType(getType());
    }

    public RDBMSXuLyVPHC(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());
    }

    @Override
    public String getType() {
        return DBConstant.T_XU_LY_VPHC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.T_XU_LY_VPHC);
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
}
