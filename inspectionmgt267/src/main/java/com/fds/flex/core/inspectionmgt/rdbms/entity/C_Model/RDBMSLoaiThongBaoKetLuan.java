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
@Table(name = DBConstant.C_LOAI_THONG_BAO_KET_LUAN)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSLoaiThongBaoKetLuan extends RDBMSBaseCategory<RDBMSLoaiThongBaoKetLuan> {

    private static final long serialVersionUID = 1L;

    public RDBMSLoaiThongBaoKetLuan() {
        super(false);
        super.setType(getType());
    }

    public RDBMSLoaiThongBaoKetLuan(boolean isUpdate) {
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

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum Loai {
        KetLuanThanhTraHanhChinh("01", "Kết luận thanh tra hành chính"),
        KetLuanKiemTraPhapLuatVeTTrTCDKNTCPCTN("04", "Kết luận kiểm tra pháp luật về TTr, TCD, KN, TC, PCTN");

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
