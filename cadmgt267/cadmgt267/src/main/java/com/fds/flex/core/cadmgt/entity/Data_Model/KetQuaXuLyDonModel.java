package com.fds.flex.core.cadmgt.entity.Data_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KetQuaXuLyDonModel {
    @JsonProperty("_id")
    public Group _id = null;

    @JsonProperty("SoLuong")
    public Long soLuong = 0L;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Group {
        @JsonProperty("CoQuanThamQuyen")
        public String coQuanThamQuyen = StringPool.BLANK;

        @JsonProperty("LoaiVuViecDonThu")
        public String loaiVuViecDonThu = StringPool.BLANK;
    }
}
