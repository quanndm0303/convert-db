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
@Table(name = DBConstant.C_PHAN_TICH_KET_QUA_KNTC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSPhanTichKetQuaKNTC extends RDBMSBaseCategory<RDBMSPhanTichKetQuaKNTC> {
    private static final long serialVersionUID = 1L;

    public RDBMSPhanTichKetQuaKNTC() {
        super(false);
        super.setType(getType());
    }

    public RDBMSPhanTichKetQuaKNTC(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_PHAN_TICH_KET_QUA_KNTC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_PHAN_TICH_KET_QUA_KNTC);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum Loai {
        ToCaoSai("06", "Tố cáo sai");

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
