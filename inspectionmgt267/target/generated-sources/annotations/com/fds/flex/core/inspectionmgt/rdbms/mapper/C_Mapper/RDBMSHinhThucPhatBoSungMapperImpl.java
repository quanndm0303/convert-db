package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucPhatBoSung;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHinhThucPhatBoSung;
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
public class RDBMSHinhThucPhatBoSungMapperImpl implements RDBMSHinhThucPhatBoSungMapper {

    @Override
    public RDBMSHinhThucPhatBoSung convert(HinhThucPhatBoSung data) {
        if ( data == null ) {
            return null;
        }

        RDBMSHinhThucPhatBoSung rDBMSHinhThucPhatBoSung = new RDBMSHinhThucPhatBoSung();

        rDBMSHinhThucPhatBoSung.setPrimKey( data.getPrimKey() );
        rDBMSHinhThucPhatBoSung.setUpdate( data.isUpdate() );
        rDBMSHinhThucPhatBoSung.setThoiGianTao( data.getThoiGianTao() );
        rDBMSHinhThucPhatBoSung.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSHinhThucPhatBoSung.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSHinhThucPhatBoSung.setMaMuc( data.getMaMuc() );
        rDBMSHinhThucPhatBoSung.setTenMuc( data.getTenMuc() );
        rDBMSHinhThucPhatBoSung.setMaPhienBan( data.getMaPhienBan() );
        rDBMSHinhThucPhatBoSung.setType( data.getType() );
        rDBMSHinhThucPhatBoSung.isUpdate = data.isUpdate;

        handleTransientField( rDBMSHinhThucPhatBoSung, data );

        return rDBMSHinhThucPhatBoSung;
    }

    @Override
    public HinhThucPhatBoSung convert(RDBMSHinhThucPhatBoSung data) {
        if ( data == null ) {
            return null;
        }

        HinhThucPhatBoSung hinhThucPhatBoSung = new HinhThucPhatBoSung();

        hinhThucPhatBoSung.setPrimKey( data.getPrimKey() );
        hinhThucPhatBoSung.setUpdate( data.isUpdate() );
        hinhThucPhatBoSung.setThoiGianTao( data.getThoiGianTao() );
        hinhThucPhatBoSung.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        hinhThucPhatBoSung.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        hinhThucPhatBoSung.setMaMuc( data.getMaMuc() );
        hinhThucPhatBoSung.setTenMuc( data.getTenMuc() );
        hinhThucPhatBoSung.setMaPhienBan( data.getMaPhienBan() );
        hinhThucPhatBoSung.setId( data.getId() );
        hinhThucPhatBoSung.setType( data.getType() );
        hinhThucPhatBoSung.isUpdate = data.isUpdate;

        handleTransientField( hinhThucPhatBoSung, data );

        return hinhThucPhatBoSung;
    }

    @Override
    public List<HinhThucPhatBoSung> convert(List<RDBMSHinhThucPhatBoSung> data) {
        if ( data == null ) {
            return null;
        }

        List<HinhThucPhatBoSung> list = new ArrayList<HinhThucPhatBoSung>( data.size() );
        for ( RDBMSHinhThucPhatBoSung rDBMSHinhThucPhatBoSung : data ) {
            list.add( convert( rDBMSHinhThucPhatBoSung ) );
        }

        return list;
    }
}
