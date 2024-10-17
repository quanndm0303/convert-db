package com.fds.flex.core.cadmgt.rdbms.entity.C_Model;

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
@Table(name = DBConstant.C_LOAI_VU_VIEC_DON_THU)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSLoaiVuViecDonThu extends RDBMSBaseCategory<RDBMSLoaiVuViecDonThu> {
    private static final long serialVersionUID = 1L;

    public RDBMSLoaiVuViecDonThu() {
        super(false);
        super.setType(getType());
    }

    public RDBMSLoaiVuViecDonThu(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_LOAI_VU_VIEC_DON_THU;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_LOAI_VU_VIEC_DON_THU);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum Loai {

        //Custom
        KhieuNai("01", "Khiếu nại"),
        ToCao("02", "Tố cáo"),
        PhanAnhKienNghi("03", "Phản ánh, kiến nghị"),
        ToGiacToiPham("04", "Tố giác tội phạm");

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
