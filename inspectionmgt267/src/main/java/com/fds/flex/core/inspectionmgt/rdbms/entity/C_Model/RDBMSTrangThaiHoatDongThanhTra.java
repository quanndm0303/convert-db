package com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = DBConstant.C_TRANG_THAI_HOAT_DONG_THANH_TRA)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSTrangThaiHoatDongThanhTra extends RDBMSBaseCategory<RDBMSTrangThaiHoatDongThanhTra> {

    private static final long serialVersionUID = 1L;

    public RDBMSTrangThaiHoatDongThanhTra() {
        super(false);
        super.setType(getType());
    }

    public RDBMSTrangThaiHoatDongThanhTra(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_TRANG_THAI_HOAT_DONG_THANH_TRA;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_TRANG_THAI_HOAT_DONG_THANH_TRA);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum TrangThai {
        LapKeHoach("01", "Chuẩn bị chương trình"),
        DangThucHien("02", "Đang thực hiện"),
        TamDinhChi("03", "Tạm dừng thực hiện"),
        ChoBaoCaoKetQua("04", "Báo cáo kết quả"),
        HoanThanh("05", "Ban hành kết luận, thông báo"),
        HuyThucHien("06", "Đình chỉ thực hiện (hủy)");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        TrangThai(String maMuc, String tenMuc) {
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
