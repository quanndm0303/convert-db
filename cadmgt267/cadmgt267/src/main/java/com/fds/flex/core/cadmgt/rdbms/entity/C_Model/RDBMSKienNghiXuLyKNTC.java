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
@Table(name = DBConstant.C_KIEN_NGHI_XU_LY_KNTC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSKienNghiXuLyKNTC extends RDBMSBaseCategory<RDBMSKienNghiXuLyKNTC> {
    private static final long serialVersionUID = 1L;

    public RDBMSKienNghiXuLyKNTC() {
        super(false);
        super.setType(getType());
    }

    public RDBMSKienNghiXuLyKNTC(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_KIEN_NGHI_XU_LY_KNTC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_KIEN_NGHI_XU_LY_KNTC);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }
}
