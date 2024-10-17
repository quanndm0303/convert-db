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
@Table(name = DBConstant.C_LOAI_DE_XUAT_KIEN_NGHI)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSLoaiDeXuatKienNghi extends RDBMSBaseCategory<RDBMSLoaiDeXuatKienNghi> {

    private static final long serialVersionUID = 1L;

    public RDBMSLoaiDeXuatKienNghi() {
        super(false);
        super.setType(getType());
    }

    public RDBMSLoaiDeXuatKienNghi(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_LOAI_DE_XUAT_KIEN_NGHI;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_LOAI_DE_XUAT_KIEN_NGHI);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum Loai {
        ThuHoiVeNSNN("01", "Thu hồi về NSNN"),
        XuLyViPhamVeKinhTe("02", "Xử lý vi phạm về kinh tế"),
        XuLyHanhChinh("03", "Xử lý hành chính"),
        ChuyenCoQuanDieuTra("04", "Chuyển cơ quan điều tra"),
        HoanThienCoCheChinhSach("05", "Hoàn thiện cơ chế, chính sách");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        Loai(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public static String getTenMucByMaMuc(String maMuc) {
            for (Loai loai : Loai.values()) {
                if (loai.getMaMuc().equals(maMuc)) {
                    return loai.getTenMuc();
                }
            }
            return null;
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }
}
