package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.NhomHanhViVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSNhomHanhViVPHC;
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
public class RDBMSNhomHanhViVPHCMapperImpl implements RDBMSNhomHanhViVPHCMapper {

    @Override
    public RDBMSNhomHanhViVPHC convert(NhomHanhViVPHC data) {
        if ( data == null ) {
            return null;
        }

        RDBMSNhomHanhViVPHC rDBMSNhomHanhViVPHC = new RDBMSNhomHanhViVPHC();

        rDBMSNhomHanhViVPHC.setPrimKey( data.getPrimKey() );
        rDBMSNhomHanhViVPHC.setUpdate( data.isUpdate() );
        rDBMSNhomHanhViVPHC.setThoiGianTao( data.getThoiGianTao() );
        rDBMSNhomHanhViVPHC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSNhomHanhViVPHC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSNhomHanhViVPHC.setMaMuc( data.getMaMuc() );
        rDBMSNhomHanhViVPHC.setTenMuc( data.getTenMuc() );
        rDBMSNhomHanhViVPHC.setMaPhienBan( data.getMaPhienBan() );
        rDBMSNhomHanhViVPHC.setType( data.getType() );
        rDBMSNhomHanhViVPHC.isUpdate = data.isUpdate;

        handleTransientField( rDBMSNhomHanhViVPHC, data );

        return rDBMSNhomHanhViVPHC;
    }

    @Override
    public NhomHanhViVPHC convert(RDBMSNhomHanhViVPHC data) {
        if ( data == null ) {
            return null;
        }

        NhomHanhViVPHC nhomHanhViVPHC = new NhomHanhViVPHC();

        nhomHanhViVPHC.setPrimKey( data.getPrimKey() );
        nhomHanhViVPHC.setUpdate( data.isUpdate() );
        nhomHanhViVPHC.setThoiGianTao( data.getThoiGianTao() );
        nhomHanhViVPHC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        nhomHanhViVPHC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        nhomHanhViVPHC.setMaMuc( data.getMaMuc() );
        nhomHanhViVPHC.setTenMuc( data.getTenMuc() );
        nhomHanhViVPHC.setMaPhienBan( data.getMaPhienBan() );
        nhomHanhViVPHC.setId( data.getId() );
        nhomHanhViVPHC.setType( data.getType() );
        nhomHanhViVPHC.isUpdate = data.isUpdate;

        handleTransientField( nhomHanhViVPHC, data );

        return nhomHanhViVPHC;
    }

    @Override
    public List<NhomHanhViVPHC> convert(List<RDBMSNhomHanhViVPHC> data) {
        if ( data == null ) {
            return null;
        }

        List<NhomHanhViVPHC> list = new ArrayList<NhomHanhViVPHC>( data.size() );
        for ( RDBMSNhomHanhViVPHC rDBMSNhomHanhViVPHC : data ) {
            list.add( convert( rDBMSNhomHanhViVPHC ) );
        }

        return list;
    }
}
