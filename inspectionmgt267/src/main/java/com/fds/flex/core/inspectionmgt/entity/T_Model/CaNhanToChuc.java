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
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.BaseModel;
import com.fds.flex.core.inspectionmgt.entity.S_Model.DanhBaLienLac;
import com.fds.flex.core.inspectionmgt.entity.S_Model.DiaChi;
import com.fds.flex.core.inspectionmgt.entity.S_Model.GiayToChungNhan;
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
@Document(collection = DBConstant.T_CA_NHAN_TO_CHUC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaNhanToChuc extends BaseModel<CaNhanToChuc> {

    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 4)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("TenGoi")
    @Field(value = "TenGoi", order = 5)
    public String tenGoi = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("LoaiDoiTuongCNTC")
    @Field(value = "LoaiDoiTuongCNTC", order = 6)
    public CaNhanToChuc_LoaiDoiTuongCNTC loaiDoiTuongCNTC = new CaNhanToChuc_LoaiDoiTuongCNTC();

    @JsonInclude
    @JsonProperty("NoiBoTrongNganh")
    @Field(value = "NoiBoTrongNganh", order = 7)
    public Boolean noiBoTrongNganh = null;

    @JsonInclude
    @JsonProperty("GiayToChungNhan")
    @Field(value = "GiayToChungNhan", order = 8)
    public GiayToChungNhan giayToChungNhan = null;

    @JsonInclude
    @JsonProperty("DiaChi")
    @Field(value = "DiaChi", order = 9)
    public DiaChi diaChi = new DiaChi();

    @JsonInclude
    @JsonProperty("NgaySinh")
    @Field(value = "NgaySinh", order = 13)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngaySinh = null;

    @JsonInclude
    @JsonProperty("QuocTich")
    @Field(value = "QuocTich", order = 14)
    public CaNhanToChuc_QuocTich quocTich = null;

    @JsonInclude
    @JsonProperty("CanBoChienSi")
    @Field(value = "CanBoChienSi", order = 15)
    public CaNhanToChuc_CanBoChienSi canBoChienSi = null;

    @JsonInclude
    @JsonProperty("NguoiDaiDien")
    @Field(value = "NguoiDaiDien", order = 16)
    public String nguoiDaiDien = null;

    @JsonInclude
    @JsonProperty("DanhBaLienLac")
    @Field(value = "DanhBaLienLac", order = 17)
    public DanhBaLienLac danhBaLienLac = null;

    @JsonInclude
    @JsonProperty("ViThanhNien")
    @Field(value = "ViThanhNien", order = 18)
    public Boolean viThanhNien = null;

    @JsonInclude
    @JsonProperty("HoatDongThanhTraKiemTra")
    @Field(value = "HoatDongThanhTraKiemTra", order = 19)
    public List<CaNhanToChuc_HoatDongThanhTraKiemTra> hoatDongThanhTraKiemTra = new ArrayList<>();

    @JsonInclude
    @JsonProperty("ThongBaoKetLuan")
    @Field(value = "ThongBaoKetLuan", order = 20)
    public List<CaNhanToChuc_ThongBaoKetLuan> thongBaoKetLuan = new ArrayList<>();

    @JsonInclude
    @JsonProperty("VuViecDonThu")
    @Field(value = "VuViecDonThu", order = 21)
    public List<CaNhanToChuc_VuViecDonThu> vuViecDonThu = new ArrayList<>();

    @JsonInclude
    @JsonProperty("XuLyDonThu")
    @Field(value = "XuLyDonThu", order = 22)
    public List<CaNhanToChuc_XuLyDonThu> xuLyDonThu = new ArrayList<>();

    public CaNhanToChuc() {
        super(false);
        super.setType(getType());
    }

    public CaNhanToChuc(boolean isUpdate) {
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
