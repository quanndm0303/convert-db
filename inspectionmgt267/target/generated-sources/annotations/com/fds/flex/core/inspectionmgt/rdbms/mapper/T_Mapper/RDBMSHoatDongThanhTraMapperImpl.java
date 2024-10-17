package com.fds.flex.core.inspectionmgt.rdbms.mapper.T_Mapper;

import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_CanBoTheoDoi;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_DoiTuongThanhTra;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_HoSoBaoCao;
import com.fds.flex.core.inspectionmgt.entity.S_Model.LichCongTacDoan;
import com.fds.flex.core.inspectionmgt.entity.S_Model.TepDuLieu;
import com.fds.flex.core.inspectionmgt.entity.S_Model.ThanhVienDoan;
import com.fds.flex.core.inspectionmgt.entity.T_Model.HoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSHoatDongThanhTra;
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
public class RDBMSHoatDongThanhTraMapperImpl implements RDBMSHoatDongThanhTraMapper {

    @Override
    public RDBMSHoatDongThanhTra convert(HoatDongThanhTra data) {
        if ( data == null ) {
            return null;
        }

        RDBMSHoatDongThanhTra rDBMSHoatDongThanhTra = new RDBMSHoatDongThanhTra();

        rDBMSHoatDongThanhTra.setPrimKey( data.getPrimKey() );
        rDBMSHoatDongThanhTra.setUpdate( data.isUpdate() );
        rDBMSHoatDongThanhTra.setUuid( data.getUuid() );
        rDBMSHoatDongThanhTra.setThoiGianTao( data.getThoiGianTao() );
        rDBMSHoatDongThanhTra.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSHoatDongThanhTra.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSHoatDongThanhTra.setNguoiTaoLap( data.getNguoiTaoLap() );
        rDBMSHoatDongThanhTra.setChuSoHuu( data.getChuSoHuu() );
        List<NguonThamChieu> list = data.getNguonThamChieu();
        if ( list != null ) {
            rDBMSHoatDongThanhTra.setNguonThamChieu( new ArrayList<NguonThamChieu>( list ) );
        }
        List<NhatKiSuaDoi> list1 = data.getNhatKiSuaDoi();
        if ( list1 != null ) {
            rDBMSHoatDongThanhTra.setNhatKiSuaDoi( new ArrayList<NhatKiSuaDoi>( list1 ) );
        }
        rDBMSHoatDongThanhTra.setLienKetURL( data.getLienKetURL() );
        List<ChiaSeTaiKhoan> list2 = data.getChiaSeTaiKhoan();
        if ( list2 != null ) {
            rDBMSHoatDongThanhTra.setChiaSeTaiKhoan( new ArrayList<ChiaSeTaiKhoan>( list2 ) );
        }
        List<ChiaSeVaiTro> list3 = data.getChiaSeVaiTro();
        if ( list3 != null ) {
            rDBMSHoatDongThanhTra.setChiaSeVaiTro( new ArrayList<ChiaSeVaiTro>( list3 ) );
        }
        rDBMSHoatDongThanhTra.setMaPhienBan( data.getMaPhienBan() );
        rDBMSHoatDongThanhTra.setMaDinhDanhThayThe( data.getMaDinhDanhThayThe() );
        rDBMSHoatDongThanhTra.setPhanVungDuLieu( data.getPhanVungDuLieu() );
        rDBMSHoatDongThanhTra.setType( data.getType() );
        rDBMSHoatDongThanhTra.isUpdate = data.isUpdate;
        rDBMSHoatDongThanhTra.maDinhDanh = data.maDinhDanh;
        rDBMSHoatDongThanhTra.tenHoatDong = data.tenHoatDong;
        rDBMSHoatDongThanhTra.loaiHoatDongThanhTra = data.loaiHoatDongThanhTra;
        rDBMSHoatDongThanhTra.loaiCheDoThanhTra = data.loaiCheDoThanhTra;
        rDBMSHoatDongThanhTra.nhiemVuCongViec = data.nhiemVuCongViec;
        rDBMSHoatDongThanhTra.soQuyetDinh = data.soQuyetDinh;
        rDBMSHoatDongThanhTra.ngayQuyetDinh = data.ngayQuyetDinh;
        rDBMSHoatDongThanhTra.coQuanBanHanh = data.coQuanBanHanh;
        List<TepDuLieu> list4 = data.tepDuLieu;
        if ( list4 != null ) {
            rDBMSHoatDongThanhTra.tepDuLieu = new ArrayList<TepDuLieu>( list4 );
        }
        rDBMSHoatDongThanhTra.giayToLuuTruSo = data.giayToLuuTruSo;
        rDBMSHoatDongThanhTra.coQuanQuanLy = data.coQuanQuanLy;
        rDBMSHoatDongThanhTra.donViChuTri = data.donViChuTri;
        List<HoatDongThanhTra_CanBoTheoDoi> list5 = data.canBoTheoDoi;
        if ( list5 != null ) {
            rDBMSHoatDongThanhTra.canBoTheoDoi = new ArrayList<HoatDongThanhTra_CanBoTheoDoi>( list5 );
        }
        rDBMSHoatDongThanhTra.linhVucChuyenNganh = data.linhVucChuyenNganh;
        rDBMSHoatDongThanhTra.noiDungHoatDong = data.noiDungHoatDong;
        rDBMSHoatDongThanhTra.thoiHanThucHien = data.thoiHanThucHien;
        rDBMSHoatDongThanhTra.giaHanThucHien = data.giaHanThucHien;
        List<HoatDongThanhTra_DoiTuongThanhTra> list6 = data.doiTuongThanhTra;
        if ( list6 != null ) {
            rDBMSHoatDongThanhTra.doiTuongThanhTra = new ArrayList<HoatDongThanhTra_DoiTuongThanhTra>( list6 );
        }
        List<ThanhVienDoan> list7 = data.thanhVienDoan;
        if ( list7 != null ) {
            rDBMSHoatDongThanhTra.thanhVienDoan = new ArrayList<ThanhVienDoan>( list7 );
        }
        List<LichCongTacDoan> list8 = data.lichCongTacDoan;
        if ( list8 != null ) {
            rDBMSHoatDongThanhTra.lichCongTacDoan = new ArrayList<LichCongTacDoan>( list8 );
        }
        rDBMSHoatDongThanhTra.trangThaiHoatDongThanhTra = data.trangThaiHoatDongThanhTra;
        rDBMSHoatDongThanhTra.ngayKetThuc = data.ngayKetThuc;
        rDBMSHoatDongThanhTra.hoSoNghiepVu = data.hoSoNghiepVu;
        rDBMSHoatDongThanhTra.huongDanChuyenDe = data.huongDanChuyenDe;
        List<HoatDongThanhTra_HoSoBaoCao> list9 = data.hoSoBaoCao;
        if ( list9 != null ) {
            rDBMSHoatDongThanhTra.hoSoBaoCao = new ArrayList<HoatDongThanhTra_HoSoBaoCao>( list9 );
        }
        rDBMSHoatDongThanhTra.capNhatDuLieu = data.capNhatDuLieu;

        handleTransientField( rDBMSHoatDongThanhTra, data );

        return rDBMSHoatDongThanhTra;
    }

