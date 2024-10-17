package com.fds.flex.core.cadmgt.entity.Data_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Don_VuViec_CountModel {

    @JsonProperty("VuViecDonThu")
    public List<DanhMucCount> vuViecDonThu = new ArrayList<>();

    @JsonProperty("XuLyDonThu")
    public List<DanhMucCount> xuLyDonThu = new ArrayList<>();

}
