package com.fds.flex.core.cadmgt.entity.S_Model;

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
public class LichHopDoiThoai {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_LICH_HOP_DOI_THOAI;

    @JsonProperty("IDLich")
    @Field(value = "IDLich", order = 1)
    public String idLich = String.valueOf(System.currentTimeMillis());

    @JsonProperty("ThoiGian")
    @Field(value = "ThoiGian", order = 2)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiGian = null;

    @JsonProperty("DiaDiem")
    @Field(value = "DiaDiem", order = 3)
    public String diaDiem = StringPool.BLANK;

    @JsonProperty("ThanhPhan")
    @Field(value = "ThanhPhan", order = 4)
    public String thanhPhan = null;

    @JsonProperty("NoiDung")
    @Field(value = "NoiDung", order = 5)
    public String noiDung = null;
}
