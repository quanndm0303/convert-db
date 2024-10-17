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
@Document(collection = DBConstant.C_LOAI_HOAT_DONG_THANH_TRA)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoaiHoatDongThanhTra extends BaseCategory<LoaiHoatDongThanhTra> {

    private static final long serialVersionUID = 1L;

    public LoaiHoatDongThanhTra() {
        super(false);
        super.setType(getType());
    }

    public LoaiHoatDongThanhTra(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_LOAI_HOAT_DONG_THANH_TRA;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_LOAI_HOAT_DONG_THANH_TRA);
    }

    @Override
    public String getPrimKey() {

        String primKey = this.get_id() != null ? this.get_id().toHexString() : StringPool.BLANK;

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum Loai {
        ThanhTraHanhChinh("CĐ1", "Thanh tra hành chính"),
        ThanhTraChuyenNganh("CĐ2", "Thanh tra chuyên ngành"),
        ThanhTraDacBiet("CĐ3", "Thanh tra đặc biệt"),
        ChuanBiThanhTra("VĐ3", "Chuẩn bị thanh tra"),
        GiamSatThanhTra("VĐ4", "Giám sát thanh tra"),
        KiemTraDieuKienThuLyGiaiQuyetKNTC("VĐ2", "Kiểm tra điều kiện thụ lý giải quyết khiếu nại, tố cáo"),
        XacMinhTaiSanThuNhap("XT", "Xác minh tài sản thu nhập"),
        KiemTraTrachNhiemThuTruong("KT1", "Kiểm tra trách nhiệm thủ trưởng"),
        KiemTraPhongChongThamNhung("KT2", "Kiểm tra phòng chống tham nhũng"),
        KiemTraThucHienKetLuan("KT3", "Kiểm tra thực hiện kết luận"),
        XacMinhPhongChongThamNhungTieuCuc("PCTN", "Xác minh phòng chống tham nhũng tiêu cực");

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
