package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class CanBoChienSi {

    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_CAN_BO_CHIEN_SI;

    @JsonProperty("CapBac")
    @Field(value = "CapBac", order = 12)
    public String capBac = null;

    @JsonProperty("ChucVu")
    @Field(value = "ChucVu", order = 13)
    public String chucVu = null;

    @JsonProperty("DonViCongTac")
    @Field(value = "DonViCongTac", order = 14)
    public String donViCongTac = null;
}
