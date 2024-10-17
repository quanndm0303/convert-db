package com.fds.flex.core.cadmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class TaiLieuBangChung {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_TAI_LIEU_BANG_CHUNG;

    @JsonProperty("TenTaiLieu")
    @Field(value = "TenTaiLieu", order = 1)
    public String tenTaiLieu = StringPool.BLANK;

    @JsonProperty("TomTatNoiDung")
    @Field(value = "TomTatNoiDung", order = 2)
    public String tomTatNoiDung = null;

    @JsonProperty("TepDuLieu")
    @Field(value = "TepDuLieu", order = 3)
    public TepDuLieu tepDuLieu = null;
}
