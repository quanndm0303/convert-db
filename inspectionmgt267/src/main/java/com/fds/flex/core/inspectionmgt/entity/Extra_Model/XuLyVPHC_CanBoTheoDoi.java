package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class XuLyVPHC_CanBoTheoDoi {

    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_CAN_BO;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("HoVaTen")
    @Field(value = "HoVaTen", order = 2)
    public String hoVaTen = StringPool.BLANK;

    @JsonProperty("MaSoCanBo")
    @Field(value = "MaSoCanBo", order = 3)
    public String maSoCanBo = StringPool.BLANK;
}

