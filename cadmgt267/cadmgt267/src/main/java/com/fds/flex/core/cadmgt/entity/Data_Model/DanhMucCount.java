package com.fds.flex.core.cadmgt.entity.Data_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DanhMucCount {
    @JsonProperty("MaMuc")
    public String maMuc = StringPool.BLANK;

    @JsonProperty("TenMuc")
    public String tenMuc = StringPool.BLANK;

    @JsonProperty("SoLuong")
    public Long soLuong = 0L;
}
