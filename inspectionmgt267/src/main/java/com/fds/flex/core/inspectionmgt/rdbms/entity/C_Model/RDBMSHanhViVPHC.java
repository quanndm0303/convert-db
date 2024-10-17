package com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HanhViVPHC_NghiDinhXPVPHC;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HanhViVPHC_NhomHanhViVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.converter.HanhViVPHC_NghiDinhXPVPHCConverter;
import com.fds.flex.core.inspectionmgt.rdbms.converter.HanhViVPHC_NhomHanhViVPHCConverter;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = DBConstant.C_HANH_VI_VPHC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSHanhViVPHC extends RDBMSBaseCategory<RDBMSHanhViVPHC> {

    private static final long serialVersionUID = 1L;

    @JsonInclude
    @JsonProperty("MucTienPhatDuoi")
    @Column(name = "MucTienPhatDuoi", nullable = false)
    public String mucTienPhatDuoi = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("MucTienPhatTren")
    @Column(name = "MucTienPhatTren", nullable = false)
    public String mucTienPhatTren = StringPool.BLANK;

    @JsonInclude
    @JsonProperty("NhomHanhViVPHC")
    @Column(name = "NhomHanhViVPHC", columnDefinition = "json")
    @Convert(converter = HanhViVPHC_NhomHanhViVPHCConverter.class)
    public HanhViVPHC_NhomHanhViVPHC nhomHanhViVPHC = null;

    @JsonInclude
    @JsonProperty("NghiDinhXPVPHC")
    @Column(name = "NghiDinhXPVPHC", columnDefinition = "json")
    @Convert(converter = HanhViVPHC_NghiDinhXPVPHCConverter.class)
    public HanhViVPHC_NghiDinhXPVPHC nghiDinhXPVPHC = null;

    public RDBMSHanhViVPHC() {
        super(false);
        super.setType(getType());
    }

    public RDBMSHanhViVPHC(boolean isUpdate) {
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

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

}
