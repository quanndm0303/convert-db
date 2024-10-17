package com.fds.flex.core.cadmgt.rdbms.entity.T_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.datetime.DateTimeUtils;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.entity.C_Model.LoaiVuViecDonThu;
import com.fds.flex.core.cadmgt.entity.Extra_Model.*;
import com.fds.flex.core.cadmgt.entity.S_Model.*;
import com.fds.flex.core.cadmgt.util.CADMgtUtil;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = DBConstant.T_VU_VIEC_DON_THU)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSVuViecDonThu extends RDBMSBaseCategory<RDBMSVuViecDonThu> {

    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 4)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("NgayTiepNhan")
    @Field(value = "NgayTiepNhan", order = 5)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayTiepNhan = null;

    @JsonInclude
    @JsonProperty("NgayVietDon")
    @Field(value = "NgayVietDon", order = 6)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayVietDon = null;

    @JsonInclude
    @JsonProperty("CoQuanQuanLy")
    @Field(value = "CoQuanQuanLy", order = 7)
    public VuViecDonThu_CoQuanQuanLy coQuanQuanLy = new VuViecDonThu_CoQuanQuanLy();

    @JsonInclude
    @JsonProperty("LanhDaoQuanLy")
    @Field(value = "LanhDaoQuanLy", order = 8)
    public VuViecDonThu_LanhDaoQuanLy lanhDaoQuanLy = null;

    @JsonInclude
    @JsonProperty("DonViTheoDoi")
    @Field(value = "DonViTheoDoi", order = 9)
    public VuViecDonThu_DonViTheoDoi donViTheoDoi = null;

    @JsonInclude
    @JsonProperty("LanhDaoTheoDoi")
    @Field(value = "LanhDaoTheoDoi", order = 10)
    public VuViecDonThu_LanhDaoTheoDoi lanhDaoTheoDoi = null;

    @JsonInclude
    @JsonProperty("CanBoXuLy")
    @Field(value = "CanBoXuLy", order = 11)
    public List<VuViecDonThu_CanBoXuLy> canBoXuLy = new ArrayList<>();

    @JsonInclude
    @JsonProperty("LoaiVuViecDonThu")
    @Field(value = "LoaiVuViecDonThu", order = 12)
    public VuViecDonThu_LoaiVuViecDonThu loaiVuViecDonThu = new VuViecDonThu_LoaiVuViecDonThu();

    @JsonInclude
    @JsonProperty("LoaiChiTietVuViec")
    @Field(value = "LoaiChiTietVuViec", order = 13)
    public VuViecDonThu_LoaiChiTietVuViec loaiChiTietVuViec = null;

    @JsonInclude
    @JsonProperty("DoiTuongVuViec")
    @Field(value = "DoiTuongVuViec", order = 14)
    public List<VuViecDonThu_DoiTuongVuViec> doiTuongVuViec = new ArrayList<>();

    @JsonInclude
    @JsonProperty("NoiDungVuViec")
    @Field(value = "NoiDungVuViec", order = 15)
    public String noiDungVuViec = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("KienNghiCongDan")
    @Field(value = "KienNghiCongDan", order = 16)
    public String kienNghiCongDan = null;

    @JsonInclude
    @JsonProperty("SoDonThu")
    @Field(value = "SoDonThu", order = 17)
    public Integer soDonThu = null;

    @JsonInclude
    @JsonProperty("LanTiepThu")
    @Field(value = "LanTiepThu", order = 18)
    public Integer lanTiepThu = null;

    @JsonInclude
    @JsonProperty("LoaiPhucTapKeoDai")
    @Field(value = "LoaiPhucTapKeoDai", order = 19)
    public VuViecDonThu_LoaiPhucTapKeoDai loaiPhucTapKeoDai = null;

    @JsonInclude
    @JsonProperty("LinhVucChuyenNganh")
    @Field(value = "LinhVucChuyenNganh", order = 20)
    public VuViecDonThu_LinhVucChuyenNganh linhVucChuyenNganh = null;

    @JsonProperty("DonThuDinhKem")
    @Field(value = "DonThuDinhKem", order = 21)
    public List<TepDuLieu> donThuDinhKem = new ArrayList<>();

    @JsonProperty("KetQuaGiaiQuyetTruoc")
    @Field(value = "KetQuaGiaiQuyetTruoc", order = 22)
    public String ketQuaGiaiQuyetTruoc = null;

    @JsonInclude
    @JsonProperty("DiaBanXuLy")
    @Field(value = "DiaBanXuLy", order = 23)
    public VuViecDonThu_DiaBanXuLy diaBanXuLy = null;

    @JsonInclude
    @JsonProperty("CoQuanThamQuyen")
    @Field(value = "CoQuanThamQuyen", order = 24)
    public VuViecDonThu_CoQuanThamQuyen coQuanThamQuyen = null;

    @JsonInclude
    @JsonProperty("LanGiaiQuyet")
    @Field(value = "LanGiaiQuyet", order = 25)
    public Integer lanGiaiQuyet = null;

    @JsonInclude
    @JsonProperty("ThoiHanThucHien")
    @Field(value = "ThoiHanThucHien", order = 26)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanThucHien = null;

    @JsonInclude
    @JsonProperty("NgayTamDinhChi")
    @Field(value = "NgayTamDinhChi", order = 27)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayTamDinhChi = null;

    @JsonInclude
    @JsonProperty("PhieuDeXuat")
    @Field(value = "PhieuDeXuat", order = 28)
    public PhieuDeXuat phieuDeXuat = null;

    @JsonInclude
    @JsonProperty("KiemTraThuLy")
    @Field(value = "KiemTraThuLy", order = 29)
    public KiemTraThuLy kiemTraThuLy = null;

    @JsonInclude
    @JsonProperty("DoanToXacMinh")
    @Field(value = "DoanToXacMinh", order = 30)
    public DoanToXacMinh doanToXacMinh = null;

    @JsonInclude
    @JsonProperty("LichHopDoiThoai")
    @Field(value = "LichHopDoiThoai", order = 31)
    public List<LichHopDoiThoai> lichHopDoiThoai = new ArrayList<>();

    @JsonInclude
    @JsonProperty("ThongBaoKetLuan")
    @Field(value = "ThongBaoKetLuan", order = 32)
    public VuViecDonThu_ThongBaoKetLuan thongBaoKetLuan = null;

    @JsonInclude
    @JsonProperty("PhanTichKetQuaKNTC")
    @Field(value = "PhanTichKetQuaKNTC", order = 33)
    public VuViecDonThu_PhanTichKetQuaKNTC phanTichKetQuaKNTC = null;

    @JsonInclude
    @JsonProperty("KienNghiXuLyKNTC")
    @Field(value = "KienNghiXuLyKNTC", order = 34)
    public VuViecDonThu_KienNghiXuLyKNTC kienNghiXuLyKNTC = null;

    @JsonInclude
    @JsonProperty("NoiDungKienNghi")
    @Field(value = "NoiDungKienNghi", order = 35)
    public String noiDungKienNghi = null;

    @JsonInclude
    @JsonProperty("TinhTrangXuLyVuViec")
    @Field(value = "TinhTrangXuLyVuViec", order = 37)
    public VuViecDonThu_TinhTrangXuLyVuViec tinhTrangXuLyVuViec = new VuViecDonThu_TinhTrangXuLyVuViec();

    @JsonInclude
    @JsonProperty("NgayKetThuc")
    @Field(value = "NgayKetThuc", order = 38)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayKetThuc = null;

    @JsonInclude
    @JsonProperty("KhoiKienHanhChinh")
    @Field(value = "KhoiKienHanhChinh", order = 39)
    public VuViecDonThu_KhoiKienHanhChinh khoiKienHanhChinh = null;

    @JsonInclude
    @JsonProperty("GhiChuVuViec")
    @Field(value = "GhiChuVuViec", order = 40)
    public String ghiChuVuViec = null;

    @JsonInclude
    @JsonProperty("HoSoNghiepVu")
    @Field(value = "HoSoNghiepVu", order = 41)
    public VuViecDonThu_HoSoNghiepVu hoSoNghiepVu = null;

    @JsonInclude
    @JsonProperty("XuLyDonThu")
    @Field(value = "XuLyDonThu", order = 42)
    public VuViecDonThu_XuLyDonThu xuLyDonThu = new VuViecDonThu_XuLyDonThu();

    @Transient
    @JsonProperty("ThoiHanXuLy")
    public Long thoiHanXuLy = 0L;


    public RDBMSVuViecDonThu() {
        super(false);
        super.setType(getType());
    }

    public RDBMSVuViecDonThu(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());
    }

    @Override
    public String getType() {
        return DBConstant.T_VU_VIEC_DON_THU;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.T_VU_VIEC_DON_THU);
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


    public Long getThoiHanXuLy() {
        if (Validator.isNull(this.getThoiHanThucHien())) {
            return thoiHanXuLy;
        }

        Date now = new Date();
        Date thoiHanThucHien = new Date(this.getThoiHanThucHien());
        long soNgayConLaiCuaBuoc = DateTimeUtils.numberOfDayFrom2Date(now, thoiHanThucHien, false);
        if (!this.getLoaiVuViecDonThu().getMaMuc().equals(LoaiVuViecDonThu.Loai.KhieuNai.getMaMuc()))
            soNgayConLaiCuaBuoc = DateTimeUtils.numberOfDayFrom2Date(now, thoiHanThucHien, true);

        long soNgayNghiLe = 0;
        List<String> rangeDate = DateTimeUtils.generateDateRange(now, thoiHanThucHien, DateTimeUtils._VN_DATE_TIME);
        for (String date : rangeDate) {
            if (CADMgtUtil.ngayNghiLe.containsKey(date)) {
                soNgayNghiLe++;
            }
        }

        return soNgayConLaiCuaBuoc - soNgayNghiLe + 1;
    }
}
