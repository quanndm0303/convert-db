package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HanhViVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHanhViVPHC;
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
public class RDBMSHanhViVPHCMapperImpl implements RDBMSHanhViVPHCMapper {

    @Override
    public RDBMSHanhViVPHC convert(HanhViVPHC data) {
        if ( data == null ) {
            return null;
        }

        RDBMSHanhViVPHC rDBMSHanhViVPHC = new RDBMSHanhViVPHC();

        rDBMSHanhViVPHC.setPrimKey( data.getPrimKey() );
        rDBMSHanhViVPHC.setUpdate( data.isUpdate() );
        rDBMSHanhViVPHC.setThoiGianTao( data.getThoiGianTao() );
        rDBMSHanhViVPHC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSHanhViVPHC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSHanhViVPHC.setMaMuc( data.getMaMuc() );
        rDBMSHanhViVPHC.setTenMuc( data.getTenMuc() );
        rDBMSHanhViVPHC.setMaPhienBan( data.getMaPhienBan() );
        rDBMSHanhViVPHC.setType( data.getType() );
        rDBMSHanhViVPHC.isUpdate = data.isUpdate;
        rDBMSHanhViVPHC.mucTienPhatDuoi = data.mucTienPhatDuoi;
        rDBMSHanhViVPHC.mucTienPhatTren = data.mucTienPhatTren;
        rDBMSHanhViVPHC.nhomHanhViVPHC = data.nhomHanhViVPHC;
        rDBMSHanhViVPHC.nghiDinhXPVPHC = data.nghiDinhXPVPHC;

        handleTransientField( rDBMSHanhViVPHC, data );

        return rDBMSHanhViVPHC;
    }

    @Override
    public HanhViVPHC convert(RDBMSHanhViVPHC data) {
        if ( data == null ) {
            return null;
        }

        HanhViVPHC hanhViVPHC = new HanhViVPHC();

        hanhViVPHC.setPrimKey( data.getPrimKey() );
        hanhViVPHC.setUpdate( data.isUpdate() );
        hanhViVPHC.setThoiGianTao( data.getThoiGianTao() );
        hanhViVPHC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        hanhViVPHC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        hanhViVPHC.setMaMuc( data.getMaMuc() );
        hanhViVPHC.setTenMuc( data.getTenMuc() );
        hanhViVPHC.setMaPhienBan( data.getMaPhienBan() );
        hanhViVPHC.setId( data.getId() );
        hanhViVPHC.setType( data.getType() );
        hanhViVPHC.isUpdate = data.isUpdate;
        hanhViVPHC.mucTienPhatDuoi = data.mucTienPhatDuoi;
        hanhViVPHC.mucTienPhatTren = data.mucTienPhatTren;
        hanhViVPHC.nhomHanhViVPHC = data.nhomHanhViVPHC;
        hanhViVPHC.nghiDinhXPVPHC = data.nghiDinhXPVPHC;

        handleTransientField( hanhViVPHC, data );

        return hanhViVPHC;
    }

    @Override
    public List<HanhViVPHC> convert(List<RDBMSHanhViVPHC> data) {
        if ( data == null ) {
            return null;
        }

        List<HanhViVPHC> list = new ArrayList<HanhViVPHC>( data.size() );
        for ( RDBMSHanhViVPHC rDBMSHanhViVPHC : data ) {
            list.add( convert( rDBMSHanhViVPHC ) );
        }

        return list;
    }
}
