package com.fds.flex.core.inspectionmgt.rdbms.mapper.T_Mapper;

import com.fds.flex.core.inspectionmgt.entity.Extra_Model.XuLyVPHC_CanBoTheoDoi;
import com.fds.flex.core.inspectionmgt.entity.S_Model.CuongCheThiHanh;
import com.fds.flex.core.inspectionmgt.entity.S_Model.KhacPhucHauQua;
import com.fds.flex.core.inspectionmgt.entity.S_Model.KhieuNaiKhoiKien;
import com.fds.flex.core.inspectionmgt.entity.S_Model.NoiDungVPHC;
import com.fds.flex.core.inspectionmgt.entity.S_Model.TamDinhChiThiHanh;
import com.fds.flex.core.inspectionmgt.entity.S_Model.ThanhPhanGiayTo;
import com.fds.flex.core.inspectionmgt.entity.S_Model.XuLyPhatBoSung;
import com.fds.flex.core.inspectionmgt.entity.T_Model.XuLyVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSXuLyVPHC;
import com.fds.flex.modelbuilder.entity.Common_Model.ChiaSeTaiKhoan;
import com.fds.flex.modelbuilder.entity.Common_Model.ChiaSeVaiTro;
import com.fds.flex.modelbuilder.entity.Common_Model.NguonThamChieu;
import com.fds.flex.modelbuilder.entity.Common_Model.NhatKiSuaDoi;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-09T15:33:21+0700",
    comments = "version: 1.6.0, compiler: javac, environment: Java 1.8.0_422 (Amazon.com Inc.)"
)
@Component
public class RDBMSXuLyVPHCMapperImpl implements RDBMSXuLyVPHCMapper {

