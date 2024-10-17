package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.NghiDinhXPVPHC;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSNghiDinhXPVPHC;
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
public class RDBMSNghiDinhXPVPHCMapperImpl implements RDBMSNghiDinhXPVPHCMapper {

    @Override
    public RDBMSNghiDinhXPVPHC convert(NghiDinhXPVPHC data) {
        if ( data == null ) {
            return null;
        }

        RDBMSNghiDinhXPVPHC rDBMSNghiDinhXPVPHC = new RDBMSNghiDinhXPVPHC();

        rDBMSNghiDinhXPVPHC.setPrimKey( data.getPrimKey() );
        rDBMSNghiDinhXPVPHC.setUpdate( data.isUpdate() );
        rDBMSNghiDinhXPVPHC.setThoiGianTao( data.getThoiGianTao() );
        rDBMSNghiDinhXPVPHC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSNghiDinhXPVPHC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSNghiDinhXPVPHC.setMaMuc( data.getMaMuc() );
        rDBMSNghiDinhXPVPHC.setTenMuc( data.getTenMuc() );
        rDBMSNghiDinhXPVPHC.setMaPhienBan( data.getMaPhienBan() );
        rDBMSNghiDinhXPVPHC.setType( data.getType() );
        rDBMSNghiDinhXPVPHC.isUpdate = data.isUpdate;

        handleTransientField( rDBMSNghiDinhXPVPHC, data );

        return rDBMSNghiDinhXPVPHC;
    }

    @Override
    public NghiDinhXPVPHC convert(RDBMSNghiDinhXPVPHC data) {
        if ( data == null ) {
            return null;
        }

        NghiDinhXPVPHC nghiDinhXPVPHC = new NghiDinhXPVPHC();

        nghiDinhXPVPHC.setPrimKey( data.getPrimKey() );
        nghiDinhXPVPHC.setUpdate( data.isUpdate() );
        nghiDinhXPVPHC.setThoiGianTao( data.getThoiGianTao() );
        nghiDinhXPVPHC.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        nghiDinhXPVPHC.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        nghiDinhXPVPHC.setMaMuc( data.getMaMuc() );
        nghiDinhXPVPHC.setTenMuc( data.getTenMuc() );
        nghiDinhXPVPHC.setMaPhienBan( data.getMaPhienBan() );
        nghiDinhXPVPHC.setId( data.getId() );
        nghiDinhXPVPHC.setType( data.getType() );
        nghiDinhXPVPHC.isUpdate = data.isUpdate;

        handleTransientField( nghiDinhXPVPHC, data );

        return nghiDinhXPVPHC;
    }

    @Override
    public List<NghiDinhXPVPHC> convert(List<RDBMSNghiDinhXPVPHC> data) {
        if ( data == null ) {
            return null;
        }

        List<NghiDinhXPVPHC> list = new ArrayList<NghiDinhXPVPHC>( data.size() );
        for ( RDBMSNghiDinhXPVPHC rDBMSNghiDinhXPVPHC : data ) {
            list.add( convert( rDBMSNghiDinhXPVPHC ) );
        }

        return list;
    }
}
