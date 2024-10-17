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
public class KhieuNaiKhoiKien {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_KHIEU_NAI_KHOI_KIEN;

    @JsonProperty("NgayVanBan")
    @Field(value = "NgayVanBan", order = 1)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayVanBan = null;

    @JsonProperty("NoiDungVanBan")
    @Field(value = "NoiDungVanBan", order = 2)
    public String noiDungVanBan = StringPool.BLANK;

    @JsonProperty("KetQuaXuLy")
    @Field(value = "KetQuaXuLy", order = 3)
    public String ketQuaXuLy = StringPool.BLANK;
}
