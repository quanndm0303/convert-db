package com.fds.flex.core.inspectionmgt.entity.Data_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.inspectionmgt.entity.S_Model.LichCongTacDoan;
import com.fds.flex.core.inspectionmgt.entity.S_Model.ThanhVienDoan;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HoatDongThanhTraUnwindLichCongTacDoan {

    @JsonProperty("TenHoatDong")
    @Field(value = "TenHoatDong", order = 5)
    public String tenHoatDong = StringPool.BLANK;

    @JsonProperty("ThanhVienDoan")
    @Field(value = "ThanhVienDoan", order = 22)
    public List<ThanhVienDoan> thanhVienDoan = new ArrayList<>();

    @JsonProperty("LichCongTacDoan")
    @Field(value = "LichCongTacDoan", order = 23)
    public LichCongTacDoan lichCongTacDoan = null;
}