    @Override
    public HoatDongThanhTra convert(RDBMSHoatDongThanhTra data) {
        if ( data == null ) {
            return null;
        }

        HoatDongThanhTra hoatDongThanhTra = new HoatDongThanhTra();

        hoatDongThanhTra.setPrimKey( data.getPrimKey() );
        hoatDongThanhTra.setUpdate( data.isUpdate() );
        hoatDongThanhTra.setUuid( data.getUuid() );
        hoatDongThanhTra.setThoiGianTao( data.getThoiGianTao() );
        hoatDongThanhTra.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        hoatDongThanhTra.setNguoiTaoLap( data.getNguoiTaoLap() );
        hoatDongThanhTra.setChuSoHuu( data.getChuSoHuu() );
        hoatDongThanhTra.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        hoatDongThanhTra.setMaPhienBan( data.getMaPhienBan() );
        List<NguonThamChieu> list = data.getNguonThamChieu();
        if ( list != null ) {
            hoatDongThanhTra.setNguonThamChieu( new ArrayList<NguonThamChieu>( list ) );
        }
        List<NhatKiSuaDoi> list1 = data.getNhatKiSuaDoi();
        if ( list1 != null ) {
            hoatDongThanhTra.setNhatKiSuaDoi( new ArrayList<NhatKiSuaDoi>( list1 ) );
        }
        hoatDongThanhTra.setLienKetURL( data.getLienKetURL() );
        hoatDongThanhTra.setMaDinhDanhThayThe( data.getMaDinhDanhThayThe() );
        hoatDongThanhTra.setPhanVungDuLieu( data.getPhanVungDuLieu() );
        List<ChiaSeTaiKhoan> list2 = data.getChiaSeTaiKhoan();
        if ( list2 != null ) {
            hoatDongThanhTra.setChiaSeTaiKhoan( new ArrayList<ChiaSeTaiKhoan>( list2 ) );
        }
        List<ChiaSeVaiTro> list3 = data.getChiaSeVaiTro();
        if ( list3 != null ) {
            hoatDongThanhTra.setChiaSeVaiTro( new ArrayList<ChiaSeVaiTro>( list3 ) );
        }
        hoatDongThanhTra.setId( data.getId() );
        hoatDongThanhTra.setType( data.getType() );
        hoatDongThanhTra.isUpdate = data.isUpdate;
        hoatDongThanhTra.maDinhDanh = data.maDinhDanh;
        hoatDongThanhTra.tenHoatDong = data.tenHoatDong;
        hoatDongThanhTra.loaiHoatDongThanhTra = data.loaiHoatDongThanhTra;
        hoatDongThanhTra.loaiCheDoThanhTra = data.loaiCheDoThanhTra;
        hoatDongThanhTra.nhiemVuCongViec = data.nhiemVuCongViec;
        hoatDongThanhTra.soQuyetDinh = data.soQuyetDinh;
        hoatDongThanhTra.ngayQuyetDinh = data.ngayQuyetDinh;
        hoatDongThanhTra.coQuanBanHanh = data.coQuanBanHanh;
        List<TepDuLieu> list4 = data.tepDuLieu;
        if ( list4 != null ) {
            hoatDongThanhTra.tepDuLieu = new ArrayList<TepDuLieu>( list4 );
        }
        hoatDongThanhTra.giayToLuuTruSo = data.giayToLuuTruSo;
        hoatDongThanhTra.coQuanQuanLy = data.coQuanQuanLy;
        hoatDongThanhTra.donViChuTri = data.donViChuTri;
        List<HoatDongThanhTra_CanBoTheoDoi> list5 = data.canBoTheoDoi;
        if ( list5 != null ) {
            hoatDongThanhTra.canBoTheoDoi = new ArrayList<HoatDongThanhTra_CanBoTheoDoi>( list5 );
        }
        hoatDongThanhTra.linhVucChuyenNganh = data.linhVucChuyenNganh;
        hoatDongThanhTra.noiDungHoatDong = data.noiDungHoatDong;
        hoatDongThanhTra.thoiHanThucHien = data.thoiHanThucHien;
        hoatDongThanhTra.giaHanThucHien = data.giaHanThucHien;
        List<HoatDongThanhTra_DoiTuongThanhTra> list6 = data.doiTuongThanhTra;
        if ( list6 != null ) {
            hoatDongThanhTra.doiTuongThanhTra = new ArrayList<HoatDongThanhTra_DoiTuongThanhTra>( list6 );
        }
        List<ThanhVienDoan> list7 = data.thanhVienDoan;
        if ( list7 != null ) {
            hoatDongThanhTra.thanhVienDoan = new ArrayList<ThanhVienDoan>( list7 );
        }
        List<LichCongTacDoan> list8 = data.lichCongTacDoan;
        if ( list8 != null ) {
            hoatDongThanhTra.lichCongTacDoan = new ArrayList<LichCongTacDoan>( list8 );
        }
        hoatDongThanhTra.trangThaiHoatDongThanhTra = data.trangThaiHoatDongThanhTra;
        hoatDongThanhTra.ngayKetThuc = data.ngayKetThuc;
        hoatDongThanhTra.hoSoNghiepVu = data.hoSoNghiepVu;
        hoatDongThanhTra.huongDanChuyenDe = data.huongDanChuyenDe;
        List<HoatDongThanhTra_HoSoBaoCao> list9 = data.hoSoBaoCao;
        if ( list9 != null ) {
            hoatDongThanhTra.hoSoBaoCao = new ArrayList<HoatDongThanhTra_HoSoBaoCao>( list9 );
        }
        hoatDongThanhTra.capNhatDuLieu = data.isCapNhatDuLieu();

        handleTransientField( hoatDongThanhTra, data );

        return hoatDongThanhTra;
    }

    @Override
    public List<HoatDongThanhTra> convert(List<RDBMSHoatDongThanhTra> data) {
        if ( data == null ) {
            return null;
        }

        List<HoatDongThanhTra> list = new ArrayList<HoatDongThanhTra>( data.size() );
        for ( RDBMSHoatDongThanhTra rDBMSHoatDongThanhTra : data ) {
            list.add( convert( rDBMSHoatDongThanhTra ) );
        }

        return list;
    }
}
