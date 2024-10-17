package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ThamDinhDuThao {

    @JsonProperty("@type")
    @Field(value = "@type", order = 0)
    public String type = DBConstant.S_THAM_DINH_DU_THAO;

    @JsonProperty("NgayYeuCau")
    @Field(value = "NgayYeuCau", order = 1)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayYeuCau = null;

    @JsonProperty("NoiDungYeuCau")
    @Field(value = "NoiDungYeuCau", order = 2)
    public String noiDungYeuCau = StringPool.BLANK;

    @JsonProperty("NgayThamDinh")
    @Field(value = "NgayThamDinh", order = 3)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayThamDinh = null;

    @JsonProperty("BaoCaoThamDinh")
    @Field(value = "BaoCaoThamDinh", order = 4)
    public TepDuLieu baoCaoThamDinh = null;
}
