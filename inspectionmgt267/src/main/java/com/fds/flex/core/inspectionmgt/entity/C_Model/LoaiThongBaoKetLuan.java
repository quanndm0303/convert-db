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
@Document(collection = DBConstant.C_LOAI_THONG_BAO_KET_LUAN)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoaiThongBaoKetLuan extends BaseCategory<LoaiThongBaoKetLuan> {

    private static final long serialVersionUID = 1L;

    public LoaiThongBaoKetLuan() {
        super(false);
        super.setType(getType());
    }

    public LoaiThongBaoKetLuan(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_LOAI_THONG_BAO_KET_LUAN;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_LOAI_THONG_BAO_KET_LUAN);
    }

    @Override
    public String getPrimKey() {

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum Loai {
        KetLuanThanhTraHanhChinh("01", "Kết luận thanh tra hành chính"),
        KetLuanThanhKiemTraChuyenNganh("02", "Kết luận thanh, kiểm tra chuyên ngành"),
        KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN("04", "Kết luận kiểm tra pháp luật về TTr, TCD, KN, TC, PCTN"),
        QuyetDinhGiaiQuyetKhieuNai("05", "Quyết định giải quyết khiếu nại"),
        KetLuanNoiDungToCao("06", "Kết luận nội dung tố cáo");

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
