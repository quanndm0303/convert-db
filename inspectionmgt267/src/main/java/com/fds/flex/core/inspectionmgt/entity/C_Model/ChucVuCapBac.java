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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChucVuCapBac extends BaseCategory<ChucVuCapBac> {
    private static final long serialVersionUID = 1L;

    public ChucVuCapBac() {
        super(false);
        super.setType(getType());
    }

    public ChucVuCapBac(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_CHUC_VU_CAP_BAC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_CHUC_VU_CAP_BAC);
    }

    @Override
    public String getPrimKey() {

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum ChucVu {
        TruongPhong("15", "Trưởng phòng"),
        PhoTruongPhong("16", "Phó trưởng phòng");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        ChucVu(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }

}