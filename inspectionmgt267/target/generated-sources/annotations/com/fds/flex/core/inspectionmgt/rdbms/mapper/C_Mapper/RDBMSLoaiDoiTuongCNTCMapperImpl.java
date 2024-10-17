package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiDoiTuongCNTC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiDoiTuongCNTC;
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
public class RDBMSLoaiDoiTuongCNTCMapperImpl implements RDBMSLoaiDoiTuongCNTCMapper {

    @Override
    public RDBMSLoaiDoiTuongCNTC convert(LoaiDoiTuongCNTC data) {
        if ( data == null ) {
            return null;
        }

        RDBMSLoaiDoiTuongCNTC rDBMSLoaiDoiTuongCNTC = new RDBMSLoaiDoiTuongCNTC();

        rDBMSLoaiDoiTuongCNTC.setPrimKey( data.getPrimKey() );
        rDBMSLoaiDoiTuongCNTC.setUpdate( data.isUpdate() );
        rDBMSLoaiDoiTuongCNTC.setThoiGianTao( data.getThoiGianTao() );
        rDBMSLoaiDoiTuongCNTC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSLoaiDoiTuongCNTC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSLoaiDoiTuongCNTC.setMaMuc( data.getMaMuc() );
        rDBMSLoaiDoiTuongCNTC.setTenMuc( data.getTenMuc() );
        rDBMSLoaiDoiTuongCNTC.setMaPhienBan( data.getMaPhienBan() );
        rDBMSLoaiDoiTuongCNTC.setType( data.getType() );
        rDBMSLoaiDoiTuongCNTC.isUpdate = data.isUpdate;
        rDBMSLoaiDoiTuongCNTC.thamChieu = data.thamChieu;

        handleTransientField( rDBMSLoaiDoiTuongCNTC, data );

        return rDBMSLoaiDoiTuongCNTC;
    }

    @Override
    public LoaiDoiTuongCNTC convert(RDBMSLoaiDoiTuongCNTC data) {
        if ( data == null ) {
            return null;
        }

        LoaiDoiTuongCNTC loaiDoiTuongCNTC = new LoaiDoiTuongCNTC();

        loaiDoiTuongCNTC.setPrimKey( data.getPrimKey() );
        loaiDoiTuongCNTC.setUpdate( data.isUpdate() );
        loaiDoiTuongCNTC.setThoiGianTao( data.getThoiGianTao() );
        loaiDoiTuongCNTC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        loaiDoiTuongCNTC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        loaiDoiTuongCNTC.setMaMuc( data.getMaMuc() );
        loaiDoiTuongCNTC.setTenMuc( data.getTenMuc() );
        loaiDoiTuongCNTC.setMaPhienBan( data.getMaPhienBan() );
        loaiDoiTuongCNTC.setId( data.getId() );
        loaiDoiTuongCNTC.setType( data.getType() );
        loaiDoiTuongCNTC.isUpdate = data.isUpdate;
        loaiDoiTuongCNTC.thamChieu = data.thamChieu;

        handleTransientField( loaiDoiTuongCNTC, data );

        return loaiDoiTuongCNTC;
    }

    @Override
    public List<LoaiDoiTuongCNTC> convert(List<RDBMSLoaiDoiTuongCNTC> data) {
        if ( data == null ) {
            return null;
        }

        List<LoaiDoiTuongCNTC> list = new ArrayList<LoaiDoiTuongCNTC>( data.size() );
        for ( RDBMSLoaiDoiTuongCNTC rDBMSLoaiDoiTuongCNTC : data ) {
            list.add( convert( rDBMSLoaiDoiTuongCNTC ) );
        }

        return list;
    }
}
