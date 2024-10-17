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
@Table(name = DBConstant.C_TRANG_THAI_THEO_DOI)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSTrangThaiTheoDoi extends RDBMSBaseCategory<RDBMSTrangThaiTheoDoi> {

    private static final long serialVersionUID = 1L;

    public RDBMSTrangThaiTheoDoi() {
        super(false);
        super.setType(getType());
    }

    public RDBMSTrangThaiTheoDoi(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_TRANG_THAI_THEO_DOI;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_TRANG_THAI_THEO_DOI);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

}
