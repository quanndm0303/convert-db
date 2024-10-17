package com.fds.flex.core.inspectionmgt.rdbms.mapper.T_Mapper;

import com.fds.flex.core.inspectionmgt.entity.Extra_Model.ThongBaoKetLuan_XuLyVPHC;
import com.fds.flex.core.inspectionmgt.entity.S_Model.DeXuatKienNghi;
import com.fds.flex.core.inspectionmgt.entity.T_Model.ThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSThongBaoKetLuan;
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
    date = "2024-10-09T15:33:22+0700",
    comments = "version: 1.6.0, compiler: javac, environment: Java 1.8.0_422 (Amazon.com Inc.)"
)
@Component
public class RDBMSThongBaoKetLuanMapperImpl implements RDBMSThongBaoKetLuanMapper {

    @Override
    public RDBMSThongBaoKetLuan convert(ThongBaoKetLuan data) {
        if ( data == null ) {
            return null;
        }

        RDBMSThongBaoKetLuan rDBMSThongBaoKetLuan = new RDBMSThongBaoKetLuan();

        rDBMSThongBaoKetLuan.setPrimKey( data.getPrimKey() );
        rDBMSThongBaoKetLuan.setUpdate( data.isUpdate() );
        rDBMSThongBaoKetLuan.setUuid( data.getUuid() );
        rDBMSThongBaoKetLuan.setThoiGianTao( data.getThoiGianTao() );
        rDBMSThongBaoKetLuan.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSThongBaoKetLuan.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSThongBaoKetLuan.setNguoiTaoLap( data.getNguoiTaoLap() );
        rDBMSThongBaoKetLuan.setChuSoHuu( data.getChuSoHuu() );
        List<NguonThamChieu> list = data.getNguonThamChieu();
        if ( list != null ) {
            rDBMSThongBaoKetLuan.setNguonThamChieu( new ArrayList<NguonThamChieu>( list ) );
        }
        List<NhatKiSuaDoi> list1 = data.getNhatKiSuaDoi();
        if ( list1 != null ) {
            rDBMSThongBaoKetLuan.setNhatKiSuaDoi( new ArrayList<NhatKiSuaDoi>( list1 ) );
        }
        rDBMSThongBaoKetLuan.setLienKetURL( data.getLienKetURL() );
        List<ChiaSeTaiKhoan> list2 = data.getChiaSeTaiKhoan();
        if ( list2 != null ) {
            rDBMSThongBaoKetLuan.setChiaSeTaiKhoan( new ArrayList<ChiaSeTaiKhoan>( list2 ) );
        }
        List<ChiaSeVaiTro> list3 = data.getChiaSeVaiTro();
        if ( list3 != null ) {
            rDBMSThongBaoKetLuan.setChiaSeVaiTro( new ArrayList<ChiaSeVaiTro>( list3 ) );
        }
        rDBMSThongBaoKetLuan.setMaPhienBan( data.getMaPhienBan() );
        rDBMSThongBaoKetLuan.setMaDinhDanhThayThe( data.getMaDinhDanhThayThe() );
        rDBMSThongBaoKetLuan.setPhanVungDuLieu( data.getPhanVungDuLieu() );
        rDBMSThongBaoKetLuan.setType( data.getType() );
        rDBMSThongBaoKetLuan.isUpdate = data.isUpdate;
        rDBMSThongBaoKetLuan.maDinhDanh = data.maDinhDanh;
        rDBMSThongBaoKetLuan.doiTuongKetLuan = data.doiTuongKetLuan;
        rDBMSThongBaoKetLuan.loaiThongBaoKetLuan = data.loaiThongBaoKetLuan;
        rDBMSThongBaoKetLuan.loaiGiayKetLuan = data.loaiGiayKetLuan;
        rDBMSThongBaoKetLuan.trichYeuVanBan = data.trichYeuVanBan;
        rDBMSThongBaoKetLuan.soHieuVanBan = data.soHieuVanBan;
        rDBMSThongBaoKetLuan.ngayVanBan = data.ngayVanBan;
        rDBMSThongBaoKetLuan.coQuanBanHanh = data.coQuanBanHanh;
        rDBMSThongBaoKetLuan.tepDuLieu = data.tepDuLieu;
        rDBMSThongBaoKetLuan.coQuanQuanLy = data.coQuanQuanLy;
        rDBMSThongBaoKetLuan.donViChuTri = data.donViChuTri;
        rDBMSThongBaoKetLuan.canBoTheoDoi = data.canBoTheoDoi;
        rDBMSThongBaoKetLuan.ngayTheoDoi = data.ngayTheoDoi;
        rDBMSThongBaoKetLuan.ghiChuKetLuan = data.ghiChuKetLuan;
        List<DeXuatKienNghi> list4 = data.deXuatKienNghi;
        if ( list4 != null ) {
            rDBMSThongBaoKetLuan.deXuatKienNghi = new ArrayList<DeXuatKienNghi>( list4 );
        }
        List<ThongBaoKetLuan_XuLyVPHC> list5 = data.xuLyVPHC;
        if ( list5 != null ) {
            rDBMSThongBaoKetLuan.xuLyVPHC = new ArrayList<ThongBaoKetLuan_XuLyVPHC>( list5 );
        }
        rDBMSThongBaoKetLuan.trangThaiTheoDoi = data.trangThaiTheoDoi;
        rDBMSThongBaoKetLuan.ngayKetThuc = data.ngayKetThuc;

        handleTransientField( rDBMSThongBaoKetLuan, data );

        return rDBMSThongBaoKetLuan;
    }

