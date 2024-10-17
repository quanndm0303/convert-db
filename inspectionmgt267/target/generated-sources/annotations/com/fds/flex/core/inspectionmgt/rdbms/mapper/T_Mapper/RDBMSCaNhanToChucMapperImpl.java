package com.fds.flex.core.inspectionmgt.rdbms.mapper.T_Mapper;

import com.fds.flex.core.inspectionmgt.entity.Extra_Model.CaNhanToChuc_HoatDongThanhTraKiemTra;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.CaNhanToChuc_ThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.CaNhanToChuc_VuViecDonThu;
import com.fds.flex.core.inspectionmgt.entity.T_Model.CaNhanToChuc;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSCaNhanToChuc;
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
public class RDBMSCaNhanToChucMapperImpl implements RDBMSCaNhanToChucMapper {

    @Override
    public RDBMSCaNhanToChuc convert(CaNhanToChuc data) {
        if ( data == null ) {
            return null;
        }

        RDBMSCaNhanToChuc rDBMSCaNhanToChuc = new RDBMSCaNhanToChuc();

        rDBMSCaNhanToChuc.setPrimKey( data.getPrimKey() );
        rDBMSCaNhanToChuc.setUpdate( data.isUpdate() );
        rDBMSCaNhanToChuc.setUuid( data.getUuid() );
        rDBMSCaNhanToChuc.setThoiGianTao( data.getThoiGianTao() );
        rDBMSCaNhanToChuc.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSCaNhanToChuc.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSCaNhanToChuc.setNguoiTaoLap( data.getNguoiTaoLap() );
        rDBMSCaNhanToChuc.setChuSoHuu( data.getChuSoHuu() );
        List<NguonThamChieu> list = data.getNguonThamChieu();
        if ( list != null ) {
            rDBMSCaNhanToChuc.setNguonThamChieu( new ArrayList<NguonThamChieu>( list ) );
        }
        List<NhatKiSuaDoi> list1 = data.getNhatKiSuaDoi();
        if ( list1 != null ) {
            rDBMSCaNhanToChuc.setNhatKiSuaDoi( new ArrayList<NhatKiSuaDoi>( list1 ) );
        }
        rDBMSCaNhanToChuc.setLienKetURL( data.getLienKetURL() );
        List<ChiaSeTaiKhoan> list2 = data.getChiaSeTaiKhoan();
        if ( list2 != null ) {
            rDBMSCaNhanToChuc.setChiaSeTaiKhoan( new ArrayList<ChiaSeTaiKhoan>( list2 ) );
        }
        List<ChiaSeVaiTro> list3 = data.getChiaSeVaiTro();
        if ( list3 != null ) {
            rDBMSCaNhanToChuc.setChiaSeVaiTro( new ArrayList<ChiaSeVaiTro>( list3 ) );
        }
        rDBMSCaNhanToChuc.setMaPhienBan( data.getMaPhienBan() );
        rDBMSCaNhanToChuc.setMaDinhDanhThayThe( data.getMaDinhDanhThayThe() );
        rDBMSCaNhanToChuc.setPhanVungDuLieu( data.getPhanVungDuLieu() );
        rDBMSCaNhanToChuc.setType( data.getType() );
        rDBMSCaNhanToChuc.isUpdate = data.isUpdate;
        rDBMSCaNhanToChuc.maDinhDanh = data.maDinhDanh;
        rDBMSCaNhanToChuc.tenGoi = data.tenGoi;
        rDBMSCaNhanToChuc.loaiDoiTuongCNTC = data.loaiDoiTuongCNTC;
        rDBMSCaNhanToChuc.noiBoTrongNganh = data.noiBoTrongNganh;
        rDBMSCaNhanToChuc.giayToChungNhan = data.giayToChungNhan;
        rDBMSCaNhanToChuc.diaChi = data.diaChi;
        rDBMSCaNhanToChuc.ngaySinh = data.ngaySinh;
        rDBMSCaNhanToChuc.quocTich = data.quocTich;
        rDBMSCaNhanToChuc.canBoChienSi = data.canBoChienSi;
        rDBMSCaNhanToChuc.nguoiDaiDien = data.nguoiDaiDien;
        rDBMSCaNhanToChuc.danhBaLienLac = data.danhBaLienLac;
        rDBMSCaNhanToChuc.viThanhNien = data.viThanhNien;
        List<CaNhanToChuc_HoatDongThanhTraKiemTra> list4 = data.hoatDongThanhTraKiemTra;
        if ( list4 != null ) {
            rDBMSCaNhanToChuc.hoatDongThanhTraKiemTra = new ArrayList<CaNhanToChuc_HoatDongThanhTraKiemTra>( list4 );
        }
        List<CaNhanToChuc_ThongBaoKetLuan> list5 = data.thongBaoKetLuan;
        if ( list5 != null ) {
            rDBMSCaNhanToChuc.thongBaoKetLuan = new ArrayList<CaNhanToChuc_ThongBaoKetLuan>( list5 );
        }
        List<CaNhanToChuc_VuViecDonThu> list6 = data.vuViecDonThu;
        if ( list6 != null ) {
            rDBMSCaNhanToChuc.vuViecDonThu = new ArrayList<CaNhanToChuc_VuViecDonThu>( list6 );
        }

        handleTransientField( rDBMSCaNhanToChuc, data );

        return rDBMSCaNhanToChuc;
    }

