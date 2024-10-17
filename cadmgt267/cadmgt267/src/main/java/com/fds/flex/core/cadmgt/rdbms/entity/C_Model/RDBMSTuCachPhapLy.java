package com.fds.flex.core.cadmgt.rdbms.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class RDBMSTuCachPhapLy extends RDBMSBaseCategory<RDBMSTuCachPhapLy> {
    private static final long serialVersionUID = 1L;

    public RDBMSTuCachPhapLy() {
        super(false);
        super.setType(getType());
    }

    public RDBMSTuCachPhapLy(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_TU_CACH_PHAP_LY;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_TU_CACH_PHAP_LY);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }
}
