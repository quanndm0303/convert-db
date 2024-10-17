package com.fds.flex.core.cadmgt.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.BaseCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = DBConstant.C_LOAI_PHUC_TAP_KEO_DAI)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoaiPhucTapKeoDai extends BaseCategory<LoaiPhucTapKeoDai> {

    private static final long serialVersionUID = 1L;

    public LoaiPhucTapKeoDai() {
        super(false);
        super.setType(getType());
    }

    public LoaiPhucTapKeoDai(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_LOAI_PHUC_TAP_KEO_DAI;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_LOAI_PHUC_TAP_KEO_DAI);
    }

    @Override
    public String getPrimKey() {

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

}
