package com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = DBConstant.C_LOAI_HOAT_DONG_THANH_TRA)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSLoaiHoatDongThanhTra extends RDBMSBaseCategory<RDBMSLoaiHoatDongThanhTra> {

    private static final long serialVersionUID = 1L;

    public RDBMSLoaiHoatDongThanhTra() {
        super(false);
        super.setType(getType());
    }

    public RDBMSLoaiHoatDongThanhTra(boolean isUpdate) {
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

        String primKey = String.valueOf(this.getId());

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
        KiemTraTrachNhiemThuTruong("KT1", "Kiểm tra trách nhiệm thủ trưởng"),
        KiemTraPhongChongThamNhung("KT2", "Kiểm tra phòng chống tham nhũng"),
        KiemTraThucHienKetLuan("KT3", "Kiểm tra thực hiện kết luận"),
        ;

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
