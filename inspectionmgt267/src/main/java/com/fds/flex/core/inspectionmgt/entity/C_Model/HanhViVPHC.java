package com.fds.flex.core.inspectionmgt.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.BaseCategory;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HanhViVPHC_NghiDinhXPVPHC;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HanhViVPHC_NhomHanhViVPHC;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = DBConstant.C_HANH_VI_VPHC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HanhViVPHC extends BaseCategory<HanhViVPHC> {

    private static final long serialVersionUID = 1L;

    @JsonProperty("MucTienPhatDuoi")
    @Field(value = "MucTienPhatDuoi", order = 6)
    public String mucTienPhatDuoi = StringPool.BLANK;

    @JsonProperty("MucTienPhatTren")
    @Field(value = "MucTienPhatTren", order = 7)
    public String mucTienPhatTren = StringPool.BLANK;

    @JsonProperty("NhomHanhViVPHC")
    @Field(value = "NhomHanhViVPHC", order = 8)
    public HanhViVPHC_NhomHanhViVPHC nhomHanhViVPHC = null;

    @JsonProperty("NghiDinhXPVPHC")
    @Field(value = "NghiDinhXPVPHC", order = 9)
    public HanhViVPHC_NghiDinhXPVPHC nghiDinhXPVPHC = null;

    public HanhViVPHC() {
        super(false);
        super.setType(getType());
    }

    public HanhViVPHC(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_HANH_VI_VPHC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_HANH_VI_VPHC);
    }

    @Override
    public String getPrimKey() {

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

}
