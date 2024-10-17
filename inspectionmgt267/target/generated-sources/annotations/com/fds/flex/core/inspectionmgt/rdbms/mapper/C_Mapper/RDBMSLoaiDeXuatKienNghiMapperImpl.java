package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiDeXuatKienNghi;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiDeXuatKienNghi;
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
public class RDBMSLoaiDeXuatKienNghiMapperImpl implements RDBMSLoaiDeXuatKienNghiMapper {

    @Override
    public RDBMSLoaiDeXuatKienNghi convert(LoaiDeXuatKienNghi data) {
        if ( data == null ) {
            return null;
        }

        RDBMSLoaiDeXuatKienNghi rDBMSLoaiDeXuatKienNghi = new RDBMSLoaiDeXuatKienNghi();

        rDBMSLoaiDeXuatKienNghi.setPrimKey( data.getPrimKey() );
        rDBMSLoaiDeXuatKienNghi.setUpdate( data.isUpdate() );
        rDBMSLoaiDeXuatKienNghi.setThoiGianTao( data.getThoiGianTao() );
        rDBMSLoaiDeXuatKienNghi.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSLoaiDeXuatKienNghi.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSLoaiDeXuatKienNghi.setMaMuc( data.getMaMuc() );
        rDBMSLoaiDeXuatKienNghi.setTenMuc( data.getTenMuc() );
        rDBMSLoaiDeXuatKienNghi.setMaPhienBan( data.getMaPhienBan() );
        rDBMSLoaiDeXuatKienNghi.setType( data.getType() );
        rDBMSLoaiDeXuatKienNghi.isUpdate = data.isUpdate;

        handleTransientField( rDBMSLoaiDeXuatKienNghi, data );

        return rDBMSLoaiDeXuatKienNghi;
    }

    @Override
    public LoaiDeXuatKienNghi convert(RDBMSLoaiDeXuatKienNghi data) {
        if ( data == null ) {
            return null;
        }

        LoaiDeXuatKienNghi loaiDeXuatKienNghi = new LoaiDeXuatKienNghi();

        loaiDeXuatKienNghi.setPrimKey( data.getPrimKey() );
        loaiDeXuatKienNghi.setUpdate( data.isUpdate() );
        loaiDeXuatKienNghi.setThoiGianTao( data.getThoiGianTao() );
        loaiDeXuatKienNghi.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        loaiDeXuatKienNghi.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        loaiDeXuatKienNghi.setMaMuc( data.getMaMuc() );
        loaiDeXuatKienNghi.setTenMuc( data.getTenMuc() );
        loaiDeXuatKienNghi.setMaPhienBan( data.getMaPhienBan() );
        loaiDeXuatKienNghi.setId( data.getId() );
        loaiDeXuatKienNghi.setType( data.getType() );
        loaiDeXuatKienNghi.isUpdate = data.isUpdate;

        handleTransientField( loaiDeXuatKienNghi, data );

        return loaiDeXuatKienNghi;
    }

    @Override
    public List<LoaiDeXuatKienNghi> convert(List<RDBMSLoaiDeXuatKienNghi> data) {
        if ( data == null ) {
            return null;
        }

        List<LoaiDeXuatKienNghi> list = new ArrayList<LoaiDeXuatKienNghi>( data.size() );
        for ( RDBMSLoaiDeXuatKienNghi rDBMSLoaiDeXuatKienNghi : data ) {
            list.add( convert( rDBMSLoaiDeXuatKienNghi ) );
        }

        return list;
    }
}
