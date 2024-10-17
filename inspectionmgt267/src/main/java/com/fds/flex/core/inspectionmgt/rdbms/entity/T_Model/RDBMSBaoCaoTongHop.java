package com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "BaoCaoTongHop")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDBMSBaoCaoTongHop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Id")
    private Long id;

    @JsonProperty("MaLopDuLieu")
    @Column(name = "MaLopDuLieu", nullable = false)
    public Long maLopDuLieu = 0L;

    @JsonProperty("LopDuLieu")
    @Column(name = "LopDuLieu")
    public String lopDuLieu = null;

    @JsonProperty("NgayDuLieu")
    @Column(name = "NgayDuLieu")
    public Long ngayDuLieu = null;

    @JsonProperty("MaDuLieu")
    @Column(name = "MaDuLieu")
    public String maDuLieu = null;

    @JsonProperty("TruongDuLieu")
    @Column(name = "TruongDuLieu")
    public String truongDuLieu = null;

    @JsonProperty("GiaTriDuLieuDangSo")
    @Column(name = "GiaTriDuLieuDangSo")
    public Long giaTriDuLieuDangSo = null;

    @JsonProperty("GiaTriDuLieuDangThapPhan")
    @Column(name = "GiaTriDuLieuDangThapPhan")
    public Double giaTriDuLieuDangThapPhan = null;

    @JsonProperty("GiaTriDuLieuDangChu")
    @Column(name = "GiaTriDuLieuDangChu")
    public String giaTriDuLieuDangChu = null;

    @JsonProperty("DuLieuThamChieu")
    @Column(name = "DuLieuThamChieu")
    public String duLieuThamChieu = null;

    public RDBMSBaoCaoTongHop(Long maLopDuLieu, String lopDuLieu, Long ngayDuLieu, String maDuLieu, String truongDuLieu, Long giaTriDuLieuDangSo, String giaTriDuLieuDangChu, Double giaTriDuLieuDangThapPhan, String duLieuThamChieu) {
        this.maLopDuLieu = maLopDuLieu;
        this.lopDuLieu = lopDuLieu;
        this.ngayDuLieu = ngayDuLieu;
        this.maDuLieu = maDuLieu;
        this.truongDuLieu = truongDuLieu;
        this.giaTriDuLieuDangSo = giaTriDuLieuDangSo;
        this.giaTriDuLieuDangChu = giaTriDuLieuDangChu;
        this.giaTriDuLieuDangThapPhan = giaTriDuLieuDangThapPhan;
        this.duLieuThamChieu = duLieuThamChieu;
    }

    public RDBMSBaoCaoTongHop() {

    }
}
