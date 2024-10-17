package com.fds.flex.core.cadmgt.rdbms.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Table;


@Setter
@Getter
@Entity
@Table(name = DBConstant.C_TINH_TRANG_XU_LY_DON_THU)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSTinhTrangXuLyDonThu extends RDBMSBaseCategory<RDBMSTinhTrangXuLyDonThu> {
    private static final long serialVersionUID = 1L;
    @JsonInclude
    @JsonProperty("SuDungChoVuViecDonThu")
    @Field(value = "SuDungChoVuViecDonThu", order = 4)
    public boolean suDungChoVuViecDonThu = false;
    @JsonInclude
    @JsonProperty("SuDungChoXuLyDonThu")
    @Field(value = "SuDungChoXuLyDonThu", order = 5)
    public boolean suDungChoXuLyDonThu = false;

    public RDBMSTinhTrangXuLyDonThu() {
        super(false);
        super.setType(getType());
    }

    public RDBMSTinhTrangXuLyDonThu(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_TINH_TRANG_XU_LY_DON_THU;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_TINH_TRANG_XU_LY_DON_THU);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum Loai {
        PhanLoaiXuLyDon("02", "Phân loại xử lý đơn"),
        HuongDan("03", "Hướng dẫn"),
        ChuyenDon("04", "Chuyển đơn"),
        DonDocGiaiQuyet("05", "Đôn đốc giải quyết"),
        LuuDon("06", "Lưu đơn"),
        XemXetThuLyDon("07", "Xem xét thụ lý đơn"),
        ThuLyGiaiQuyet("08", "Thụ lý giải quyết"),
        KhongDuDieuKienThuLy("09", "Không đủ điều kiện thụ lý"),
        RaKetQuaGiaiQuyet("10", "Ra kết quả giải quyết"),
        RutDonChamDut("11", "Rút đơn, chấm dứt KNTC");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        Loai(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }
}
