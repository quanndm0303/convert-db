package com.fds.flex.core.inspectionmgt.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.NhomHanhViVPHC_NghiDinhXPVPHC;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.BaseCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = DBConstant.C_NHOM_HANH_VI_VPHC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NhomHanhViVPHC extends BaseCategory<NhomHanhViVPHC> {

    private static final long serialVersionUID = 1L;

    @JsonProperty("NghiDinhXPVPHC")
    @Field(value = "NghiDinhXPVPHC", order = 5)
    public NhomHanhViVPHC_NghiDinhXPVPHC nghiDinhXPVPHC = null;


    public NhomHanhViVPHC() {
        super(false);
        super.setType(getType());
    }

    public NhomHanhViVPHC(boolean isUpdate) {
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

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

}
