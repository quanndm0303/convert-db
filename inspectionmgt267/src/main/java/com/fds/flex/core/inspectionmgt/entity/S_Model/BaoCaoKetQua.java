package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.core.inspectionmgt.entity.S_Model.TepDuLieu;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class BaoCaoKetQua {

    @JsonProperty("@type")
    @Field(value = "@type", order = 0)
    public String type = DBConstant.S_TAI_LIEU_HUONG_DAN;

    @JsonProperty("NgayBaoCao")
    @Field(value = "NgayBaoCao", order = 2)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayBaoCao = null;

    @JsonProperty("SoVanBan")
    @Field(value = "SoVanBan", order = 3)
    public String soVanBan = null;

    @JsonProperty("TepDuLieu")
    @Field(value = "TepDuLieu", order = 4)
    public TepDuLieu tepDuLieu = null;
}
