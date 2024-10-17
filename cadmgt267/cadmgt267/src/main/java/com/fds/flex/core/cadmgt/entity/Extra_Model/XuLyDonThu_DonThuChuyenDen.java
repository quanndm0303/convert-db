package com.fds.flex.core.cadmgt.entity.Extra_Model;

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
public class XuLyDonThu_DonThuChuyenDen {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_VAN_BAN_DEN;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("SoHieuVanBan")
    @Field(value = "SoHieuVanBan", order = 2)
    public String soHieuVanBan = StringPool.BLANK;

    @JsonProperty("NgayVanBan")
    @Field(value = "NgayVanBan", order = 3)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayVanBan = null;

    @JsonProperty("CoQuanBanHanh")
    @Field(value = "CoQuanBanHanh", order = 4)
    public CoQuanBanHanh coQuanBanHanh = new CoQuanBanHanh();

    @Getter
    @Setter
    public static class CoQuanBanHanh {
        @JsonProperty("@type")
        @Field(value = "@type")
        public String type = DBConstant.T_CO_QUAN_DON_VI;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh")
        public String maDinhDanh = StringPool.BLANK;

        @JsonProperty("TenGoi")
        @Field(value = "TenGoi")
        public String tenGoi = StringPool.BLANK;
    }
}
