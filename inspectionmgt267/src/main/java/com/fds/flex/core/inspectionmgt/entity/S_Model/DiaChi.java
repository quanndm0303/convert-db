package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
public class DiaChi {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_DIA_CHI;

    @JsonProperty("SoNhaChiTiet")
    @Field(value = "SoNhaChiTiet", order = 1)
    public String soNhaChiTiet = StringPool.BLANK;

    @JsonProperty("TinhThanh")
    @Field(value = "TinhThanh", order = 2)
    public TinhThanh tinhThanh = new TinhThanh();

    @JsonProperty("HuyenQuan")
    @Field(value = "HuyenQuan", order = 3)
    public HuyenQuan huyenQuan = null;

    @JsonProperty("XaPhuong")
    @Field(value = "XaPhuong", order = 4)
    public XaPhuong xaPhuong = null;

    @Setter
    @Getter
    public static class XaPhuong {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_XA_PHUONG;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Setter
    @Getter
    public static class HuyenQuan {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_HUYEN_QUAN;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Setter
    @Getter
    public static class TinhThanh {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_TINH_THANH;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

}