    @Override
    public RDBMSXuLyVPHC convert(XuLyVPHC data) {
        if ( data == null ) {
            return null;
        }

        RDBMSXuLyVPHC rDBMSXuLyVPHC = new RDBMSXuLyVPHC();

        rDBMSXuLyVPHC.setPrimKey( data.getPrimKey() );
        rDBMSXuLyVPHC.setUpdate( data.isUpdate() );
        rDBMSXuLyVPHC.setUuid( data.getUuid() );
        rDBMSXuLyVPHC.setThoiGianTao( data.getThoiGianTao() );
        rDBMSXuLyVPHC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSXuLyVPHC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSXuLyVPHC.setNguoiTaoLap( data.getNguoiTaoLap() );
        rDBMSXuLyVPHC.setChuSoHuu( data.getChuSoHuu() );
        List<NguonThamChieu> list = data.getNguonThamChieu();
        if ( list != null ) {
            rDBMSXuLyVPHC.setNguonThamChieu( new ArrayList<NguonThamChieu>( list ) );
        }
        List<NhatKiSuaDoi> list1 = data.getNhatKiSuaDoi();
        if ( list1 != null ) {
            rDBMSXuLyVPHC.setNhatKiSuaDoi( new ArrayList<NhatKiSuaDoi>( list1 ) );
        }
        rDBMSXuLyVPHC.setLienKetURL( data.getLienKetURL() );
        List<ChiaSeTaiKhoan> list2 = data.getChiaSeTaiKhoan();
        if ( list2 != null ) {
            rDBMSXuLyVPHC.setChiaSeTaiKhoan( new ArrayList<ChiaSeTaiKhoan>( list2 ) );
        }
        List<ChiaSeVaiTro> list3 = data.getChiaSeVaiTro();
        if ( list3 != null ) {
            rDBMSXuLyVPHC.setChiaSeVaiTro( new ArrayList<ChiaSeVaiTro>( list3 ) );
        }
        rDBMSXuLyVPHC.setMaPhienBan( data.getMaPhienBan() );
        rDBMSXuLyVPHC.setMaDinhDanhThayThe( data.getMaDinhDanhThayThe() );
        rDBMSXuLyVPHC.setPhanVungDuLieu( data.getPhanVungDuLieu() );
        rDBMSXuLyVPHC.setType( data.getType() );
        rDBMSXuLyVPHC.isUpdate = data.isUpdate;
        rDBMSXuLyVPHC.maDinhDanh = data.maDinhDanh;
        rDBMSXuLyVPHC.coQuanQuanLy = data.coQuanQuanLy;
        rDBMSXuLyVPHC.donViChuTri = data.donViChuTri;
        List<XuLyVPHC_CanBoTheoDoi> list4 = data.canBoTheoDoi;
        if ( list4 != null ) {
            rDBMSXuLyVPHC.canBoTheoDoi = new ArrayList<XuLyVPHC_CanBoTheoDoi>( list4 );
        }
        rDBMSXuLyVPHC.ngayLapBienBan = data.ngayLapBienBan;
        rDBMSXuLyVPHC.soBienBanVPHC = data.soBienBanVPHC;
        rDBMSXuLyVPHC.tiepNhanVuViec = data.tiepNhanVuViec;
        rDBMSXuLyVPHC.doiTuongVPHC = data.doiTuongVPHC;
        List<NoiDungVPHC> list5 = data.noiDungVPHC;
        if ( list5 != null ) {
            rDBMSXuLyVPHC.noiDungVPHC = new ArrayList<NoiDungVPHC>( list5 );
        }
        rDBMSXuLyVPHC.tinhTietTangNang = data.tinhTietTangNang;
        rDBMSXuLyVPHC.tinhTietGiamNhe = data.tinhTietGiamNhe;
        rDBMSXuLyVPHC.soQuyetDinh = data.soQuyetDinh;
        rDBMSXuLyVPHC.ngayQuyetDinh = data.ngayQuyetDinh;
        rDBMSXuLyVPHC.ngayHieuLuc = data.ngayHieuLuc;
        rDBMSXuLyVPHC.coQuanQuyetDinh = data.coQuanQuyetDinh;
        rDBMSXuLyVPHC.hinhThucXuLyChinh = data.hinhThucXuLyChinh;
        rDBMSXuLyVPHC.noiDungLyDoXuLy = data.noiDungLyDoXuLy;
        rDBMSXuLyVPHC.giayToTamGiu = data.giayToTamGiu;
        rDBMSXuLyVPHC.bienPhapNganChanDamBao = data.bienPhapNganChanDamBao;
        rDBMSXuLyVPHC.ngayNhanQuyetDinh = data.ngayNhanQuyetDinh;
        rDBMSXuLyVPHC.truongHopPhatTien = data.truongHopPhatTien;
        List<XuLyPhatBoSung> list6 = data.xuLyPhatBoSung;
        if ( list6 != null ) {
            rDBMSXuLyVPHC.xuLyPhatBoSung = new ArrayList<XuLyPhatBoSung>( list6 );
        }
        List<KhacPhucHauQua> list7 = data.khacPhucHauQua;
        if ( list7 != null ) {
            rDBMSXuLyVPHC.khacPhucHauQua = new ArrayList<KhacPhucHauQua>( list7 );
        }
        rDBMSXuLyVPHC.thoiHanChapHanh = data.thoiHanChapHanh;
        List<CuongCheThiHanh> list8 = data.cuongCheThiHanh;
        if ( list8 != null ) {
            rDBMSXuLyVPHC.cuongCheThiHanh = new ArrayList<CuongCheThiHanh>( list8 );
        }
        List<TamDinhChiThiHanh> list9 = data.tamDinhChiThiHanh;
        if ( list9 != null ) {
            rDBMSXuLyVPHC.tamDinhChiThiHanh = new ArrayList<TamDinhChiThiHanh>( list9 );
        }
        List<KhieuNaiKhoiKien> list10 = data.khieuNaiKhoiKien;
        if ( list10 != null ) {
            rDBMSXuLyVPHC.khieuNaiKhoiKien = new ArrayList<KhieuNaiKhoiKien>( list10 );
        }
        rDBMSXuLyVPHC.chuyenCoQuanThiHanh = data.chuyenCoQuanThiHanh;
        rDBMSXuLyVPHC.chuyenCoQuanDieuTra = data.chuyenCoQuanDieuTra;
        rDBMSXuLyVPHC.trangThaiXuLyVPHC = data.trangThaiXuLyVPHC;
        rDBMSXuLyVPHC.ngayKetThuc = data.ngayKetThuc;
        rDBMSXuLyVPHC.lyDoHuyThiHanh = data.lyDoHuyThiHanh;
        List<ThanhPhanGiayTo> list11 = data.thanhPhanGiayTo;
        if ( list11 != null ) {
            rDBMSXuLyVPHC.thanhPhanGiayTo = new ArrayList<ThanhPhanGiayTo>( list11 );
        }

        handleTransientField( rDBMSXuLyVPHC, data );

        return rDBMSXuLyVPHC;
    }

