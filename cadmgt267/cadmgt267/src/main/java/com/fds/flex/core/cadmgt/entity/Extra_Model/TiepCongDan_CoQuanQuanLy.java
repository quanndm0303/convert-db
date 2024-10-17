package com.fds.flex.core.cadmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class TiepCongDan_CoQuanQuanLy {
    @JsonProperty("@type")
    @Field(value = "@type")
    public String type = DBConstant.T_CO_QUAN_DON_VI;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh")
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("TenGoi")
    @Field(value = "TenGoi")
    public String tenGoi = StringPool.BLANK;
}
