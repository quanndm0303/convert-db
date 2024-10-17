package com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.LoaiDoiTuongCNTC_ThamChieu;
import com.fds.flex.core.inspectionmgt.rdbms.converter.LoaiDoiTuongCNTC_ThamChieuConverter;
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
@Table(name = DBConstant.C_LOAI_DOI_TUONG_CNTC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSLoaiDoiTuongCNTC extends RDBMSBaseCategory<RDBMSLoaiDoiTuongCNTC> {

    private static final long serialVersionUID = 1L;

    @JsonInclude
    @JsonProperty("ThamChieu")
    @Column(name = "ThamChieu", columnDefinition = "json")
    @Convert(converter = LoaiDoiTuongCNTC_ThamChieuConverter.class)
    public LoaiDoiTuongCNTC_ThamChieu thamChieu = null;

    public RDBMSLoaiDoiTuongCNTC() {
        super(false);
        super.setType(getType());
    }

    public RDBMSLoaiDoiTuongCNTC(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_LOAI_DOI_TUONG_CNTC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_LOAI_DOI_TUONG_CNTC);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

}
