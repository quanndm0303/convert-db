package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.inspectionmgt.entity.S_Model.DeXuatKienNghi;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CaNhanToChuc_ThongBaoKetLuan {

    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_THONG_BAO_KET_LUAN;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("SoHieuVanBan")
    @Field(value = "SoHieuVanBan", order = 2)
    public String soHieuVanBan = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("NgayVanBan")
    @Field(value = "NgayVanBan", order = 3)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayVanBan = null;

    @JsonInclude
    @JsonProperty("CoQuanBanHanh")
    @Field(value = "CoQuanBanHanh", order = 4)
    public ThongBaoKetLuan_CoQuanBanHanh coQuanBanHanh = null;

    @JsonInclude
    @JsonProperty("TrichYeuVanBan")
    @Field(value = "TrichYeuVanBan", order = 5)
    public String trichYeuVanBan = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("DeXuatKienNghi")
    @Field(value = "DeXuatKienNghi", order = 6)
    public List<DeXuatKienNghi> deXuatKienNghi = new ArrayList<>();
}

