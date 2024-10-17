package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoatDongThanhTra_HoSoNghiepVu {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_HO_SO_NGHIEP_VU;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("SoDangKy")
    @Field(value = "SoDangKy", order = 2)
    public String soDangKy = null;

    @JsonProperty("TrichYeuHoSo")
    @Field(value = "TrichYeuHoSo", order = 3)
    public String trichYeuHoSo = null;

    @JsonProperty("TrangThaiHoSoNghiepVu")
    @Field(value = "TrangThaiHoSoNghiepVu", order = 4)
    public HoSoNghiepVu_TrangThaiHoSoNghiepVu trangThaiHoSoNghiepVu = null;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HoSoNghiepVu_TrangThaiHoSoNghiepVu {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_TRANG_THAI_HO_SO_NGHIEP_VU;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

}
