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
@Table(name = DBConstant.C_NHOM_HANH_VI_VPHC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSNhomHanhViVPHC extends RDBMSBaseCategory<RDBMSNhomHanhViVPHC> {

    private static final long serialVersionUID = 1L;

    public RDBMSNhomHanhViVPHC() {
        super(false);
        super.setType(getType());
    }

    public RDBMSNhomHanhViVPHC(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_NHOM_HANH_VI_VPHC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_NHOM_HANH_VI_VPHC);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

}
