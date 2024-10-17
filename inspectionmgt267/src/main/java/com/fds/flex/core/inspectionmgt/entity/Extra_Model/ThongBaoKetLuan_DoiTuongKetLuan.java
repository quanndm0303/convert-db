package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.inspectionmgt.entity.T_Model.HoatDongThanhTra;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class ThongBaoKetLuan_DoiTuongKetLuan {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = StringPool.BLANK;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @JsonProperty("TenHoatDong")
    @Field(value = "TenHoatDong", order = 2)
    public String tenHoatDong = StringPool.BLANK;

    @JsonProperty("NoiDungVuViec")
    @Field(value = "NoiDungVuViec", order = 3)
    public String noiDungVuViec = StringPool.BLANK;

    public void setMaDinhDanh(String maDinhDanh) {
        this.maDinhDanh = maDinhDanh;
        if (maDinhDanh.startsWith(HoatDongThanhTra.class.getSimpleName())) {
            this.type = DBConstant.T_HOAT_DONG_THANH_TRA;
        } else if (maDinhDanh.startsWith("VuViecDonThu")) {
            this.type = "T_VuViecDonThu";
        }
    }
}
