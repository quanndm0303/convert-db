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
@Document(collection = DBConstant.C_TRANG_THAI_THEO_DOI)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrangThaiTheoDoi extends BaseCategory<TrangThaiTheoDoi> {

    private static final long serialVersionUID = 1L;

    public TrangThaiTheoDoi() {
        super(false);
        super.setType(getType());
    }

    public TrangThaiTheoDoi(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_TRANG_THAI_THEO_DOI;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_TRANG_THAI_THEO_DOI);
    }

    @Override
    public String getPrimKey() {

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum TrangThai {
        DuThaoKetQua("01", "Dự thảo kết quả"),
        ChoThamDinh("02", "Chờ thẩm định"),
        DaBanHanhThongBaoKetLuan("03", "Đã ban hành thông báo, kết luận"),
        PhanCongPhoTruongPhongChiDao("05", "Phân công phó trưởng phòng chỉ đạo");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        TrangThai(String maMuc, String tenMuc) {
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
