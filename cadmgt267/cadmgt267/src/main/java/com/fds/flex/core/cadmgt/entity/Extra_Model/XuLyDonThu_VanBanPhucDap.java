package com.fds.flex.core.cadmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.entity.S_Model.TepDuLieu;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class XuLyDonThu_VanBanPhucDap {
    @JsonProperty("@type")
    @Field(value = "@type", order = 0)
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

    @JsonProperty("NgayVanBanDen")
    @Field(value = "NgayVanBanDen", order = 4)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayVanBanDen = null;

    @JsonProperty("TepDuLieu")
    @Field(value = "TepDuLieu", order = 5)
    public TepDuLieu tepDuLieu = null;
}