    @Override
    public ThongBaoKetLuan convert(RDBMSThongBaoKetLuan data) {
        if ( data == null ) {
            return null;
        }

        ThongBaoKetLuan thongBaoKetLuan = new ThongBaoKetLuan();

        thongBaoKetLuan.setPrimKey( data.getPrimKey() );
        thongBaoKetLuan.setUpdate( data.isUpdate() );
        thongBaoKetLuan.setUuid( data.getUuid() );
        thongBaoKetLuan.setThoiGianTao( data.getThoiGianTao() );
        thongBaoKetLuan.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        thongBaoKetLuan.setNguoiTaoLap( data.getNguoiTaoLap() );
        thongBaoKetLuan.setChuSoHuu( data.getChuSoHuu() );
        thongBaoKetLuan.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        thongBaoKetLuan.setMaPhienBan( data.getMaPhienBan() );
        List<NguonThamChieu> list = data.getNguonThamChieu();
        if ( list != null ) {
            thongBaoKetLuan.setNguonThamChieu( new ArrayList<NguonThamChieu>( list ) );
        }
        List<NhatKiSuaDoi> list1 = data.getNhatKiSuaDoi();
        if ( list1 != null ) {
            thongBaoKetLuan.setNhatKiSuaDoi( new ArrayList<NhatKiSuaDoi>( list1 ) );
        }
        thongBaoKetLuan.setLienKetURL( data.getLienKetURL() );
        thongBaoKetLuan.setMaDinhDanhThayThe( data.getMaDinhDanhThayThe() );
        thongBaoKetLuan.setPhanVungDuLieu( data.getPhanVungDuLieu() );
        List<ChiaSeTaiKhoan> list2 = data.getChiaSeTaiKhoan();
        if ( list2 != null ) {
            thongBaoKetLuan.setChiaSeTaiKhoan( new ArrayList<ChiaSeTaiKhoan>( list2 ) );
        }
        List<ChiaSeVaiTro> list3 = data.getChiaSeVaiTro();
        if ( list3 != null ) {
            thongBaoKetLuan.setChiaSeVaiTro( new ArrayList<ChiaSeVaiTro>( list3 ) );
        }
        thongBaoKetLuan.setId( data.getId() );
        thongBaoKetLuan.setType( data.getType() );
        thongBaoKetLuan.isUpdate = data.isUpdate;
        thongBaoKetLuan.maDinhDanh = data.maDinhDanh;
        thongBaoKetLuan.coQuanQuanLy = data.coQuanQuanLy;
        thongBaoKetLuan.donViChuTri = data.donViChuTri;
        thongBaoKetLuan.canBoTheoDoi = data.canBoTheoDoi;
        thongBaoKetLuan.doiTuongKetLuan = data.doiTuongKetLuan;
        thongBaoKetLuan.loaiThongBaoKetLuan = data.loaiThongBaoKetLuan;
        thongBaoKetLuan.loaiGiayKetLuan = data.loaiGiayKetLuan;
        thongBaoKetLuan.coQuanBanHanh = data.coQuanBanHanh;
        thongBaoKetLuan.trichYeuVanBan = data.trichYeuVanBan;
        thongBaoKetLuan.soHieuVanBan = data.soHieuVanBan;
        thongBaoKetLuan.ngayVanBan = data.ngayVanBan;
        thongBaoKetLuan.ngayTheoDoi = data.ngayTheoDoi;
        thongBaoKetLuan.ghiChuKetLuan = data.ghiChuKetLuan;
        List<DeXuatKienNghi> list4 = data.deXuatKienNghi;
        if ( list4 != null ) {
            thongBaoKetLuan.deXuatKienNghi = new ArrayList<DeXuatKienNghi>( list4 );
        }
        List<ThongBaoKetLuan_XuLyVPHC> list5 = data.xuLyVPHC;
        if ( list5 != null ) {
            thongBaoKetLuan.xuLyVPHC = new ArrayList<ThongBaoKetLuan_XuLyVPHC>( list5 );
        }
        thongBaoKetLuan.trangThaiTheoDoi = data.trangThaiTheoDoi;
        thongBaoKetLuan.ngayKetThuc = data.ngayKetThuc;
        thongBaoKetLuan.tepDuLieu = data.tepDuLieu;

        handleTransientField( thongBaoKetLuan, data );

        return thongBaoKetLuan;
    }

    @Override
    public List<ThongBaoKetLuan> convert(List<RDBMSThongBaoKetLuan> data) {
        if ( data == null ) {
            return null;
        }

        List<ThongBaoKetLuan> list = new ArrayList<ThongBaoKetLuan>( data.size() );
        for ( RDBMSThongBaoKetLuan rDBMSThongBaoKetLuan : data ) {
            list.add( convert( rDBMSThongBaoKetLuan ) );
        }

        return list;
    }
}
