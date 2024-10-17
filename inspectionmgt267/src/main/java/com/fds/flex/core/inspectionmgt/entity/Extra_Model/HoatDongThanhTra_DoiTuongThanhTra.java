package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class HoatDongThanhTra_DoiTuongThanhTra {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_CA_NHAN_TO_CHUC;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh;

    @JsonProperty("TenGoi")
    @Field(value = "TenGoi", order = 2)
    public String tenGoi = StringPool.BLANK;

    @JsonProperty("LoaiDoiTuongCNTC")
    @Field(value = "LoaiDoiTuongCNTC", order = 3)
    public LoaiDoiTuongCNTC loaiDoiTuongCNTC = new LoaiDoiTuongCNTC();

    @JsonProperty("NoiBoTrongNganh")
    @Field(value = "NoiBoTrongNganh", order = 4)
    public Boolean noiBoTrongNganh = false;

    @JsonProperty("ThongTinTrichNgang")
    @Field(value = "ThongTinTrichNgang", order = 5)
    public String thongTinTrichNgang = StringPool.BLANK;

    @Getter
    @Setter
    public static class LoaiDoiTuongCNTC {

        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_LOAI_DOI_TUONG_CNTC;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }
}
