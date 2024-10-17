package com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model;

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
@Table(name = DBConstant.C_NGHI_DINH_XPVPHC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSNghiDinhXPVPHC extends RDBMSBaseCategory<RDBMSNghiDinhXPVPHC> {

    private static final long serialVersionUID = 1L;

    public RDBMSNghiDinhXPVPHC() {
        super(false);
        super.setType(getType());
    }

    public RDBMSNghiDinhXPVPHC(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_NGHI_DINH_XPVPHC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_NGHI_DINH_XPVPHC);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

}
