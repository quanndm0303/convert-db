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
public class RDBMSTrangThaiGiaiQuyetDon extends RDBMSBaseCategory<RDBMSTrangThaiGiaiQuyetDon> {
    private static final long serialVersionUID = 1L;

    public RDBMSTrangThaiGiaiQuyetDon() {
        super(false);
        super.setType(getType());
    }

    public RDBMSTrangThaiGiaiQuyetDon(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_TINH_TRANG_XU_LY_VU_VIEC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_TRANG_THAI_GIAI_QUYET_DON);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }
}
