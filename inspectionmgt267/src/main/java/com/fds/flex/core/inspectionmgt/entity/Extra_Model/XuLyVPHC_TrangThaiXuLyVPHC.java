package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter

@Setter
public class XuLyVPHC_TrangThaiXuLyVPHC {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.C_TRANG_THAI_XU_LY_VPHC;

    @JsonProperty("MaMuc")
    @Field(value = "MaMuc", order = 1)
    public String maMuc = StringPool.BLANK;

    @JsonProperty("TenMuc")
    @Field(value = "TenMuc", order = 2)
    public String tenMuc = StringPool.BLANK;
}