    @Override
    public XuLyVPHC convert(RDBMSXuLyVPHC data) {
        if ( data == null ) {
            return null;
        }

        XuLyVPHC xuLyVPHC = new XuLyVPHC();

        xuLyVPHC.setPrimKey( data.getPrimKey() );
        xuLyVPHC.setUpdate( data.isUpdate() );
        xuLyVPHC.setUuid( data.getUuid() );
        xuLyVPHC.setThoiGianTao( data.getThoiGianTao() );
        xuLyVPHC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        xuLyVPHC.setNguoiTaoLap( data.getNguoiTaoLap() );
        xuLyVPHC.setChuSoHuu( data.getChuSoHuu() );
        xuLyVPHC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        xuLyVPHC.setMaPhienBan( data.getMaPhienBan() );
        List<NguonThamChieu> list = data.getNguonThamChieu();
        if ( list != null ) {
            xuLyVPHC.setNguonThamChieu( new ArrayList<NguonThamChieu>( list ) );
        }
        List<NhatKiSuaDoi> list1 = data.getNhatKiSuaDoi();
        if ( list1 != null ) {
            xuLyVPHC.setNhatKiSuaDoi( new ArrayList<NhatKiSuaDoi>( list1 ) );
        }
        xuLyVPHC.setLienKetURL( data.getLienKetURL() );
        xuLyVPHC.setMaDinhDanhThayThe( data.getMaDinhDanhThayThe() );
        xuLyVPHC.setPhanVungDuLieu( data.getPhanVungDuLieu() );
        List<ChiaSeTaiKhoan> list2 = data.getChiaSeTaiKhoan();
        if ( list2 != null ) {
            xuLyVPHC.setChiaSeTaiKhoan( new ArrayList<ChiaSeTaiKhoan>( list2 ) );
        }
        List<ChiaSeVaiTro> list3 = data.getChiaSeVaiTro();
        if ( list3 != null ) {
            xuLyVPHC.setChiaSeVaiTro( new ArrayList<ChiaSeVaiTro>( list3 ) );
        }
        xuLyVPHC.setId( data.getId() );
        xuLyVPHC.setType( data.getType() );
        xuLyVPHC.isUpdate = data.isUpdate;
        xuLyVPHC.maDinhDanh = data.maDinhDanh;
        xuLyVPHC.coQuanQuanLy = data.coQuanQuanLy;
        xuLyVPHC.donViChuTri = data.donViChuTri;
        List<XuLyVPHC_CanBoTheoDoi> list4 = data.canBoTheoDoi;
        if ( list4 != null ) {
            xuLyVPHC.canBoTheoDoi = new ArrayList<XuLyVPHC_CanBoTheoDoi>( list4 );
        }
        xuLyVPHC.ngayLapBienBan = data.ngayLapBienBan;
        xuLyVPHC.soBienBanVPHC = data.soBienBanVPHC;
        xuLyVPHC.tiepNhanVuViec = data.tiepNhanVuViec;
        xuLyVPHC.doiTuongVPHC = data.doiTuongVPHC;
        List<NoiDungVPHC> list5 = data.noiDungVPHC;
        if ( list5 != null ) {
            xuLyVPHC.noiDungVPHC = new ArrayList<NoiDungVPHC>( list5 );
        }
        xuLyVPHC.tinhTietTangNang = data.tinhTietTangNang;
        xuLyVPHC.tinhTietGiamNhe = data.tinhTietGiamNhe;
        xuLyVPHC.soQuyetDinh = data.soQuyetDinh;
        xuLyVPHC.ngayQuyetDinh = data.ngayQuyetDinh;
        xuLyVPHC.ngayHieuLuc = data.ngayHieuLuc;
        xuLyVPHC.coQuanQuyetDinh = data.coQuanQuyetDinh;
        xuLyVPHC.hinhThucXuLyChinh = data.hinhThucXuLyChinh;
        xuLyVPHC.noiDungLyDoXuLy = data.noiDungLyDoXuLy;
        xuLyVPHC.giayToTamGiu = data.giayToTamGiu;
        xuLyVPHC.bienPhapNganChanDamBao = data.bienPhapNganChanDamBao;
        xuLyVPHC.ngayNhanQuyetDinh = data.ngayNhanQuyetDinh;
        xuLyVPHC.truongHopPhatTien = data.truongHopPhatTien;
        List<XuLyPhatBoSung> list6 = data.xuLyPhatBoSung;
        if ( list6 != null ) {
            xuLyVPHC.xuLyPhatBoSung = new ArrayList<XuLyPhatBoSung>( list6 );
        }
        List<KhacPhucHauQua> list7 = data.khacPhucHauQua;
        if ( list7 != null ) {
            xuLyVPHC.khacPhucHauQua = new ArrayList<KhacPhucHauQua>( list7 );
        }
        xuLyVPHC.thoiHanChapHanh = data.thoiHanChapHanh;
        List<CuongCheThiHanh> list8 = data.cuongCheThiHanh;
        if ( list8 != null ) {
            xuLyVPHC.cuongCheThiHanh = new ArrayList<CuongCheThiHanh>( list8 );
        }
        List<TamDinhChiThiHanh> list9 = data.tamDinhChiThiHanh;
        if ( list9 != null ) {
            xuLyVPHC.tamDinhChiThiHanh = new ArrayList<TamDinhChiThiHanh>( list9 );
        }
        List<KhieuNaiKhoiKien> list10 = data.khieuNaiKhoiKien;
        if ( list10 != null ) {
            xuLyVPHC.khieuNaiKhoiKien = new ArrayList<KhieuNaiKhoiKien>( list10 );
        }
        xuLyVPHC.chuyenCoQuanThiHanh = data.chuyenCoQuanThiHanh;
        xuLyVPHC.chuyenCoQuanDieuTra = data.chuyenCoQuanDieuTra;
        xuLyVPHC.trangThaiXuLyVPHC = data.trangThaiXuLyVPHC;
        xuLyVPHC.ngayKetThuc = data.ngayKetThuc;
        xuLyVPHC.lyDoHuyThiHanh = data.lyDoHuyThiHanh;
        List<ThanhPhanGiayTo> list11 = data.thanhPhanGiayTo;
        if ( list11 != null ) {
            xuLyVPHC.thanhPhanGiayTo = new ArrayList<ThanhPhanGiayTo>( list11 );
        }

        handleTransientField( xuLyVPHC, data );

        return xuLyVPHC;
    }

    @Override
    public List<XuLyVPHC> convert(List<RDBMSXuLyVPHC> data) {
        if ( data == null ) {
            return null;
        }

        List<XuLyVPHC> list = new ArrayList<XuLyVPHC>( data.size() );
        for ( RDBMSXuLyVPHC rDBMSXuLyVPHC : data ) {
            list.add( convert( rDBMSXuLyVPHC ) );
        }

        return list;
    }
}
