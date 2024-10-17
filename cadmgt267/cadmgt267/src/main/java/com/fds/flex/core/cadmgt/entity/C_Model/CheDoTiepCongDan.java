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
@Document(collection = DBConstant.C_CHE_DO_TIEP_CONG_DAN)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheDoTiepCongDan extends BaseCategory<CheDoTiepCongDan> {

    private static final long serialVersionUID = 1L;

    public CheDoTiepCongDan() {
        super(false);
        super.setType(getType());
    }

    public CheDoTiepCongDan(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_CHE_DO_TIEP_CONG_DAN;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_CHE_DO_TIEP_CONG_DAN);
    }

    @Override
    public String getPrimKey() {

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum CheDo {
        TiepThuongXuyen("01", "Tiếp thường xuyên"),
        ThuTruongTiep("02", "Thủ trưởng tiếp"),
        UyQuyenCuaThuTruongTiepDinhKy("03", "Ủy quyền của Thủ trưởng tiếp định kỳ");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        CheDo(String maMuc, String tenMuc) {
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
