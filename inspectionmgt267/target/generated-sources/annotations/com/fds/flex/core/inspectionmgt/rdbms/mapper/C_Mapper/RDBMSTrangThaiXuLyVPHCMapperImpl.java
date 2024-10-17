package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiXuLyVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiXuLyVPHC;
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
public class RDBMSTrangThaiXuLyVPHCMapperImpl implements RDBMSTrangThaiXuLyVPHCMapper {

    @Override
    public RDBMSTrangThaiXuLyVPHC convert(TrangThaiXuLyVPHC data) {
        if ( data == null ) {
            return null;
        }

        RDBMSTrangThaiXuLyVPHC rDBMSTrangThaiXuLyVPHC = new RDBMSTrangThaiXuLyVPHC();

        rDBMSTrangThaiXuLyVPHC.setPrimKey( data.getPrimKey() );
        rDBMSTrangThaiXuLyVPHC.setUpdate( data.isUpdate() );
        rDBMSTrangThaiXuLyVPHC.setThoiGianTao( data.getThoiGianTao() );
        rDBMSTrangThaiXuLyVPHC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSTrangThaiXuLyVPHC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSTrangThaiXuLyVPHC.setMaMuc( data.getMaMuc() );
        rDBMSTrangThaiXuLyVPHC.setTenMuc( data.getTenMuc() );
        rDBMSTrangThaiXuLyVPHC.setMaPhienBan( data.getMaPhienBan() );
        rDBMSTrangThaiXuLyVPHC.setType( data.getType() );
        rDBMSTrangThaiXuLyVPHC.isUpdate = data.isUpdate;

        handleTransientField( rDBMSTrangThaiXuLyVPHC, data );

        return rDBMSTrangThaiXuLyVPHC;
    }

    @Override
    public TrangThaiXuLyVPHC convert(RDBMSTrangThaiXuLyVPHC data) {
        if ( data == null ) {
            return null;
        }

        TrangThaiXuLyVPHC trangThaiXuLyVPHC = new TrangThaiXuLyVPHC();

        trangThaiXuLyVPHC.setPrimKey( data.getPrimKey() );
        trangThaiXuLyVPHC.setUpdate( data.isUpdate() );
        trangThaiXuLyVPHC.setThoiGianTao( data.getThoiGianTao() );
        trangThaiXuLyVPHC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        trangThaiXuLyVPHC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        trangThaiXuLyVPHC.setMaMuc( data.getMaMuc() );
        trangThaiXuLyVPHC.setTenMuc( data.getTenMuc() );
        trangThaiXuLyVPHC.setMaPhienBan( data.getMaPhienBan() );
        trangThaiXuLyVPHC.setId( data.getId() );
        trangThaiXuLyVPHC.setType( data.getType() );
        trangThaiXuLyVPHC.isUpdate = data.isUpdate;

        handleTransientField( trangThaiXuLyVPHC, data );

        return trangThaiXuLyVPHC;
    }

    @Override
    public List<TrangThaiXuLyVPHC> convert(List<RDBMSTrangThaiXuLyVPHC> data) {
        if ( data == null ) {
            return null;
        }

        List<TrangThaiXuLyVPHC> list = new ArrayList<TrangThaiXuLyVPHC>( data.size() );
        for ( RDBMSTrangThaiXuLyVPHC rDBMSTrangThaiXuLyVPHC : data ) {
            list.add( convert( rDBMSTrangThaiXuLyVPHC ) );
        }

        return list;
    }
}
