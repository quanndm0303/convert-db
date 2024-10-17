package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LichCongTacDoan {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_LICH_CONG_TAC_DOAN;

    @JsonProperty("IDLich")
    @Field(value = "IDLich", order = 1)
    public String idLich = String.valueOf(System.currentTimeMillis());

    @JsonProperty("NgayBatDau")
    @Field(value = "NgayBatDau", order = 2)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayBatDau = null;

    @JsonProperty("NgayKetThuc")
    @Field(value = "NgayKetThuc", order = 3)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayKetThuc = null;

    @JsonProperty("NoiDungCongTac")
    @Field(value = "NoiDungCongTac", order = 4)
    public String noiDungCongTac = StringPool.BLANK;

    @JsonProperty("IDThanhVien")
    @Field(value = "IDThanhVien", order = 5)
    public List<String> idThanhVien = new ArrayList<>();

    public void setIdLich(String idLich) {
        if (Validator.isNull(idLich)) {
            this.idLich = String.valueOf(System.currentTimeMillis());
        } else {
            this.idLich = idLich;
        }
    }
}
