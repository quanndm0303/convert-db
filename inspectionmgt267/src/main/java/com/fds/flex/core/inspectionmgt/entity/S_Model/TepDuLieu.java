package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.file.MimeTypes;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TepDuLieu {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_TEP_DU_LIEU;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 4)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("TenTep")
    @Field(value = "TenTep", order = 5)
    public String tenTep = StringPool.BLANK;

    @JsonProperty("DinhDangTep")
    @Field(value = "DinhDangTep", order = 6)
    public String dinhDangTep = StringPool.BLANK;

    @JsonProperty("KichThuocTep")
    @Field(value = "KichThuocTep", order = 7)
    public Long kichThuocTep;

    @JsonProperty("LoaiNguonLuuTru")
    @Field(value = "LoaiNguonLuuTru", order = 8)
    public LoaiNguonLuuTru loaiNguonLuuTru = new LoaiNguonLuuTru();

    @JsonProperty("DuongDanURL")
    @Field(value = "DuongDanURL", order = 9)
    public String duongDanURL = StringPool.BLANK;

    @JsonProperty("MaHoaDuLieu")
    @Field(value = "MaHoaDuLieu", order = 10)
    public String maHoaDuLieu = StringPool.BLANK;

    @Transient
    @JsonProperty("MaTepDuLieu")
    public String maTepDuLieu = StringPool.BLANK;

    @Transient
    @JsonProperty("Ext")
    public String ext = StringPool.BLANK;
    @Transient
    @JsonProperty("isTempFile")
    public boolean isTempFile = false;

    public String getExt() {
        String ext = !Validator.isBlank(this.getDinhDangTep())
                ? MimeTypes.getDefaultExt(this.getDinhDangTep())
                : StringPool.BLANK;

        this.setExt(ext);

        return ext;
    }

    @Getter
    @Setter
    public static class LoaiNguonLuuTru {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_LOAI_NGUON_LUU_TRU;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }
}
