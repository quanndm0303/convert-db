package com.fds.flex.core.cadmgt.rdbms.entity.C_Model;

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
@Table(name = DBConstant.C_DIA_BAN_XU_LY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSDiaBanXuLy extends RDBMSBaseCategory<RDBMSDiaBanXuLy> {

    private static final long serialVersionUID = 1L;

    public RDBMSDiaBanXuLy() {
        super(false);
        super.setType(getType());
    }

    public RDBMSDiaBanXuLy(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_DIA_BAN_XU_LY;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_DIA_BAN_XU_LY);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

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
