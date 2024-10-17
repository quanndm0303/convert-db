package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoatDongThanhTra_GiayToLuuTruSo {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_GIAY_TO_LUU_TRU_SO;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("TenGiayTo")
    @Field(value = "TenGiayTo", order = 4)
    public String tenGiayTo = StringPool.BLANK;

}
