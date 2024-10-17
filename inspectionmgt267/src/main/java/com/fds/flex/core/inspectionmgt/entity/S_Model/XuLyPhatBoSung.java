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
public class XuLyPhatBoSung {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_XU_PHAT_BO_SUNG;

    @JsonProperty("IDXuLyPhatBoSung")
    @Field(value = "IDXuLyPhatBoSung", order = 1)
    public String idXuLyPhatBoSung = String.valueOf(System.currentTimeMillis());

    @JsonProperty("HinhThucPhatBoSung")
    @Field(value = "HinhThucPhatBoSung", order = 2)
    public HinhThucPhatBoSung hinhThucPhatBoSung = new HinhThucPhatBoSung();

    @JsonProperty("NoiDungPhatBoSung")
    @Field(value = "NoiDungPhatBoSung", order = 3)
    public String noiDungPhatBoSung;

    @JsonProperty("ThoiHanThucHien")
    @Field(value = "ThoiHanThucHien", order = 4)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanThucHien = null;

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

    public void setIdXuLyPhatBoSung(String idXuLyPhatBoSung) {
        if (Validator.isNull(idXuLyPhatBoSung)) {
            this.idXuLyPhatBoSung = String.valueOf(System.currentTimeMillis());
        } else {
            this.idXuLyPhatBoSung = idXuLyPhatBoSung;
        }
    }

    @Getter
    @Setter
    public static class HinhThucPhatBoSung {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_HINH_THUC_XU_PHAT;

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
        public String type = DBConstant.C_HINH_THUC_XU_PHAT;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }
}
