package com.fds.flex.core.inspectionmgt.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.LoaiDoiTuongCNTC_ThamChieu;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.BaseCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = DBConstant.C_LOAI_DOI_TUONG_CNTC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoaiDoiTuongCNTC extends BaseCategory<LoaiDoiTuongCNTC> {

    private static final long serialVersionUID = 1L;

    @JsonInclude
    @JsonProperty("ThamChieu")
    @Field(value = "ThamChieu", order = 7)
    public LoaiDoiTuongCNTC_ThamChieu thamChieu = null;

    public LoaiDoiTuongCNTC() {
        super(false);
        super.setType(getType());
    }

    public LoaiDoiTuongCNTC(boolean isUpdate) {
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

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

}
