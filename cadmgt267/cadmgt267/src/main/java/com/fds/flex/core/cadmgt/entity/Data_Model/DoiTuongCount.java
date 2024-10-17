package com.fds.flex.core.cadmgt.entity.Data_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoiTuongCount {
    @JsonProperty("MaDinhDanh")
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("TenGoi")
    public String tenGoi = StringPool.BLANK;

    @JsonProperty("SoLuong")
    public Long soLuong = 0L;
}
