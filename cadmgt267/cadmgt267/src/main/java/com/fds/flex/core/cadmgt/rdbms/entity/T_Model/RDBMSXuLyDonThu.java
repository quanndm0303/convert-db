package com.fds.flex.core.cadmgt.rdbms.entity.T_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.entity.Extra_Model.*;
import com.fds.flex.core.cadmgt.entity.S_Model.PhieuDeXuat;
import com.fds.flex.core.cadmgt.entity.S_Model.PhieuDeXuatXuLyVuViec;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = DBConstant.T_XU_LY_DON_THU)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSXuLyDonThu extends RDBMSBaseCategory<RDBMSXuLyDonThu> {
    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 4)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("MaDonThu")
    @Field(value = "MaDonThu", order = 5)
    public String maDonThu = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("NgayTiepNhan")
    @Field(value = "NgayTiepNhan", order = 6)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayTiepNhan = null;

    @JsonInclude
    @JsonProperty("TenTrenBiThu")
    @Field(value = "TenTrenBiThu", order = 7)
    public String tenTrenBiThu = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("CoQuanQuanLy")
    @Field(value = "CoQuanQuanLy", order = 8)
    public XuLyDonThu_CoQuanQuanLy coQuanQuanLy = new XuLyDonThu_CoQuanQuanLy();

    @JsonInclude
    @JsonProperty("LanhDaoQuanLy")
    @Field(value = "LanhDaoQuanLy", order = 9)
    public XuLyDonThu_LanhDaoQuanLy lanhDaoQuanLy = null;

    @JsonInclude
    @JsonProperty("DonViTheoDoi")
    @Field(value = "DonViTheoDoi", order = 10)
    public XuLyDonThu_DonViTheoDoi donViTheoDoi = null;

    @JsonInclude
    @JsonProperty("LanhDaoTheoDoi")
    @Field(value = "LanhDaoTheoDoi", order = 11)
    public XuLyDonThu_LanhDaoTheoDoi lanhDaoTheoDoi = null;

    @JsonInclude
    @JsonProperty("CanBoXuLy")
    @Field(value = "CanBoXuLy", order = 12)
    public List<XuLyDonThu_CanBoXuLy> canBoXuLy = new ArrayList<>();

    @JsonInclude
    @JsonProperty("LoaiDoiTuongDungTen")
    @Field(value = "LoaiDoiTuongDungTen", order = 13)
    public XuLyDonThu_LoaiDoiTuongDungTen loaiDoiTuongDungTen = new XuLyDonThu_LoaiDoiTuongDungTen();

    @JsonInclude
    @JsonProperty("NguoiVietDon")
    @Field(value = "NguoiVietDon", order = 14)
    public List<XuLyDonThu_NguoiVietDon> nguoiVietDon = new ArrayList<>();

    @JsonInclude
    @JsonProperty("HinhThucNhanDonThu")
    @Field(value = "HinhThucNhanDonThu", order = 15)
    public XuLyDonThu_HinhThucNhanDonThu hinhThucNhanDonThu = new XuLyDonThu_HinhThucNhanDonThu();

    @JsonInclude
    @JsonProperty("TiepCongDan")
    @Field(value = "TiepCongDan", order = 16)
    public XuLyDonThu_TiepCongDan tiepCongDan = null;

    @JsonInclude
    @JsonProperty("LanhDaoChuyen")
    @Field(value = "LanhDaoChuyen", order = 17)
    public XuLyDonThu_LanhDaoChuyen lanhDaoChuyen = null;

    @JsonInclude
    @JsonProperty("DonThuChuyenDen")
    @Field(value = "DonThuChuyenDen", order = 18)
    public XuLyDonThu_DonThuChuyenDen donThuChuyenDen = null;

    @JsonInclude
    @JsonProperty("VuViecDonThu")
    @Field(value = "VuViecDonThu", order = 19)
    public XuLyDonThu_VuViecDonThu vuViecDonThu = null;

    @JsonInclude
    @JsonProperty("TinhTrangXuLyDonThu")
    @Field(value = "TinhTrangXuLyDonThu", order = 20)
    public XuLyDonThu_TinhTrangXuLyDonThu tinhTrangXuLyDonThu = new XuLyDonThu_TinhTrangXuLyDonThu();

    @JsonInclude
    @JsonProperty("NgayNhanDon")
    @Field(value = "NgayNhanDon", order = 21)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayNhanDon = null;

    @JsonInclude
    @JsonProperty("DonViGiaiQuyet")
    @Field(value = "DonViGiaiQuyet", order = 22)
    public XuLyDonThu_DonViGiaiQuyet donViGiaiQuyet = null;

    @JsonInclude
    @JsonProperty("CanBoGiaiQuyet")
    @Field(value = "CanBoGiaiQuyet", order = 23)
    public List<XuLyDonThu_CanBoGiaiQuyet> canBoGiaiQuyet = new ArrayList<>();

    @JsonInclude
    @JsonProperty("TrangThaiGiaiQuyetDon")
    @Field(value = "TrangThaiGiaiQuyetDon", order = 24)
    public XuLyDonThu_TrangThaiGiaiQuyetDon trangThaiGiaiQuyetDon = null;

    @JsonInclude
    @JsonProperty("NgayKetThuc")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    @Field(value = "NgayKetThuc", order = 25)
    public Long ngayKetThuc = null;

    @JsonInclude
    @JsonProperty("PhieuDeXuatXuLyVuViec")
    @Field(value = "PhieuDeXuatXuLyVuViec", order = 26)
    public PhieuDeXuatXuLyVuViec phieuDeXuatXuLyVuViec = null;

    @JsonInclude
    @JsonProperty("PhieuDeXuat")
    @Field(value = "PhieuDeXuat", order = 27)
    public List<PhieuDeXuat> phieuDeXuat = new ArrayList<>();

    @JsonInclude
    @JsonProperty("VanBanChuyenDon")
    @Field(value = "VanBanChuyenDon", order = 28)
    public List<XuLyDonThu_VanBanChuyenDon> vanBanChuyenDon = new ArrayList<>();

    @JsonInclude
    @JsonProperty("VanBanPhucDap")
    @Field(value = "VanBanPhucDap", order = 29)
    public List<XuLyDonThu_VanBanPhucDap> vanBanPhucDap = new ArrayList<>();

    @JsonInclude
    @JsonProperty("HoSoNghiepVu")
    @Field(value = "HoSoNghiepVu", order = 30)
    public XuLyDonThu_HoSoNghiepVu hoSoNghiepVu = null;

    @JsonInclude
    @JsonProperty("YKienChiDao")
    @Field(value = "YKienChiDao", order = 31)
    public String yKienChiDao = null;

    public RDBMSXuLyDonThu() {
        super(false);
        super.setType(getType());
    }

    public RDBMSXuLyDonThu(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());
    }

    @Override
    public String getType() {
        return DBConstant.T_XU_LY_DON_THU;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.T_XU_LY_DON_THU);
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
