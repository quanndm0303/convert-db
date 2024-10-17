package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class ChuyenCoQuanDieuTra {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_CHUYEN_CO_QUAN_DIEU_TRA;

    @JsonProperty("NgayVanBan")
    @Field(value = "NgayVanBan", order = 1)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayVanBan = null;

    @JsonProperty("SoVanBan")
    @Field(value = "SoVanBan", order = 2)
    public String soVanBan = StringPool.BLANK;

    @JsonProperty("CoQuanDieuTra")
    @Field(value = "CoQuanDieuTra", order = 3)
    public CoQuanDieuTra coQuanDieuTra = new CoQuanDieuTra();

    @JsonProperty("KhoiToHinhSu")
    @Field(value = "KhoiToHinhSu", order = 3)
    public Boolean khoiToHinhSu = null;

    @Getter
    @Setter
    public static class CoQuanDieuTra {

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
