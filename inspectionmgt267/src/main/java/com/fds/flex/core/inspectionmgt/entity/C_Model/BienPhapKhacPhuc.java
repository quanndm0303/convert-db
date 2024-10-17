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
@Document(collection = DBConstant.C_BIEN_PHAP_KHAC_PHUC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BienPhapKhacPhuc extends BaseCategory<BienPhapKhacPhuc> {

    private static final long serialVersionUID = 1L;

    public BienPhapKhacPhuc() {
        super(false);
        super.setType(getType());
    }

    public BienPhapKhacPhuc(boolean isUpdate) {
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

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

}
