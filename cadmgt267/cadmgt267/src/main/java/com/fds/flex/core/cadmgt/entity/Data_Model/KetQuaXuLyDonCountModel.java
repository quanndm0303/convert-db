package com.fds.flex.core.cadmgt.entity.Data_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class KetQuaXuLyDonCountModel {

    @JsonProperty("DonThuocThamQuyen")
    public List<DanhMucCount> donThuocThamQuyen = new ArrayList<>();

    @JsonProperty("DonKhongThuocThamQuyen")
    public List<DanhMucCount> donKhongThuocThamQuyen = new ArrayList<>();

}
