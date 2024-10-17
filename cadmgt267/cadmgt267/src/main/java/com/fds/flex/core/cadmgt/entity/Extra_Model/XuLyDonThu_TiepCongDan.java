package com.fds.flex.core.cadmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class XuLyDonThu_TiepCongDan {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_TIEP_CONG_DAN;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("CheDoTiepCongDan")
    @Field(value = "CheDoTiepCongDan", order = 2)
    public CheDoTiepCongDan cheDoTiepCongDan = new CheDoTiepCongDan();

    @JsonProperty("NguoiDuocTiep")
    @Field(value = "NguoiDuocTiep", order = 2)
    public List<NguoiDuocTiep> nguoiDuocTiep = new ArrayList<>();

    @JsonProperty("CoQuanTiepCongDan")
    @Field(value = "CoQuanTiepCongDan", order = 2)
    public CoQuanTiepCongDan coQuanTiepCongDan = null;

    @Getter
    @Setter
    public static class CheDoTiepCongDan {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_CHE_DO_TIEP_CONG_DAN;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class NguoiDuocTiep {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.T_CA_NHAN_TO_CHUC;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh", order = 1)
        public String maDinhDanh = StringPool.BLANK;

        @JsonProperty("TenGoi")
        @Field(value = "TenGoi", order = 2)
        public String tenGoi = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class CoQuanTiepCongDan {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.T_CO_QUAN_DON_VI;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh", order = 1)
        public String maDinhDanh = StringPool.BLANK;

        @JsonProperty("TenGoi")
        @Field(value = "TenGoi", order = 2)
        public String tenGoi = StringPool.BLANK;
    }
}
