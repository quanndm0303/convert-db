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
@Table(name = DBConstant.C_TRANG_THAI_XU_LY_VPHC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSTrangThaiXuLyVPHC extends RDBMSBaseCategory<RDBMSTrangThaiXuLyVPHC> {

    private static final long serialVersionUID = 1L;

    public RDBMSTrangThaiXuLyVPHC() {
        super(false);
        super.setType(getType());
    }

    public RDBMSTrangThaiXuLyVPHC(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_TRANG_THAI_XU_LY_VPHC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_TRANG_THAI_XU_LY_VPHC);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum TrangThai {
        ChoLapQuyetDinh("01", "Chờ lập quyết định"),
        HetThoiHieuXuPhat("09", "Hết thời hiệu xử phạt");

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
