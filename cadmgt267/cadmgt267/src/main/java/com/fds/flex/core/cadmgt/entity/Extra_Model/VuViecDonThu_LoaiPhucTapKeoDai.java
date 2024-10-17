package com.fds.flex.core.cadmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class VuViecDonThu_LoaiPhucTapKeoDai {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.C_LOAI_PHUC_TAP_KEO_DAI;

    @JsonProperty("MaMuc")
    @Field(value = "MaMuc", order = 1)
    public String maMuc = StringPool.BLANK;

    @JsonProperty("TenMuc")
    @Field(value = "TenMuc", order = 2)
    public String tenMuc = StringPool.BLANK;
}
