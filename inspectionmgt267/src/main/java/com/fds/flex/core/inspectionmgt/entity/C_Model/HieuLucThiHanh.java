package com.fds.flex.core.inspectionmgt.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.BaseCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = DBConstant.C_HIEU_LUC_THI_HANH)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HieuLucThiHanh extends BaseCategory<HieuLucThiHanh> {

    private static final long serialVersionUID = 1L;

    public HieuLucThiHanh() {
        super(false);
        super.setType(getType());
    }

    public HieuLucThiHanh(boolean isUpdate) {
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

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

}
