package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.customformat.DecimalSerializer;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TruongHopPhatTien {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_XU_PHAT_BO_SUNG;

    @JsonProperty("TongSoTienPhat")
    @Field(value = "TongSoTienPhat", order = 1)
    public Long tongSoTienPhat = 0L;

    @JsonProperty("ThoiHanNopTien")
    @Field(value = "ThoiHanNopTien", order = 2)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanNopTien = null;

    @JsonProperty("NoiThuTienPhat")
    @Field(value = "NoiThuTienPhat", order = 3)
    public String noiThuTienPhat = StringPool.BLANK;

    @JsonProperty("HoanMienGiam")
    @Field(value = "HoanMienGiam", order = 4)
    public List<HoanMienGiam> hoanMienGiam = new ArrayList<>();

    @JsonProperty("SoTienPhatPhaiNop")
    @Field(value = "SoTienPhatPhaiNop", order = 5)
    public Long soTienPhatPhaiNop = 0L;

    @JsonProperty("SoTienPhatChamNop")
    @Field(value = "SoTienPhatChamNop", order = 6)
    public Long soTienPhatChamNop = null;

    @JsonProperty("ThongTinNopTien")
    @Field(value = "ThongTinNopTien", order = 7)
    public List<ThongTinNopTien> thongTinNopTien = new ArrayList<>();

    @JsonProperty("HieuLucThiHanh")
    @Field(value = "HieuLucThiHanh", order = 8)
    public HieuLucThiHanh hieuLucThiHanh = new HieuLucThiHanh();

    @JsonProperty("NgayHoanThanh")
    @Field(value = "NgayHoanThanh", order = 9)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayHoanThanh = null;

    @JsonProperty("NgayHuyThiHanh")
    @Field(value = "NgayHuyThiHanh", order = 10)
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayHuyThiHanh = null;

    @JsonProperty("LyDoHuyThiHanh")
    @Field(value = "LyDoHuyThiHanh", order = 11)
    public String lyDoHuyThiHanh = null;

    @Getter
    @Setter
    public static class HieuLucThiHanh {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_HINH_THUC_XU_PHAT;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class HoanMienGiam {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.S_HOAN_MIEN_GIAM;

        @JsonProperty("LoaiQuyetDinh")
        @Field(value = "LoaiQuyetDinh", order = 1)
        public Integer loaiQuyetDinh = 0;

        @JsonProperty("NgayQuyetDinh")
        @Field(value = "NgayQuyetDinh", order = 2)
        @JsonSerialize(using = CustomUTCDateSerializer.class)
        @JsonDeserialize(using = CustomUTCDateDeserializer.class)
        @Schema(type = "string", format = "date-time")
        public Long ngayQuyetDinh = null;

        @JsonProperty("SoQuyetDinh")
        @Field(value = "SoQuyetDinh", order = 3)
        public String soQuyetDinh = StringPool.BLANK;

        @JsonProperty("SoTienMienGiam")
        @Field(value = "SoTienMienGiam", order = 4)
        public Long soTienMienGiam = null;

        @JsonProperty("SoTienPhatSauMienGiam")
        @Field(value = "SoTienPhatSauMienGiam", order = 5)
        public Long soTienPhatSauMienGiam = null;

        @JsonProperty("ThoiHanHoanNop")
        @Field(value = "ThoiHanHoanNop", order = 6)
        @JsonSerialize(using = CustomUTCDateSerializer.class)
        @JsonDeserialize(using = CustomUTCDateDeserializer.class)
        @Schema(type = "string", format = "date-time")
        public Long thoiHanHoanNop = null;
    }

    @Getter
    @Setter
    public static class ThongTinNopTien {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.S_THONG_TIN_NOP_TIEN;

        @JsonProperty("IDThongTinNopTien")
        @Field(value = "IDThongTinNopTien", order = 1)
        public String idThongTinNopTien = String.valueOf(System.currentTimeMillis());

        @JsonProperty("NgayNop")
        @Field(value = "NgayNop", order = 2)
        @JsonSerialize(using = CustomUTCDateSerializer.class)
        @JsonDeserialize(using = CustomUTCDateDeserializer.class)
        @Schema(type = "string", format = "date-time")
        public Long ngayNop = null;

        @JsonProperty("SoTienNop")
        @Field(value = "SoTienNop", order = 3)

        public Long soTienNop = 0L;

        @JsonProperty("SoChungTu")
        @Field(value = "SoChungTu", order = 4)
        public String soChungTu = StringPool.BLANK;
    }

    public Long getSoTienPhatPhaiNop() {

        long tongSoTienPhat = Validator.isNull(this.tongSoTienPhat) ? 0L : this.tongSoTienPhat;
        long soTienHoanGiam = 0L;
        long soTienDaNop = 0L;

        if (!this.getHoanMienGiam().isEmpty()) {
            for (HoanMienGiam hoanMienGiam : this.getHoanMienGiam()) {
                if (hoanMienGiam.getLoaiQuyetDinh() != 1)
                    soTienHoanGiam += Validator.isNull(hoanMienGiam.getSoTienMienGiam()) ? 0L : hoanMienGiam.getSoTienMienGiam();
            }
        }

        if (!this.getThongTinNopTien().isEmpty()) {
            for (ThongTinNopTien thongTinNopTien : this.getThongTinNopTien()) {
                soTienDaNop += thongTinNopTien.getSoTienNop();
            }
        }

        long result = tongSoTienPhat - soTienHoanGiam - soTienDaNop;
        if (result < 0) {
            return 0L;
        }
        return result;
    }
}
