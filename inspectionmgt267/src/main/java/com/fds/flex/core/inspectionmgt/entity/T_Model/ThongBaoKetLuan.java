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
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.*;
import com.fds.flex.core.inspectionmgt.entity.S_Model.*;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.BaseModel;
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
@Document(collection = DBConstant.T_THONG_BAO_KET_LUAN)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThongBaoKetLuan extends BaseModel<ThongBaoKetLuan> {
    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 4)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("CoQuanQuanLy")
    @Field(value = "CoQuanQuanLy", order = 17)
    public ThongBaoKetLuan_CoQuanQuanLy coQuanQuanLy = new ThongBaoKetLuan_CoQuanQuanLy();

    @JsonInclude
    @JsonProperty("DonViChuTri")
    @Field(value = "DonViChuTri", order = 18)
    public ThongBaoKetLuan_DonViChuTri donViChuTri = null;

    @JsonInclude
    @JsonProperty("CanBoChiDao")
    @Field(value = "CanBoChiDao", order = 18)
    public ThongBaoKetLuan_CanBoChiDao canBoChiDao = null;

    @JsonInclude
    @JsonProperty("CanBoTheoDoi")
    @Field(value = "CanBoTheoDoi", order = 19)
    public ThongBaoKetLuan_CanBoTheoDoi canBoTheoDoi = null;

    @JsonInclude
    @JsonProperty("DoiTuongKetLuan")
    @Field(value = "DoiTuongKetLuan", order = 5)
    public ThongBaoKetLuan_DoiTuongKetLuan doiTuongKetLuan = null;

    @JsonInclude
    @JsonProperty("LoaiThongBaoKetLuan")
    @Field(value = "LoaiThongBaoKetLuan", order = 6)
    public ThongBaoKetLuan_LoaiThongBaoKetLuan loaiThongBaoKetLuan = new ThongBaoKetLuan_LoaiThongBaoKetLuan();

    @JsonInclude
    @JsonProperty("LoaiGiayKetLuan")
    @Field(value = "LoaiGiayKetLuan", order = 7)
    public ThongBaoKetLuan_LoaiGiayKetLuan loaiGiayKetLuan = null;

    @JsonInclude
    @JsonProperty("CoQuanBanHanh")
    @Field(value = "CoQuanBanHanh", order = 14)
    public ThongBaoKetLuan_CoQuanBanHanh coQuanBanHanh = null;

    @JsonInclude
    @JsonProperty("TrichYeuVanBan")
    @Field(value = "TrichYeuVanBan", order = 8)
    public String trichYeuVanBan = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("VanBanDuThao")
    @Field(value = "VanBanDuThao", order = 9)
    public TepDuLieu vanBanDuThao = null;

    @JsonInclude
    @JsonProperty("ThamDinhDuThao")
    @Field(value = "ThamDinhDuThao", order = 10)
    public ThamDinhDuThao thamDinhDuThao = null;

    @JsonInclude
    @JsonProperty("SoHieuVanBan")
    @Field(value = "SoHieuVanBan", order = 11)
    public String soHieuVanBan = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("NgayVanBan")
    @Field(value = "NgayVanBan", order = 12)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayVanBan = null;

    @JsonInclude
    @JsonProperty("VanBanBanHanh")
    @Field(value = "VanBanBanHanh", order = 13)
    public TepDuLieu vanBanBanHanh = null;

    @JsonInclude
    @JsonProperty("NgayTheoDoi")
    @Field(value = "NgayTheoDoi", order = 20)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayTheoDoi = null;

    @JsonInclude
    @JsonProperty("ThoiHanBaoCao")
    @Field(value = "ThoiHanBaoCao", order = 20)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanBaoCao = null;

    @JsonInclude
    @JsonProperty("GhiChuKetLuan")
    @Field(value = "GhiChuKetLuan", order = 21)
    public String ghiChuKetLuan = null;

    @JsonInclude
    @JsonProperty("HoSoNghiepVu")
    @Field(value = "HoSoNghiepVu", order = 21)
    public ThongBaoKetLuan_HoSoNghiepVu hoSoNghiepVu = null;

    @JsonInclude
    @JsonProperty("DeXuatKienNghi")
    @Field(value = "DeXuatKienNghi", order = 22)
    public List<DeXuatKienNghi> deXuatKienNghi = new ArrayList<>();

    @JsonInclude
    @JsonProperty("XuLyVPHC")
    @Field(value = "XuLyVPHC", order = 23)
    public List<ThongBaoKetLuan_XuLyVPHC> xuLyVPHC = new ArrayList<>();

    @JsonInclude
    @JsonProperty("TrangThaiTheoDoi")
    @Field(value = "TrangThaiTheoDoi", order = 25)
    public ThongBaoKetLuan_TrangThaiTheoDoi trangThaiTheoDoi = new ThongBaoKetLuan_TrangThaiTheoDoi();

    @JsonInclude
    @JsonProperty("NgayKetThuc")
    @Field(value = "NgayKetThuc", order = 26)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayKetThuc = null;

    @JsonInclude
    @JsonProperty("TenDoanKiemTra")
    @Field(value = "TenDoanKiemTra", order = 27)
    public String tenDoanKiemTra = null;

    @JsonInclude
    @JsonProperty("SoQuyetDinhKiemTra")
    @Field(value = "SoQuyetDinhKiemTra", order = 28)
    public String soQuyetDinhKiemTra = null;

    @JsonInclude
    @JsonProperty("NgayQuyetDinhKiemTra")
    @Field(value = "NgayQuyetDinhKiemTra", order = 29)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayQuyetDinhKiemTra = null;

    @JsonInclude
    @JsonProperty("TepDuLieu")
    @Field(value = "TepDuLieu", order = 30)
    public TepDuLieu tepDuLieu = null;

    @JsonInclude
    @JsonProperty("GiayToLuuTruSo")
    @Field(value = "GiayToLuuTruSo", order = 31)
    public ThongBaoKetLuan_GiayToLuuTruSo giayToLuuTruSo = null;

    @JsonInclude
    @JsonProperty("ThuKyDoan")
    @Field(value = "ThuKyDoan", order = 32)
    public List<ThongBaoKetLuan_ThuKyDoan> thuKyDoan = new ArrayList<>();

    @JsonInclude
    @JsonProperty("ThanhVienToKiemTra")
    @Field(value = "ThanhVienToKiemTra", order = 33)
    public List<ThanhVienDoan> thanhVienToKiemTra = new ArrayList<>();

    @JsonInclude
    @JsonProperty("LichKiemTra")
    @Field(value = "LichKiemTra", order = 34)
    public List<LichCongTacDoan> lichKiemTra = new ArrayList<>();


    public ThongBaoKetLuan() {
        super(false);
        super.setType(getType());
    }

    public ThongBaoKetLuan(boolean isUpdate) {
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
