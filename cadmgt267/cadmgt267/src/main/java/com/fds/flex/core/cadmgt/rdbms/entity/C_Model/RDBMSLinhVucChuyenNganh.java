package com.fds.flex.core.cadmgt.rdbms.entity.C_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.entity.Extra_Model.LinhVucChuyenNganh_LoaiVuViecDonThu;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = DBConstant.C_LINH_VUC_CHUYEN_NGANH)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSLinhVucChuyenNganh extends RDBMSBaseCategory<RDBMSLinhVucChuyenNganh> {
    private static final long serialVersionUID = 1L;

    @JsonInclude
    @JsonProperty("LoaiVuViecDonThu")
    @Field(value = "LoaiVuViecDonThu", order = 7)
    public List<LinhVucChuyenNganh_LoaiVuViecDonThu> loaiVuViecDonThu = new ArrayList<>();

    public RDBMSLinhVucChuyenNganh() {
        super(false);
        super.setType(getType());
    }

    public RDBMSLinhVucChuyenNganh(boolean isUpdate) {
        super(isUpdate);
        super.setType(getType());

    }

    @Override
    public String getType() {
        return DBConstant.C_LINH_VUC_CHUYEN_NGANH;
    }

    @Override
    public void setType(String type) {
        super.setType(DBConstant.C_LINH_VUC_CHUYEN_NGANH);
    }

    @Override
    public String getPrimKey() {

        String primKey = String.valueOf(this.getId());

        super.setPrimKey(primKey);

        return primKey;
    }

    @Getter
    public enum LinhVuc {
        LinhVucHanhChinh("01", "Lĩnh vực hành chính"),
        CheDoChinhSach("01.1", "Chế độ, chính sách"),
        DatDaiNhaCua("01.2", "Đất đai nhà cửa"),
        CongChucCongVu("01.3", "Công chức, công vụ"),
        DauTuXayDungCoBan("01.4", "Đầu tư xây dựng cơ bản"),
        TaiChinhNganSach("01.5", "Tài chính, ngân sách"),
        HanhChinhKhac("01.9", "Hành chính khác"),
        LinhVucTuPhap("02", "Lĩnh vực Tư pháp"),
        LinhVucDangToanThe("03", "Lĩnh vực Đẩng đoàn thể"),
        ThamNhung("04", "Tham nhũng");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        LinhVuc(String maMuc, String tenMuc) {
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
