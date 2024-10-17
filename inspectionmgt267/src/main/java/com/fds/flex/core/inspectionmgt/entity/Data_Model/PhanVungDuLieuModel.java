package com.fds.flex.core.inspectionmgt.entity.Data_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhanVungDuLieuModel {

    @JsonProperty("MaMuc")
    public String maMuc = StringPool.BLANK;

    @JsonProperty("TenMuc")
    public String tenMuc = StringPool.BLANK;

    @JsonProperty("PhanVungCha")
    public PhanVungDuLieu_PhanVungCha phanVungCha = new PhanVungDuLieu_PhanVungCha();

    @JsonProperty("PhanLoaiCacCap")
    public String phanLoaiCacCap = StringPool.BLANK;

    @Getter
    @Setter
    public static class PhanVungDuLieu_PhanVungCha {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_PHAN_VUNG_DU_LIEU;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }
}
