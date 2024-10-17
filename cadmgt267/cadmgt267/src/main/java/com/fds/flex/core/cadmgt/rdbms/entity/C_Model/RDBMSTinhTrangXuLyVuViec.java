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
@Table(name = DBConstant.C_TINH_TRANG_XU_LY_VU_VIEC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSTinhTrangXuLyVuViec extends RDBMSBaseCategory<RDBMSTinhTrangXuLyVuViec> {
    private static final long serialVersionUID = 1L;

    public RDBMSTinhTrangXuLyVuViec() {
        super(false);
        super.setType(getType());
    }

    public RDBMSTinhTrangXuLyVuViec(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_TINH_TRANG_XU_LY_VU_VIEC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_TINH_TRANG_XU_LY_VU_VIEC);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum TinhTrang {
        ThuLyGiaiQuyet("05", "Thụ lý giải quyết"),
        TrungCauGiamDinh("15", "Trưng cầu giám định");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        TinhTrang(String maMuc, String tenMuc) {
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
