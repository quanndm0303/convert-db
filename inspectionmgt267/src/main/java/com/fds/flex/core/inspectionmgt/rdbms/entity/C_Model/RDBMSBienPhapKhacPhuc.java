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
@Table(name = DBConstant.C_BIEN_PHAP_KHAC_PHUC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSBienPhapKhacPhuc extends RDBMSBaseCategory<RDBMSBienPhapKhacPhuc> {

    private static final long serialVersionUID = 1L;

    public RDBMSBienPhapKhacPhuc() {
        super(false);
        super.setType(getType());
    }

    public RDBMSBienPhapKhacPhuc(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_BIEN_PHAP_KHAC_PHUC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_BIEN_PHAP_KHAC_PHUC);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

}
