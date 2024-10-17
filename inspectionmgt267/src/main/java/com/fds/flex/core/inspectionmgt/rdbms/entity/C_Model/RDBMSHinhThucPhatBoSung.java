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
@Table(name = DBConstant.C_HINH_THUC_PHAT_BO_SUNG)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSHinhThucPhatBoSung extends RDBMSBaseCategory<RDBMSHinhThucPhatBoSung> {

    private static final long serialVersionUID = 1L;

    public RDBMSHinhThucPhatBoSung() {
        super(false);
        super.setType(getType());
    }

    public RDBMSHinhThucPhatBoSung(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_HINH_THUC_PHAT_BO_SUNG;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_HINH_THUC_PHAT_BO_SUNG);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

}
