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
@Document(collection = DBConstant.C_TRANG_THAI_HOAT_DONG_THANH_TRA)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrangThaiHoatDongThanhTra extends BaseCategory<TrangThaiHoatDongThanhTra> {

    private static final long serialVersionUID = 1L;

    public TrangThaiHoatDongThanhTra() {
        super(false);
        super.setType(getType());
    }

    public TrangThaiHoatDongThanhTra(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_TRANG_THAI_HOAT_DONG_THANH_TRA;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_TRANG_THAI_HOAT_DONG_THANH_TRA);
    }

    @Override
    public String getPrimKey() {

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum TrangThai {
        ChuaTrienKhai("01", "Chưa triển khai"),
        LapKeHoach("02", "Lập kế hoạch"),
        DangThucHien("03", "Đang thực hiện"),
        TamDinhChi("09", "Tạm đình chỉ"),
        ChoBaoCaoKetQua("04", "Chờ báo cáo kết quả"),
        ThamDinhXinYKien("06", "Thẩm định/ Xin ý kiến"),
        HoanThanh("08", "Hoàn thành"),
        HuyThucHien("10", "Hủy thực hiện");

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
