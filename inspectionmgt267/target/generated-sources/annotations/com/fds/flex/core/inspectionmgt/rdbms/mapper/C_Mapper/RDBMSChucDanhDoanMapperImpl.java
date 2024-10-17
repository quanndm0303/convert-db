package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.ChucDanhDoan;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSChucDanhDoan;
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
public class RDBMSChucDanhDoanMapperImpl implements RDBMSChucDanhDoanMapper {

    @Override
    public RDBMSChucDanhDoan convert(ChucDanhDoan data) {
        if ( data == null ) {
            return null;
        }

        RDBMSChucDanhDoan rDBMSChucDanhDoan = new RDBMSChucDanhDoan();

        rDBMSChucDanhDoan.setPrimKey( data.getPrimKey() );
        rDBMSChucDanhDoan.setUpdate( data.isUpdate() );
        rDBMSChucDanhDoan.setThoiGianTao( data.getThoiGianTao() );
        rDBMSChucDanhDoan.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSChucDanhDoan.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSChucDanhDoan.setMaMuc( data.getMaMuc() );
        rDBMSChucDanhDoan.setTenMuc( data.getTenMuc() );
        rDBMSChucDanhDoan.setMaPhienBan( data.getMaPhienBan() );
        rDBMSChucDanhDoan.setType( data.getType() );
        rDBMSChucDanhDoan.isUpdate = data.isUpdate;

        handleTransientField( rDBMSChucDanhDoan, data );

        return rDBMSChucDanhDoan;
    }

    @Override
    public ChucDanhDoan convert(RDBMSChucDanhDoan data) {
        if ( data == null ) {
            return null;
        }

        ChucDanhDoan chucDanhDoan = new ChucDanhDoan();

        chucDanhDoan.setPrimKey( data.getPrimKey() );
        chucDanhDoan.setUpdate( data.isUpdate() );
        chucDanhDoan.setThoiGianTao( data.getThoiGianTao() );
        chucDanhDoan.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        chucDanhDoan.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        chucDanhDoan.setMaMuc( data.getMaMuc() );
        chucDanhDoan.setTenMuc( data.getTenMuc() );
        chucDanhDoan.setMaPhienBan( data.getMaPhienBan() );
        chucDanhDoan.setId( data.getId() );
        chucDanhDoan.setType( data.getType() );
        chucDanhDoan.isUpdate = data.isUpdate;

        handleTransientField( chucDanhDoan, data );

        return chucDanhDoan;
    }

    @Override
    public List<ChucDanhDoan> convert(List<RDBMSChucDanhDoan> data) {
        if ( data == null ) {
            return null;
        }

        List<ChucDanhDoan> list = new ArrayList<ChucDanhDoan>( data.size() );
        for ( RDBMSChucDanhDoan rDBMSChucDanhDoan : data ) {
            list.add( convert( rDBMSChucDanhDoan ) );
        }

        return list;
    }
}