    @Override
    public CaNhanToChuc convert(RDBMSCaNhanToChuc data) {
        if ( data == null ) {
            return null;
        }

        CaNhanToChuc caNhanToChuc = new CaNhanToChuc();

        caNhanToChuc.setPrimKey( data.getPrimKey() );
        caNhanToChuc.setUpdate( data.isUpdate() );
        caNhanToChuc.setUuid( data.getUuid() );
        caNhanToChuc.setThoiGianTao( data.getThoiGianTao() );
        caNhanToChuc.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        caNhanToChuc.setNguoiTaoLap( data.getNguoiTaoLap() );
        caNhanToChuc.setChuSoHuu( data.getChuSoHuu() );
        caNhanToChuc.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        caNhanToChuc.setMaPhienBan( data.getMaPhienBan() );
        List<NguonThamChieu> list = data.getNguonThamChieu();
        if ( list != null ) {
            caNhanToChuc.setNguonThamChieu( new ArrayList<NguonThamChieu>( list ) );
        }
        List<NhatKiSuaDoi> list1 = data.getNhatKiSuaDoi();
        if ( list1 != null ) {
            caNhanToChuc.setNhatKiSuaDoi( new ArrayList<NhatKiSuaDoi>( list1 ) );
        }
        caNhanToChuc.setLienKetURL( data.getLienKetURL() );
        caNhanToChuc.setMaDinhDanhThayThe( data.getMaDinhDanhThayThe() );
        caNhanToChuc.setPhanVungDuLieu( data.getPhanVungDuLieu() );
        List<ChiaSeTaiKhoan> list2 = data.getChiaSeTaiKhoan();
        if ( list2 != null ) {
            caNhanToChuc.setChiaSeTaiKhoan( new ArrayList<ChiaSeTaiKhoan>( list2 ) );
        }
        List<ChiaSeVaiTro> list3 = data.getChiaSeVaiTro();
        if ( list3 != null ) {
            caNhanToChuc.setChiaSeVaiTro( new ArrayList<ChiaSeVaiTro>( list3 ) );
        }
        caNhanToChuc.setId( data.getId() );
        caNhanToChuc.setType( data.getType() );
        caNhanToChuc.isUpdate = data.isUpdate;
        caNhanToChuc.maDinhDanh = data.maDinhDanh;
        caNhanToChuc.tenGoi = data.tenGoi;
        caNhanToChuc.loaiDoiTuongCNTC = data.loaiDoiTuongCNTC;
        caNhanToChuc.noiBoTrongNganh = data.noiBoTrongNganh;
        caNhanToChuc.giayToChungNhan = data.giayToChungNhan;
        caNhanToChuc.diaChi = data.diaChi;
        caNhanToChuc.ngaySinh = data.ngaySinh;
        caNhanToChuc.quocTich = data.quocTich;
        caNhanToChuc.canBoChienSi = data.canBoChienSi;
        caNhanToChuc.nguoiDaiDien = data.nguoiDaiDien;
        caNhanToChuc.danhBaLienLac = data.danhBaLienLac;
        caNhanToChuc.viThanhNien = data.viThanhNien;
        List<CaNhanToChuc_HoatDongThanhTraKiemTra> list4 = data.hoatDongThanhTraKiemTra;
        if ( list4 != null ) {
            caNhanToChuc.hoatDongThanhTraKiemTra = new ArrayList<CaNhanToChuc_HoatDongThanhTraKiemTra>( list4 );
        }
        List<CaNhanToChuc_ThongBaoKetLuan> list5 = data.thongBaoKetLuan;
        if ( list5 != null ) {
            caNhanToChuc.thongBaoKetLuan = new ArrayList<CaNhanToChuc_ThongBaoKetLuan>( list5 );
        }
        List<CaNhanToChuc_VuViecDonThu> list6 = data.vuViecDonThu;
        if ( list6 != null ) {
            caNhanToChuc.vuViecDonThu = new ArrayList<CaNhanToChuc_VuViecDonThu>( list6 );
        }

        handleTransientField( caNhanToChuc, data );

        return caNhanToChuc;
    }

    @Override
    public List<CaNhanToChuc> convert(List<RDBMSCaNhanToChuc> data) {
        if ( data == null ) {
            return null;
        }

        List<CaNhanToChuc> list = new ArrayList<CaNhanToChuc>( data.size() );
        for ( RDBMSCaNhanToChuc rDBMSCaNhanToChuc : data ) {
            list.add( convert( rDBMSCaNhanToChuc ) );
        }

        return list;
    }
}
