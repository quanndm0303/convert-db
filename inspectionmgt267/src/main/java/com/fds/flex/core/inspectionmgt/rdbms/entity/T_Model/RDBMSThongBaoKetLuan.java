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
import com.fds.flex.core.inspectionmgt.entity.S_Model.DeXuatKienNghi;
import com.fds.flex.core.inspectionmgt.entity.S_Model.TepDuLieu;
import com.fds.flex.core.inspectionmgt.rdbms.converter.*;
import com.fds.flex.core.inspectionmgt.rdbms.listener.BaoCaoTongHopUpdateListener;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = DBConstant.T_THONG_BAO_KET_LUAN)
@EntityListeners(BaoCaoTongHopUpdateListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSThongBaoKetLuan extends RDBMSBaseModel<RDBMSThongBaoKetLuan> {
    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Column(name = "MaDinhDanh")
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("DoiTuongKetLuan")
    @Column(name = "DoiTuongKetLuan", columnDefinition = "json")
    @Convert(converter = ThongBaoKetLuan_DoiTuongKetLuanConverter.class)
    public ThongBaoKetLuan_DoiTuongKetLuan doiTuongKetLuan = null;

    @JsonInclude
    @JsonProperty("LoaiThongBaoKetLuan")
    @Column(name = "LoaiThongBaoKetLuan", nullable = false, columnDefinition = "json")
    @Convert(converter = ThongBaoKetLuan_LoaiThongBaoKetLuanConverter.class)
    public ThongBaoKetLuan_LoaiThongBaoKetLuan loaiThongBaoKetLuan = new ThongBaoKetLuan_LoaiThongBaoKetLuan();

    @JsonInclude
    @JsonProperty("LoaiGiayKetLuan")
    @Column(name = "LoaiGiayKetLuan", columnDefinition = "json")
    @Convert(converter = ThongBaoKetLuan_LoaiGiayKetLuanConverter.class)
    public ThongBaoKetLuan_LoaiGiayKetLuan loaiGiayKetLuan = null;

    @JsonInclude
    @JsonProperty("TrichYeuVanBan")
    @Column(name = "TrichYeuVanBan", nullable = false, length = 2048)
    public String trichYeuVanBan = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("SoHieuVanBan")
    @Column(name = "SoHieuVanBan", nullable = false)
    public String soHieuVanBan = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("NgayVanBan")
    @Column(name = "NgayVanBan")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayVanBan = null;

    @JsonInclude
    @JsonProperty("CoQuanBanHanh")
    @Column(name = "CoQuanBanHanh", columnDefinition = "json")
    @Convert(converter = ThongBaoKetLuan_CoQuanBanHanhConverter.class)
    public ThongBaoKetLuan_CoQuanBanHanh coQuanBanHanh = null;

    @JsonInclude
    @JsonProperty("TepDuLieu")
    @Column(name = "TepDuLieu", columnDefinition = "json")
    @Convert(converter = TepDuLieuConverter.class)
    public TepDuLieu tepDuLieu = null;

    @JsonInclude
    @JsonProperty("CoQuanQuanLy")
    @Column(name = "CoQuanQuanLy", nullable = false, columnDefinition = "json")
    @Convert(converter = ThongBaoKetLuan_CoQuanQuanLyConverter.class)
    public ThongBaoKetLuan_CoQuanQuanLy coQuanQuanLy = new ThongBaoKetLuan_CoQuanQuanLy();

    @JsonInclude
    @JsonProperty("DonViChuTri")
    @Column(name = "DonViChuTri", columnDefinition = "json")
    @Convert(converter = ThongBaoKetLuan_DonViChuTriConverter.class)
    public ThongBaoKetLuan_DonViChuTri donViChuTri = null;

    @JsonInclude
    @JsonProperty("CanBoTheoDoi")
    @Column(name = "CanBoTheoDoi", nullable = false, columnDefinition = "json")
    @Convert(converter = ThongBaoKetLuan_CanBoTheoDoiConverter.class)
    public ThongBaoKetLuan_CanBoTheoDoi canBoTheoDoi = null;

    @JsonInclude
    @JsonProperty("NgayTheoDoi")
    @Column(name = "NgayTheoDoi")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayTheoDoi = null;

    @JsonInclude
    @JsonProperty("GhiChuKetLuan")
    @Column(name = "GhiChuKetLuan", length = 2048)
    public String ghiChuKetLuan = null;

    @JsonInclude
    @JsonProperty("DeXuatKienNghi")
    @Column(name = "DeXuatKienNghi", nullable = false, columnDefinition = "json")
    @Convert(converter = DeXuatKienNghiConverter.class)
    public List<DeXuatKienNghi> deXuatKienNghi = new ArrayList<>();

    @JsonInclude
    @JsonProperty("XuLyVPHC")
    @Column(name = "XuLyVPHC", nullable = false, columnDefinition = "json")
    @Convert(converter = ThongBaoKetLuan_XuLyVPHCConverter.class)
    public List<ThongBaoKetLuan_XuLyVPHC> xuLyVPHC = new ArrayList<>();

    @JsonInclude
    @JsonProperty("DonDocThucHien")
    @Column(name = "DonDocThucHien")
    public String donDocThucHien = null;

    @JsonInclude
    @JsonProperty("TrangThaiTheoDoi")
    @Column(name = "TrangThaiTheoDoi", nullable = false, columnDefinition = "json")
    @Convert(converter = ThongBaoKetLuan_TrangThaiTheoDoiConverter.class)
    public ThongBaoKetLuan_TrangThaiTheoDoi trangThaiTheoDoi = new ThongBaoKetLuan_TrangThaiTheoDoi();

    @JsonInclude
    @JsonProperty("NgayKetThuc")
    @Column(name = "NgayKetThuc")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayKetThuc = null;

    public RDBMSThongBaoKetLuan() {
        super(false);
        super.setType(getType());
    }

    public RDBMSThongBaoKetLuan(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());
    }

    @Override
    public String getType() {
        return DBConstant.T_THONG_BAO_KET_LUAN;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.T_THONG_BAO_KET_LUAN);
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
