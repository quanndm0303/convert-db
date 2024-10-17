package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class DanhBaLienLac {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_DANH_BA_LIEN_LAC;

    @JsonProperty("ThuDienTu")
    @Field(value = "ThuDienTu", order = 1)
    public String thuDienTu = StringPool.BLANK;

    @JsonProperty("SoDienThoai")
    @Field(value = "SoDienThoai", order = 2)
    public String soDienThoai = StringPool.BLANK;

    @JsonProperty("SoFax")
    @Field(value = "SoFax", order = 3)
    public String soFax = StringPool.BLANK;
}
