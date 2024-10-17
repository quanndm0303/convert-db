package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class NoiDungVPHC {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_NOI_DUNG_VPHC;

    @JsonProperty("IDNoiDungVPHC")
    @Field(value = "IDNoiDungVPHC", order = 1)
    public String idNoiDungVPHC = String.valueOf(System.currentTimeMillis());

    @JsonProperty("NghiDinhXPVPHC")
    @Field(value = "NghiDinhXPVPHC", order = 2)
    public NghiDinhXPVPHC nghiDinhXPVPHC = new NghiDinhXPVPHC();

    @JsonProperty("NhomHanhViVPHC")
    @Field(value = "NhomHanhViVPHC", order = 3)
    public NhomHanhViVPHC nhomHanhViVPHC = new NhomHanhViVPHC();

    @JsonProperty("HanhViVPHC")
    @Field(value = "HanhViVPHC", order = 4)
    public HanhViVPHC hanhViVPHC = new HanhViVPHC();

    @JsonProperty("MoTaHanhViVPHC")
    @Field(value = "MoTaHanhViVPHC", order = 5)
    public String moTaHanhViVPHC = StringPool.BLANK;

    public void setIdNoiDungVPHC(String idNoiDungVPHC) {
        if (Validator.isNull(idNoiDungVPHC)) {
            this.idNoiDungVPHC = String.valueOf(System.currentTimeMillis());
        } else {
            this.idNoiDungVPHC = idNoiDungVPHC;
        }
    }

    @Setter
    @Getter
    public static class NghiDinhXPVPHC {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_NGHI_DINH_XPVPHC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Setter
    @Getter
    public static class HanhViVPHC {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_HANH_VI_VPHC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Setter
    @Getter
    public static class NhomHanhViVPHC {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_NHOM_HANH_VI_VPHC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }
}
