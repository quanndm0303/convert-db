package com.fds.flex.core.cadmgt.rdbms.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.entity.Extra_Model.LoaiChiTietVuViec_LoaiVuViecDonThu;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = DBConstant.C_LOAI_CHI_TIET_VU_VIEC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSLoaiChiTietVuViec extends RDBMSBaseCategory<RDBMSLoaiChiTietVuViec> {
    private static final long serialVersionUID = 1L;

    @JsonInclude
    @JsonProperty("LoaiVuViecDonThu")
    @Field(value = "LoaiVuViecDonThu", order = 7)
    public LoaiChiTietVuViec_LoaiVuViecDonThu loaiVuViecDonThu = null;

    public RDBMSLoaiChiTietVuViec() {
        super(false);
        super.setType(getType());
    }

    public RDBMSLoaiChiTietVuViec(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_LOAI_CHI_TIET_VU_VIEC;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_LOAI_CHI_TIET_VU_VIEC);
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
