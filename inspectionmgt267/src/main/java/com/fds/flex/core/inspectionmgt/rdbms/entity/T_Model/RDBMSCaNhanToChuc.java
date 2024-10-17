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
import com.fds.flex.core.inspectionmgt.entity.S_Model.DanhBaLienLac;
import com.fds.flex.core.inspectionmgt.entity.S_Model.DiaChi;
import com.fds.flex.core.inspectionmgt.entity.S_Model.GiayToChungNhan;
import com.fds.flex.core.inspectionmgt.rdbms.converter.*;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = DBConstant.T_CA_NHAN_TO_CHUC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSCaNhanToChuc extends RDBMSBaseModel<RDBMSCaNhanToChuc> {

    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Column(name = "MaDinhDanh", nullable = false)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("TenGoi")
    @Column(name = "TenGoi", nullable = false, length = 1024)
    public String tenGoi = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("LoaiDoiTuongCNTC")
    @Column(name = "LoaiDoiTuongCNTC", nullable = false, columnDefinition = "json")
    @Convert(converter = CaNhanToChuc_LoaiDoiTuongCNTCConverter.class)
    public CaNhanToChuc_LoaiDoiTuongCNTC loaiDoiTuongCNTC = new CaNhanToChuc_LoaiDoiTuongCNTC();

    @JsonInclude
    @JsonProperty("NoiBoTrongNganh")
    @Column(name = "NoiBoTrongNganh")
    public Boolean noiBoTrongNganh = null;

    @JsonInclude
    @JsonProperty("GiayToChungNhan")
    @Column(name = "GiayToChungNhan", columnDefinition = "json")
    @Convert(converter = GiayToChungNhanConverter.class)
    public GiayToChungNhan giayToChungNhan = null;

    @JsonInclude
    @JsonProperty("DiaChi")
    @Column(name = "DiaChi", nullable = false, columnDefinition = "json")
    @Convert(converter = DiaChiConverter.class)
    public DiaChi diaChi = new DiaChi();

    @JsonInclude
    @JsonProperty("DiaChiLienHe")
    @Column(name = "DiaChiLienHe", nullable = false, columnDefinition = "json")
    @Convert(converter = DiaChiConverter.class)
    public DiaChi diaChiLienHe = new DiaChi();

    @JsonInclude
    @JsonProperty("GioiTinh")
    @Column(name = "GioiTinh", columnDefinition = "json")
    @Convert(converter = CaNhanToChuc_GioiTinhConverter.class)
    public CaNhanToChuc_GioiTinh gioiTinh = null;

    @JsonInclude
    @JsonProperty("NgaySinh")
    @Column(name = "NgaySinh")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngaySinh = null;

    @JsonInclude
    @JsonProperty("QuocTich")
    @Column(name = "QuocTich", columnDefinition = "json")
    @Convert(converter = CaNhanToChuc_QuocTichConverter.class)
    public CaNhanToChuc_QuocTich quocTich = null;

    @JsonInclude
    @JsonProperty("CanBoChienSi")
    @Column(name = "CanBoChienSi", columnDefinition = "json")
    @Convert(converter = CaNhanToChuc_CanBoChienSiConverter.class)
    public CaNhanToChuc_CanBoChienSi canBoChienSi = null;

    @JsonInclude
    @JsonProperty("NguoiDaiDien")
    @Column(name = "NguoiDaiDien", nullable = false)
    public String nguoiDaiDien = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("DanhBaLienLac")
    @Column(name = "DanhBaLienLac", columnDefinition = "json")
    @Convert(converter = DanhBaLienLacConverter.class)
    public DanhBaLienLac danhBaLienLac = null;

    @JsonInclude
    @JsonProperty("ViThanhNien")
    @Column(name = "ViThanhNien")
    public Boolean viThanhNien = null;

    @JsonInclude
    @JsonProperty("HoatDongThanhTraKiemTra")
    @Column(name = "HoatDongThanhTraKiemTra", nullable = false, columnDefinition = "json")
    @Convert(converter = CaNhanToChuc_HoatDongThanhTraKiemTraConverter.class)
    public List<CaNhanToChuc_HoatDongThanhTraKiemTra> hoatDongThanhTraKiemTra = new ArrayList<>();

    @JsonInclude
    @JsonProperty("ThongBaoKetLuan")
    @Column(name = "ThongBaoKetLuan", nullable = false, columnDefinition = "json")
    @Convert(converter = CaNhanToChuc_ThongBaoKetLuanConverter.class)
    public List<CaNhanToChuc_ThongBaoKetLuan> thongBaoKetLuan = new ArrayList<>();

    @JsonInclude
    @JsonProperty("VuViecDonThu")
    @Column(name = "VuViecDonThu", nullable = false, columnDefinition = "json")
    @Convert(converter = CaNhanToChuc_VuViecDonThuConverter.class)
    public List<CaNhanToChuc_VuViecDonThu> vuViecDonThu = new ArrayList<>();

    public RDBMSCaNhanToChuc() {
        super(false);
        super.setType(getType());
    }

    public RDBMSCaNhanToChuc(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());
    }

    @Override
    public String getType() {
        return DBConstant.T_CA_NHAN_TO_CHUC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.T_CA_NHAN_TO_CHUC);
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
