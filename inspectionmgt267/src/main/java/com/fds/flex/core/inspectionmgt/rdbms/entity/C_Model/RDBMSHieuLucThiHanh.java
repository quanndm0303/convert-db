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
@Table(name = DBConstant.C_HIEU_LUC_THI_HANH)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSHieuLucThiHanh extends RDBMSBaseCategory<RDBMSHieuLucThiHanh> {

    private static final long serialVersionUID = 1L;

    public RDBMSHieuLucThiHanh() {
        super(false);
        super.setType(getType());
    }

    public RDBMSHieuLucThiHanh(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_HIEU_LUC_THI_HANH;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_HIEU_LUC_THI_HANH);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

}
