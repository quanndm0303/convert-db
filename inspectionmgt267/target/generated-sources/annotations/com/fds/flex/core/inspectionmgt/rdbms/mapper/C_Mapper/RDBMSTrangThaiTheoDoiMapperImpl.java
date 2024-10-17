package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiTheoDoi;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiTheoDoi;
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
public class RDBMSTrangThaiTheoDoiMapperImpl implements RDBMSTrangThaiTheoDoiMapper {

    @Override
    public RDBMSTrangThaiTheoDoi convert(TrangThaiTheoDoi data) {
        if ( data == null ) {
            return null;
        }

        RDBMSTrangThaiTheoDoi rDBMSTrangThaiTheoDoi = new RDBMSTrangThaiTheoDoi();

        rDBMSTrangThaiTheoDoi.setPrimKey( data.getPrimKey() );
        rDBMSTrangThaiTheoDoi.setUpdate( data.isUpdate() );
        rDBMSTrangThaiTheoDoi.setThoiGianTao( data.getThoiGianTao() );
        rDBMSTrangThaiTheoDoi.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSTrangThaiTheoDoi.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSTrangThaiTheoDoi.setMaMuc( data.getMaMuc() );
        rDBMSTrangThaiTheoDoi.setTenMuc( data.getTenMuc() );
        rDBMSTrangThaiTheoDoi.setMaPhienBan( data.getMaPhienBan() );
        rDBMSTrangThaiTheoDoi.setType( data.getType() );
        rDBMSTrangThaiTheoDoi.isUpdate = data.isUpdate;

        handleTransientField( rDBMSTrangThaiTheoDoi, data );

        return rDBMSTrangThaiTheoDoi;
    }

    @Override
    public TrangThaiTheoDoi convert(RDBMSTrangThaiTheoDoi data) {
        if ( data == null ) {
            return null;
        }

        TrangThaiTheoDoi trangThaiTheoDoi = new TrangThaiTheoDoi();

        trangThaiTheoDoi.setPrimKey( data.getPrimKey() );
        trangThaiTheoDoi.setUpdate( data.isUpdate() );
        trangThaiTheoDoi.setThoiGianTao( data.getThoiGianTao() );
        trangThaiTheoDoi.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        trangThaiTheoDoi.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        trangThaiTheoDoi.setMaMuc( data.getMaMuc() );
        trangThaiTheoDoi.setTenMuc( data.getTenMuc() );
        trangThaiTheoDoi.setMaPhienBan( data.getMaPhienBan() );
        trangThaiTheoDoi.setId( data.getId() );
        trangThaiTheoDoi.setType( data.getType() );
        trangThaiTheoDoi.isUpdate = data.isUpdate;

        handleTransientField( trangThaiTheoDoi, data );

        return trangThaiTheoDoi;
    }

    @Override
    public List<TrangThaiTheoDoi> convert(List<RDBMSTrangThaiTheoDoi> data) {
        if ( data == null ) {
            return null;
        }

        List<TrangThaiTheoDoi> list = new ArrayList<TrangThaiTheoDoi>( data.size() );
        for ( RDBMSTrangThaiTheoDoi rDBMSTrangThaiTheoDoi : data ) {
            list.add( convert( rDBMSTrangThaiTheoDoi ) );
        }

        return list;
    }
}
