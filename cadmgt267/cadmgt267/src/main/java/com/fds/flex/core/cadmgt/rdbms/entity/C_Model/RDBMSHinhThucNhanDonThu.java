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
@Table(name = DBConstant.C_HINH_THUC_NHAN_DON_THU)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSHinhThucNhanDonThu extends RDBMSBaseCategory<RDBMSHinhThucNhanDonThu> {
    private static final long serialVersionUID = 1L;

    public RDBMSHinhThucNhanDonThu() {
        super(false);
        super.setType(getType());
    }

    public RDBMSHinhThucNhanDonThu(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_HINH_THUC_NHAN_DON_THU;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_HINH_THUC_NHAN_DON_THU);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }
}
