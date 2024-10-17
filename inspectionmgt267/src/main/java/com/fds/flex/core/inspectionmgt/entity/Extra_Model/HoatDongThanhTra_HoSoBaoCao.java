package com.fds.flex.core.inspectionmgt.entity.Extra_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fds.flex.common.customformat.CustomUTCDateDeserializer;
import com.fds.flex.common.customformat.CustomUTCDateSerializer;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.core.inspectionmgt.entity.S_Model.TepDuLieu;
import com.fds.flex.modelbuilder.constant.DBConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoatDongThanhTra_HoSoBaoCao {
    @JsonProperty("@type")
    @Field(value = DBConstant.TYPE, order = 0)
    public String type = DBConstant.T_HO_SO_BAO_CAO;

    @JsonProperty("MaDinhDanh")
    @Field(value = "MaDinhDanh", order = 1)
    public String maDinhDanh = StringPool.BLANK;

    @Transient
    @JsonProperty("MauCheDoBaoCao")
    public HoSoBaoCao_MauCheDoBaoCao mauCheDoBaoCao;

    @Transient
    @JsonProperty("LinhVucBaoCao")
    public HoSoBaoCao_LinhVucBaoCao linhVucBaoCao;

    @Transient
    @JsonProperty("NhomChuyenDeBaoCao")
    public HoSoBaoCao_NhomChuyenDeBaoCao nhomChuyenDeBaoCao;

    @Transient
    @JsonProperty("CapLapBaoCao")
    public HoSoBaoCao_CapLapBaoCao capLapBaoCao;

    @Transient
    @JsonProperty("LoaiBaoCaoDinhKy")
    public HoSoBaoCao_LoaiBaoCaoDinhKy loaiBaoCaoDinhKy;

    @Transient
    @JsonProperty("TenBaoCao")
    public String tenBaoCao;

    @Transient
    @JsonProperty("NamBaoCao")
    public Integer namBaoCao;

    @Transient
    @JsonProperty("QuyBaoCao")
    public Integer quyBaoCao;

    @Transient
    @JsonProperty("ThangBaoCao")
    public Integer thangBaoCao;

    @Transient
    @JsonProperty("TuanBaoCao")
    public Integer tuanBaoCao;

    @Transient
    @JsonProperty("NgayBaoCao")
    public Integer ngayBaoCao;

    @Transient
    @JsonProperty("ThoiHanBaoCao")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long thoiHanBaoCao;

    @Transient
    @JsonProperty("DonViBaoCao")
    public HoSoBaoCao_DonViBaoCao donViBaoCao;

    @Transient
    @JsonProperty("NoiNhanBaoCao")
    public List<HoSoBaoCao_NoiNhanBaoCao> noiNhanBaoCao;

    @Transient
    @JsonProperty("NgayBanHanh")
    @JsonSerialize(using = CustomUTCDateSerializer.class)
    @JsonDeserialize(using = CustomUTCDateDeserializer.class)
    @Schema(type = "string", format = "date-time")
    public Long ngayBanHanh;

    @Transient
    @JsonProperty("SoHieuVanBan")
    public String soHieuVanBan;

    @Transient
    @JsonProperty("TrangThaiHoSoBaoCao")
    public HoSoBaoCao_TrangThaiHoSoBaoCao trangThaiHoSoBaoCao;

    @Transient
    @JsonProperty("TepDuLieu")
    public List<TepDuLieu> tepDuLieu;

    @Transient
    @JsonProperty("GiayToLuuTruSo")
    public HoSoBaoCao_GiayToLuuTruSo giayToLuuTruSo;

}
