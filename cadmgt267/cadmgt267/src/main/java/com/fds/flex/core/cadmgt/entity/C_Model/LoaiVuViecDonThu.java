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
@Document(collection = DBConstant.C_LOAI_VU_VIEC_DON_THU)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoaiVuViecDonThu extends BaseCategory<LoaiVuViecDonThu> {

    private static final long serialVersionUID = 1L;

    public LoaiVuViecDonThu() {
        super(false);
        super.setType(getType());
    }

    public LoaiVuViecDonThu(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_LOAI_VU_VIEC_DON_THU;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_LOAI_VU_VIEC_DON_THU);
    }

    @Override
    public String getPrimKey() {

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum Loai {

        //Custom
        KhieuNai("01", "Khiếu nại"),
        ToCao("02", "Tố cáo"),
        PhanAnhKienNghi("03", "Phản ánh, kiến nghị"),
        ToGiacToiPham("04", "Tố giác tội phạm");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        Loai(String maMuc, String tenMuc) {
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
