package com.fds.flex.core.inspectionmgt.entity.T_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.BaseModel;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.*;
import com.fds.flex.core.inspectionmgt.entity.S_Model.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document(collection = DBConstant.T_XU_LY_VPHC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class XuLyVPHC extends BaseModel<XuLyVPHC> {

    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 4)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("CoQuanQuanLy")
    @Field(value = "CoQuanQuanLy", order = 5)
    public XuLyVPHC_CoQuanQuanLy coQuanQuanLy = new XuLyVPHC_CoQuanQuanLy();

    @JsonInclude
    @JsonProperty("DonViChuTri")
    @Field(value = "DonViChuTri", order = 6)
    public XuLyVPHC_DonViChuTri donViChuTri = null;

    @JsonInclude
    @JsonProperty("CanBoTheoDoi")
    @Field(value = "CanBoTheoDoi", order = 7)
    public List<XuLyVPHC_CanBoTheoDoi> canBoTheoDoi = new ArrayList<>();

    @JsonInclude
    @JsonProperty("NgayLapBienBan")
    @Field(value = "NgayLapBienBan", order = 8)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayLapBienBan = null;

    @JsonInclude
    @JsonProperty("SoBienBanVPHC")
    @Field(value = "SoBienBanVPHC", order = 9)
    public String soBienBanVPHC = null;

    @JsonInclude
    @JsonProperty("TiepNhanVuViec")
    @Field(value = "TiepNhanVuViec", order = 10)
    public TiepNhanVuViec tiepNhanVuViec = null;

    @JsonInclude
    @JsonProperty("DoiTuongVPHC")
    @Field(value = "DoiTuongVPHC", order = 11)
    public XuLyVPHC_DoiTuongVPHC doiTuongVPHC = new XuLyVPHC_DoiTuongVPHC();

    @JsonInclude
    @JsonProperty("NoiDungVPHC")
    @Field(value = "NoiDungVPHC", order = 12)
    public List<NoiDungVPHC> noiDungVPHC = new ArrayList<>();

    @JsonInclude
    @JsonProperty("TinhTietTangNang")
    @Field(value = "TinhTietTangNang", order = 13)
    public String tinhTietTangNang = null;

    @JsonInclude
    @JsonProperty("TinhTietGiamNhe")
    @Field(value = "TinhTietGiamNhe", order = 14)
    public String tinhTietGiamNhe = null;

    @JsonInclude
    @JsonProperty("SoQuyetDinh")
    @Field(value = "SoQuyetDinh", order = 15)
    public String soQuyetDinh = null;

    @JsonInclude
    @JsonProperty("NgayQuyetDinh")
    @Field(value = "NgayQuyetDinh", order = 16)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayQuyetDinh = null;

    @JsonInclude
    @JsonProperty("NgayHieuLuc")
    @Field(value = "NgayHieuLuc", order = 16)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayHieuLuc = null;

    @JsonInclude
    @JsonProperty("CoQuanQuyetDinh")
    @Field(value = "CoQuanQuyetDinh", order = 17)
    public XuLyVPHC_CoQuanQuyetDinh coQuanQuyetDinh = null;

    @JsonInclude
    @JsonProperty("HinhThucXuLyChinh")
    @Field(value = "HinhThucXuLyChinh", order = 18)
    public XuLyVPHC_HinhThucXuLyChinh hinhThucXuLyChinh = new XuLyVPHC_HinhThucXuLyChinh();

    @JsonInclude
    @JsonProperty("NoiDungLyDoXuLy")
    @Field(value = "NoiDungLyDoXuLy", order = 19)
    public String noiDungLyDoXuLy = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("GiayToTamGiu")
    @Field(value = "GiayToTamGiu", order = 19)
    public String giayToTamGiu = null;

    @JsonInclude
    @JsonProperty("BienPhapNganChanDamBao")
    @Field(value = "BienPhapNganChanDamBao", order = 19)
    public String bienPhapNganChanDamBao = null;

    @JsonInclude
    @JsonProperty("NgayNhanQuyetDinh")
    @Field(value = "NgayNhanQuyetDinh", order = 16)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayNhanQuyetDinh = null;

    @JsonInclude
    @JsonProperty("TruongHopPhatTien")
    @Field(value = "TruongHopPhatTien", order = 20)
    public TruongHopPhatTien truongHopPhatTien = null;

    @JsonInclude
    @JsonProperty("XuLyPhatBoSung")
    @Field(value = "XuLyPhatBoSung", order = 21)
    public List<XuLyPhatBoSung> xuLyPhatBoSung = new ArrayList<>();

    @JsonInclude
    @JsonProperty("KhacPhucHauQua")
    @Field(value = "KhacPhucHauQua", order = 22)
    public List<KhacPhucHauQua> khacPhucHauQua = new ArrayList<>();

    @JsonInclude
    @JsonProperty("ThoiHanChapHanh")
    @Field(value = "ThoiHanChapHanh", order = 23)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanChapHanh = null;

    @JsonInclude
    @JsonProperty("CuongCheThiHanh")
    @Field(value = "CuongCheThiHanh", order = 24)
    public List<CuongCheThiHanh> cuongCheThiHanh = new ArrayList<>();

    @JsonInclude
    @JsonProperty("TamDinhChiThiHanh")
    @Field(value = "TamDinhChiThiHanh", order = 25)
    public List<TamDinhChiThiHanh> tamDinhChiThiHanh = new ArrayList<>();

    @JsonInclude
    @JsonProperty("KhieuNaiKhoiKien")
    @Field(value = "KhieuNaiKhoiKien", order = 26)
    public List<KhieuNaiKhoiKien> khieuNaiKhoiKien = new ArrayList<>();

    @JsonInclude
    @JsonProperty("ChuyenCoQuanThiHanh")
    @Field(value = "ChuyenCoQuanThiHanh", order = 27)
    public XuLyVPHC_ChuyenCoQuanThiHanh chuyenCoQuanThiHanh = null;

    @JsonInclude
    @JsonProperty("ChuyenCoQuanDieuTra")
    @Field(value = "ChuyenCoQuanDieuTra", order = 28)
    public ChuyenCoQuanDieuTra chuyenCoQuanDieuTra = null;

    @JsonInclude
    @JsonProperty("TrangThaiXuLyVPHC")
    @Field(value = "TrangThaiXuLyVPHC", order = 29)
    public XuLyVPHC_TrangThaiXuLyVPHC trangThaiXuLyVPHC = new XuLyVPHC_TrangThaiXuLyVPHC();

    @JsonInclude
    @JsonProperty("NgayKetThuc")
    @Field(value = "NgayKetThuc", order = 30)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayKetThuc = null;

    @JsonInclude
    @JsonProperty("LyDoHuyThiHanh")
    @Field(value = "LyDoHuyThiHanh", order = 31)
    public String lyDoHuyThiHanh = null;

    @JsonInclude
    @JsonProperty("ThanhPhanGiayTo")
    @Field(value = "ThanhPhanGiayTo", order = 32)
    public List<ThanhPhanGiayTo> thanhPhanGiayTo = new ArrayList<>();

    public XuLyVPHC() {
        super(false);
        super.setType(getType());
    }

    public XuLyVPHC(boolean isUpdate) {
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
            String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;
            super.setPrimKey(primKey);
            return primKey;
        }
        return super.getPrimKey();
    }

    @Override
    public void set_id(ObjectId _id) {
        super.set_id(_id);
        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;
        super.setPrimKey(primKey);
    }
}
