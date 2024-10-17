package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class KhacPhucHauQua {

    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_KHAC_PHUC_HAU_QUA;

    @JsonProperty("IDKhacPhucHauQua")
    @Field(value = "IDKhacPhucHauQua", order = 1)
    public String idKhacPhucHauQua = String.valueOf(System.currentTimeMillis());

    @JsonProperty("BienPhapKhacPhuc")
    @Field(value = "BienPhapKhacPhuc", order = 1)
    public BienPhapKhacPhuc bienPhapKhacPhuc = new BienPhapKhacPhuc();

    @JsonProperty("NoiDungKhacPhuc")
    @Field(value = "NoiDungKhacPhuc", order = 2)
    public String noiDungKhacPhuc = StringPool.BLANK;

    @JsonProperty("SoTienKhacPhuc")
    @Field(value = "SoTienKhacPhuc", order = 3)
    public Long soTienKhacPhuc = null;

    @JsonProperty("ThoiHanKhacPhuc")
    @Field(value = "ThoiHanKhacPhuc", order = 4)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanKhacPhuc = null;

    @JsonProperty("HieuLucThiHanh")
    @Field(value = "HieuLucThiHanh", order = 5)
    public HieuLucThiHanh hieuLucThiHanh = new HieuLucThiHanh();

    @JsonProperty("NgayHoanThanh")
    @Field(value = "NgayHoanThanh", order = 6)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayHoanThanh = null;

    @JsonProperty("NgayHuyThiHanh")
    @Field(value = "NgayHuyThiHanh", order = 7)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayHuyThiHanh = null;

    @JsonProperty("LyDoHuyThiHanh")
    @Field(value = "LyDoHuyThiHanh", order = 8)
    public String lyDoHuyThiHanh = StringPool.BLANK;

    public void setIdKhacPhucHauQua(String idKhacPhucHauQua) {
        if (Validator.isNull(idKhacPhucHauQua)) {
            this.idKhacPhucHauQua = String.valueOf(System.currentTimeMillis());
        } else {
            this.idKhacPhucHauQua = idKhacPhucHauQua;
        }
    }

    @Getter
    @Setter
    public static class BienPhapKhacPhuc {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_BIEN_PHAP_KHAC_PHUC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class HieuLucThiHanh {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_HIEU_LUC_THI_HANH;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

}
