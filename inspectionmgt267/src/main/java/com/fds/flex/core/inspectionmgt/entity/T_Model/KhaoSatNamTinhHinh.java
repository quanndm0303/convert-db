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
@Document(collection = DBConstant.T_KHAO_SAT_NAM_TINH_HINH)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhaoSatNamTinhHinh extends BaseModel<KhaoSatNamTinhHinh> {

    @JsonInclude
    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 4)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("NhiemVuCongViec")
    @Field(value = "NhiemVuCongViec", order = 5)
    public KhaoSatNamTinhHinh_NhiemVuCongViec nhiemVuCongViec = null;

    @JsonInclude
    @JsonProperty("LoaiCheDoThanhTra")
    @Field(value = "LoaiCheDoThanhTra", order = 6)
    public KhaoSatNamTinhHinh_LoaiCheDoThanhTra loaiCheDoThanhTra = null;

    @JsonInclude
    @JsonProperty("TieuDeKhaoSat")
    @Field(value = "TieuDeKhaoSat", order = 7)
    public String tieuDeKhaoSat = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("CoQuanThucHien")
    @Field(value = "CoQuanThucHien", order = 8)
    public KhaoSatNamTinhHinh_CoQuanThucHien coQuanThucHien = new KhaoSatNamTinhHinh_CoQuanThucHien();

    @JsonInclude
    @JsonProperty("DonViThucHien")
    @Field(value = "DonViThucHien", order = 9)
    public KhaoSatNamTinhHinh_DonViThucHien donViThucHien = new KhaoSatNamTinhHinh_DonViThucHien();

    @JsonInclude
    @JsonProperty("ThoiHanThucHien")
    @Field(value = "ThoiHanThucHien", order = 10)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanThucHien = null;

    @JsonInclude
    @JsonProperty("VanBanKhaoSat")
    @Field(value = "VanBanKhaoSat", order = 11)
    public TepDuLieu vanBanKhaoSat = null;

    @JsonInclude
    @JsonProperty("KetQuaKhaoSat")
    @Field(value = "KetQuaKhaoSat", order = 12)
    public TepDuLieu ketQuaKhaoSat = null;

    @JsonInclude
    @JsonProperty("TrangThaiKhaoSat")
    @Field(value = "TrangThaiKhaoSat", order = 13)
    public KhaoSatNamTinhHinh_TrangThaiKhaoSat trangThaiKhaoSat = new KhaoSatNamTinhHinh_TrangThaiKhaoSat();

    @JsonInclude
    @JsonProperty("HoSoNghiepVu")
    @Field(value = "HoSoNghiepVu", order = 14)
    public KhaoSatNamTinhHinh_HoSoNghiepVu hoSoNghiepVu = null;


    public KhaoSatNamTinhHinh() {
        super(false);
        super.setType(getType());
    }

    public KhaoSatNamTinhHinh(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());
    }

    @Override
    public String getType() {
        return DBConstant.T_KHAO_SAT_NAM_TINH_HINH;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.T_KHAO_SAT_NAM_TINH_HINH);
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
