package com.fds.flex.core.inspectionmgt.entity.T_Model;

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
import com.fds.flex.core.inspectionmgt.entity.S_Model.BaoCaoKetQua;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.core.inspectionmgt.constant.NotificationConstant;
import com.fds.flex.modelbuilder.entity.BaseModel;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.*;
import com.fds.flex.core.inspectionmgt.entity.S_Model.LichCongTacDoan;
import com.fds.flex.core.inspectionmgt.entity.S_Model.TepDuLieu;
import com.fds.flex.core.inspectionmgt.entity.S_Model.ThanhVienDoan;
import com.fds.flex.core.inspectionmgt.util.InspectionUtil;
import com.fds.flex.service.context.ServiceContextHolder;
import com.fds.flex.user.context.UserContextHolder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Document(collection = DBConstant.T_HOAT_DONG_THANH_TRA)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoatDongThanhTra extends BaseModel<HoatDongThanhTra> {
    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 4)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("TenHoatDong")
    @Field(value = "TenHoatDong", order = 5)
    public String tenHoatDong = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("LoaiHoatDongThanhTra")
    @Field(value = "LoaiHoatDongThanhTra", order = 6)
    public HoatDongThanhTra_LoaiHoatDongThanhTra loaiHoatDongThanhTra = new HoatDongThanhTra_LoaiHoatDongThanhTra();

    @JsonInclude
    @JsonProperty("LoaiCheDoThanhTra")
    @Field(value = "LoaiCheDoThanhTra", order = 7)
    public HoatDongThanhTra_LoaiCheDoThanhTra loaiCheDoThanhTra = null;

    @JsonInclude
    @JsonProperty("LoaiHinhThucGiamSat")
    @Field(value = "LoaiHinhThucGiamSat", order = 7)
    public HoatDongThanhTra_LoaiHinhThucGiamSat loaiHinhThucGiamSat = null;

    @JsonInclude
    @JsonProperty("KhaoSatNamTinhHinh")
    @Field(value = "KhaoSatNamTinhHinh", order = 8)
    public HoatDongThanhTra_KhaoSatNamTinhHinh khaoSatNamTinhHinh = null;

    @JsonInclude
    @JsonProperty("NhiemVuCongViec")
    @Field(value = "NhiemVuCongViec", order = 8)
    public HoatDongThanhTra_NhiemVuCongViec nhiemVuCongViec = null;

    @JsonInclude
    @JsonProperty("HoatDongGiamSat")
    @Field(value = "HoatDongGiamSat", order = 8)
    public HoatDongThanhTra_HoatDongGiamSat hoatDongGiamSat = null;

    @JsonInclude
    @JsonProperty("DoiTuongGiamSat")
    @Field(value = "DoiTuongGiamSat", order = 8)
    public List<ThanhVienDoan> doiTuongGiamSat = new ArrayList<>();

    @JsonInclude
    @JsonProperty("NamKeHoach")
    @Field(value = "NamKeHoach", order = 9)
    public Integer namKeHoach = 0;

    @JsonInclude
    @JsonProperty("SoQuyetDinh")
    @Field(value = "SoQuyetDinh", order = 9)
    public String soQuyetDinh = null;

    @JsonInclude
    @JsonProperty("NgayQuyetDinh")
    @Field(value = "NgayQuyetDinh", order = 10)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayQuyetDinh = null;

    @JsonInclude
    @JsonProperty("CoQuanBanHanh")
    @Field(value = "CoQuanBanHanh", order = 11)
    public HoatDongThanhTra_CoQuanBanHanh coQuanBanHanh = null;

    @JsonInclude
    @JsonProperty("TepDuLieu")
    @Field(value = "TepDuLieu", order = 12)
    public List<TepDuLieu> tepDuLieu = new ArrayList<>();

    @JsonInclude
    @JsonProperty("GiayToLuuTruSo")
    @Field(value = "GiayToLuuTruSo", order = 13)
    public HoatDongThanhTra_GiayToLuuTruSo giayToLuuTruSo = null;

    @JsonInclude
    @JsonProperty("CoQuanQuanLy")
    @Field(value = "CoQuanQuanLy", order = 14)
    public HoatDongThanhTra_CoQuanQuanLy coQuanQuanLy = new HoatDongThanhTra_CoQuanQuanLy();

    @JsonInclude
    @JsonProperty("DonViChuTri")
    @Field(value = "DonViChuTri", order = 15)
    public HoatDongThanhTra_DonViChuTri donViChuTri = null;

    @JsonInclude
    @JsonProperty("CanBoTheoDoi")
    @Field(value = "CanBoTheoDoi", order = 16)
    public List<HoatDongThanhTra_CanBoTheoDoi> canBoTheoDoi = new ArrayList<>();

    @JsonInclude
    @JsonProperty("LinhVucChuyenNganh")
    @Field(value = "LinhVucChuyenNganh", order = 17)
    public HoatDongThanhTra_LinhVucChuyenNganh linhVucChuyenNganh = null;

    @JsonInclude
    @JsonProperty("NoiDungHoatDong")
    @Field(value = "NoiDungHoatDong", order = 18)
    public String noiDungHoatDong = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("ThoiHanThucHien")
    @Field(value = "ThoiHanThucHien", order = 19)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanThucHien = null;

    @JsonInclude
    @JsonProperty("GiaHanThucHien")
    @Field(value = "GiaHanThucHien", order = 20)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long giaHanThucHien = null;

    @JsonInclude
    @JsonProperty("DoiTuongThanhTra")
    @Field(value = "DoiTuongThanhTra", order = 21)
    public List<HoatDongThanhTra_DoiTuongThanhTra> doiTuongThanhTra = new ArrayList<>();

    @JsonInclude
    @JsonProperty("ThanhVienDoan")
    @Field(value = "ThanhVienDoan", order = 22)
    public List<ThanhVienDoan> thanhVienDoan = new ArrayList<>();

    @JsonInclude
    @JsonProperty("LichCongTacDoan")
    @Field(value = "LichCongTacDoan", order = 23)
    public List<LichCongTacDoan> lichCongTacDoan = new ArrayList<>();

    @JsonInclude
    @JsonProperty("TrangThaiHoatDongThanhTra")
    @Field(value = "TrangThaiHoatDongThanhTra", order = 24)
    public HoatDongThanhTra_TrangThaiHoatDongThanhTra trangThaiHoatDongThanhTra = new HoatDongThanhTra_TrangThaiHoatDongThanhTra();

    @JsonInclude
    @JsonProperty("NgayBatDau")
    @Field(value = "NgayBatDau", order = 25)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayBatDau = null;

    @JsonInclude
    @JsonProperty("NgayKetThuc")
    @Field(value = "NgayKetThuc", order = 25)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayKetThuc = null;

    @JsonInclude
    @JsonProperty("BaoCaoKetQua")
    @Field(value = "BaoCaoKetQua", order = 26)
    public BaoCaoKetQua baoCaoKetQua = null;

    @JsonInclude
    @JsonProperty("HoSoNghiepVu")
    @Field(value = "HoSoNghiepVu", order = 27)
    public HoatDongThanhTra_HoSoNghiepVu hoSoNghiepVu = null;

    @JsonInclude
    @JsonProperty("HuongDanChuyenDe")
    @Field(value = "HuongDanChuyenDe", order = 28)
    public HoatDongThanhTra_HuongDanChuyenDe huongDanChuyenDe = null;

    @JsonInclude
    @JsonProperty("HoSoBaoCao")
    @Field(value = "HoSoBaoCao", order = 29)
    public List<HoatDongThanhTra_HoSoBaoCao> hoSoBaoCao = new ArrayList<>();

    @Transient
    @JsonInclude
    @JsonProperty("CapNhatDuLieu")
    public Boolean capNhatDuLieu = false;

    public HoatDongThanhTra() {
        super(false);
        super.setType(getType());
    }

    public HoatDongThanhTra(boolean isUpdate) {
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

//    public boolean isCapNhatDuLieu() {
//        if (InspectionUtil.checkSuperAdmin() || InspectionUtil.checkAdmin()) {
//            return true;
//        }
//        User user = UserContextHolder.getContext().getUser();
//        if (user.getDanhTinhDienTu().getDoiTuongXacThuc().getType().equals(DBConstant.T_CAN_BO)) {
//            if (this.getThanhVienDoan().isEmpty())
//                return false;
//            for (ThanhVienDoan thanhVienDoan : this.getThanhVienDoan()) {
//                if (thanhVienDoan.getCanBo().getMaDinhDanh().equals(user.getDanhTinhDienTu().getDoiTuongXacThuc().getMaDinhDanh())) {
//                    return true;
//                }
//            }
//        } else {
//            return false;
//        }
//
//        return false;
//    }


}
