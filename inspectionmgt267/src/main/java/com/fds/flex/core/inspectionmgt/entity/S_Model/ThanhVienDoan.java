package com.fds.flex.core.inspectionmgt.entity.S_Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.modelbuilder.constant.DBConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class ThanhVienDoan {

    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.S_THANH_VIEN_DOAN;

    @JsonProperty("IDThanhVien")
    @Field(value = "IDThanhVien", order = 1)
    public String idThanhVien = String.valueOf(System.currentTimeMillis());

    @JsonProperty("CanBo")
    @Field(value = "CanBo", order = 2)
    public CanBo canBo = new CanBo();

    @JsonProperty("ChucDanhDoan")
    @Field(value = "ChucDanhDoan", order = 3)
    public ChucDanhDoan chucDanhDoan = new ChucDanhDoan(); //"Chức danh trong đoàn thanh tra/ đoàn kiểm tra 01: Trưởng đoàn 02: Tổ trưởng 03: Phó trưởng đoàn 04: Thư ký đoàn 05: Thành viên"

    @JsonProperty("GhiChuThamGia")
    @Field(value = "GhiChuThamGia", order = 4)
    public String ghiChuThamGia = StringPool.BLANK;

    @JsonProperty("CapNhatDuLieu")
    @Field(value = "CapNhatDuLieu", order = 5)
    public boolean capNhatDuLieu = false; //True: Được phép cập nhật dữ liệu về danh sách, lịch công tác đoàn và các kết luận (ngầm định dành cho trường đoàn, phó đoàn, thư ký đoàn)

    public void setIdThanhVien(String idThanhVien) {
        if (Validator.isNull(idThanhVien)) {
            this.idThanhVien = String.valueOf(System.currentTimeMillis());
        } else {
            this.idThanhVien = idThanhVien;
        }
    }

    @Getter
    @Setter
    public static class ChucDanhDoan {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.C_CHUC_DANH_DOAN;

        @JsonProperty("MaMuc")
        @Field(value = "MaMuc", order = 1)
        public String maMuc = StringPool.BLANK;

        @JsonProperty("TenMuc")
        @Field(value = "TenMuc", order = 2)
        public String tenMuc = StringPool.BLANK;
    }

    @Getter
    @Setter
    public static class CanBo {
        @JsonProperty("@type")
        @Field(value = DBConstant.TYPE, order = 0)
        public String type = DBConstant.T_CAN_BO;

        @JsonProperty("MaDinhDanh")
        @Field(value = "MaDinhDanh", order = 1)
        public String maDinhDanh = null;

        @JsonProperty("HoVaTen")
        @Field(value = "HoVaTen", order = 2)
        public String hoVaTen = StringPool.BLANK;

        @JsonProperty("MaSoCanBo")
        @Field(value = "MaSoCanBo", order = 3)
        public String maSoCanBo = null;

        @JsonProperty("ChucVu")
        @Field(value = "ChucVu", order = 4)
        public String chucVu = StringPool.BLANK;

        @JsonProperty("NoiCongTac")
        @Field(value = "NoiCongTac", order = 5)
        public String noiCongTac = StringPool.BLANK;

    }
}
