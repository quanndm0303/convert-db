package com.fds.flex.core.cadmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class VuViecDonThu_ThongBaoKetLuan {
    @JsonProperty("@type")
    @Field(value = "@type", order = 0)
    public String type = DBConstant.T_THONG_BAO_KET_LUAN;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("SoHieuVanBan")
    @Field(value = "SoHieuVanBan", order = 2)
    public String soHieuVanBan = StringPool.BLANK;

    @JsonProperty("TrichYeuVanBan")
    @Field(value = "TrichYeuVanBan", order = 3)
    public String trichYeuVanBan = StringPool.BLANK;
}
