package com.fds.flex.core.inspectionmgt.entity.Statistic_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HDTT_LichCongTacDoan_Statistic {
    @JsonProperty("TenHoatDong")
    public String tenHoatDong = StringPool.BLANK;

    @JsonProperty("TruongDoan")
    public String truongDoan = StringPool.BLANK;

    @JsonProperty("CanBoThamGia")
    public String canBoThamGia = StringPool.BLANK;

    @JsonProperty("NoiDungCongTac")
    public String noiDungCongTac = StringPool.BLANK;

}
