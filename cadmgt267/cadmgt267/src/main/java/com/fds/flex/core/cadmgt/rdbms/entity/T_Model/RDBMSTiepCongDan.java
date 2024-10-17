package com.fds.flex.core.cadmgt.rdbms.entity.T_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.cadmgt.entity.Extra_Model.*;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.RDBMSBaseCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = DBConstant.T_TIEP_CONG_DAN)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSTiepCongDan extends RDBMSBaseCategory<RDBMSTiepCongDan> {

        @JsonInclude
        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh", order = 4)
        public String maDinhDanh = StringPool.BLANK;

        @JsonInclude
        @JsonProperty("SoThuTuTiep")
        @Field(value = "SoThuTuTiep", order = 4)
        public String soThuTuTiep = StringPool.BLANK;

        @JsonInclude
        @JsonProperty("NgayTiepCongDan")
        @Field(value = "NgayTiepCongDan", order = 5)
        @JsonSerialize(using = CustomUTCDateSerializer.class)
        @JsonDeserialize(using = CustomUTCDateDeserializer.class)
        @Schema(type = "string", format = "date-time")
        public Long ngayTiepCongDan = null;

        @JsonInclude
        @JsonProperty("CheDoTiepCongDan")
        @Field(value = "CheDoTiepCongDan", order = 6)
        public TiepCongDan_CheDoTiepCongDan cheDoTiepCongDan = new TiepCongDan_CheDoTiepCongDan();

        @JsonInclude
        @JsonProperty("DoiTuongTiepCongDan")
        @Field(value = "DoiTuongTiepCongDan", order = 7)
        public TiepCongDan_DoiTuongTiepCongDan doiTuongTiepCongDan = null;

        @JsonInclude
        @JsonProperty("DoiTuongDuocTiep")
        @Field(value = "DoiTuongDuocTiep", order = 8)
        public TiepCongDan_DoiTuongDuocTiep doiTuongDuocTiep = new TiepCongDan_DoiTuongDuocTiep();

        @JsonInclude
        @JsonProperty("CoQuanQuanLy")
        @Field(value = "CoQuanQuanLy", order = 9)
        public TiepCongDan_CoQuanQuanLy coQuanQuanLy = new TiepCongDan_CoQuanQuanLy();

        @JsonInclude
        @JsonProperty("CoQuanTiepCongDan")
        @Field(value = "CoQuanTiepCongDan", order = 10)
        public TiepCongDan_CoQuanTiepCongDan coQuanTiepCongDan = new TiepCongDan_CoQuanTiepCongDan();

        @JsonInclude
        @JsonProperty("LanhDaoChuTri")
        @Field(value = "LanhDaoChuTri", order = 11)
        public TiepCongDan_LanhDaoChuTri lanhDaoChuTri = null;

        @JsonInclude
        @JsonProperty("LanhDaoQuanLy")
        @Field(value = "LanhDaoQuanLy", order = 12)
        public TiepCongDan_LanhDaoQuanLy lanhDaoQuanLy = null;

        @JsonInclude
        @JsonProperty("DonViTheoDoi")
        @Field(value = "DonViTheoDoi", order = 13)
        public TiepCongDan_DonViTheoDoi donViTheoDoi = null;

        @JsonInclude
        @JsonProperty("DonViPhoiHop")
        @Field(value = "DonViPhoiHop", order = 14)
        public TiepCongDan_DonViPhoiHop donViPhoiHop = null;

        @JsonInclude
        @JsonProperty("LanhDaoTheoDoi")
        @Field(value = "LanhDaoTheoDoi", order = 15)
        public TiepCongDan_LanhDaoTheoDoi lanhDaoTheoDoi = null;

        @JsonInclude
        @JsonProperty("NguoiTiepCongDan")
        @Field(value = "NguoiTiepCongDan", order = 16)
        public TiepCongDan_NguoiTiepCongDan nguoiTiepCongDan = new TiepCongDan_NguoiTiepCongDan();

        @JsonInclude
        @JsonProperty("CanBoThamGia")
        @Field(value = "CanBoThamGia", order = 15)
        public String canBoThamGia = null;

        @JsonInclude
        @JsonProperty("KyTiepCongDan")
        @Field(value = "KyTiepCongDan", order = 17)
        public Integer kyTiepCongDan = null;

        @JsonInclude
        @JsonProperty("SoLuongCongDan")
        @Field(value = "SoLuongCongDan", order = 18)
        public Integer soLuongCongDan = 0;

        @JsonInclude
        @JsonProperty("SoVuViec")
        @Field(value = "SoVuViec", order = 19)
        public Integer soVuViec = 0;

        @JsonInclude
        @JsonProperty("NguoiDuocTiep")
        @Field(value = "NguoiDuocTiep", order = 20)
        public List<TiepCongDan_NguoiDuocTiep> nguoiDuocTiep = new ArrayList<>();

        @JsonInclude
        @JsonProperty("VuViecDonThu")
        @Field(value = "VuViecDonThu", order = 21)
        public TiepCongDan_VuViecDonThu vuViecDonThu = new TiepCongDan_VuViecDonThu();

        @JsonInclude
        @JsonProperty("KetQuaTiepCongDan")
        @Field(value = "KetQuaTiepCongDan", order = 22)
        public TiepCongDan_KetQuaTiepCongDan ketQuaTiepCongDan = new TiepCongDan_KetQuaTiepCongDan();

        @JsonInclude
        @JsonProperty("NoiDungHuongDan")
        @Field(value = "NoiDungHuongDan", order = 23)
        public String noiDungHuongDan = null;

        @JsonInclude
        @JsonProperty("DeXuatCuaCoQuanThamMuu")
        @Field(value = "DeXuatCuaCoQuanThamMuu", order = 24)
        public String deXuatCuaCoQuanThamMuu = null;

        @JsonInclude
        @JsonProperty("GhiChuKetQua")
        @Field(value = "GhiChuKetQua", order = 25)
        public String ghiChuKetQua = null;

        @Transient
        @JsonProperty("XuLyDonThu")
        public TiepCongDan_XuLyDonThu xuLyDonThu = null;

        public RDBMSTiepCongDan() {
            super(false);
            super.setType(getType());
        }

        public RDBMSTiepCongDan(boolean isUpdate) {
            super(isUpdate);
            super.setType(getType());
        }

        @Override
        public String getType() {
            return DBConstant.T_TIEP_CONG_DAN;
        }

        @Override
        public void setType(String type) {
            super.setType(DBConstant.T_TIEP_CONG_DAN);
        }

        @Override
        public String getPrimKey() {
            if (Validator.isNull(super.getPrimKey())) {
                String primKey = String.valueOf(this.getId());
                super.setPrimKey(primKey);
                return primKey;
            }
            return super.getPrimKey();
        }

}
