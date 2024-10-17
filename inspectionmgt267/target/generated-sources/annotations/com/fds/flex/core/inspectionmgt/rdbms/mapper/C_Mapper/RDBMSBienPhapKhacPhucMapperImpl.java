package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.BienPhapKhacPhuc;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSBienPhapKhacPhuc;
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
public class RDBMSBienPhapKhacPhucMapperImpl implements RDBMSBienPhapKhacPhucMapper {

    @Override
    public RDBMSBienPhapKhacPhuc convert(BienPhapKhacPhuc data) {
        if ( data == null ) {
            return null;
        }

        RDBMSBienPhapKhacPhuc rDBMSBienPhapKhacPhuc = new RDBMSBienPhapKhacPhuc();

        rDBMSBienPhapKhacPhuc.setPrimKey( data.getPrimKey() );
        rDBMSBienPhapKhacPhuc.setUpdate( data.isUpdate() );
        rDBMSBienPhapKhacPhuc.setThoiGianTao( data.getThoiGianTao() );
        rDBMSBienPhapKhacPhuc.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSBienPhapKhacPhuc.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSBienPhapKhacPhuc.setMaMuc( data.getMaMuc() );
        rDBMSBienPhapKhacPhuc.setTenMuc( data.getTenMuc() );
        rDBMSBienPhapKhacPhuc.setMaPhienBan( data.getMaPhienBan() );
        rDBMSBienPhapKhacPhuc.setType( data.getType() );
        rDBMSBienPhapKhacPhuc.isUpdate = data.isUpdate;

        handleTransientField( rDBMSBienPhapKhacPhuc, data );

        return rDBMSBienPhapKhacPhuc;
    }

    @Override
    public BienPhapKhacPhuc convert(RDBMSBienPhapKhacPhuc data) {
        if ( data == null ) {
            return null;
        }

        BienPhapKhacPhuc bienPhapKhacPhuc = new BienPhapKhacPhuc();

        bienPhapKhacPhuc.setPrimKey( data.getPrimKey() );
        bienPhapKhacPhuc.setUpdate( data.isUpdate() );
        bienPhapKhacPhuc.setThoiGianTao( data.getThoiGianTao() );
        bienPhapKhacPhuc.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        bienPhapKhacPhuc.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        bienPhapKhacPhuc.setMaMuc( data.getMaMuc() );
        bienPhapKhacPhuc.setTenMuc( data.getTenMuc() );
        bienPhapKhacPhuc.setMaPhienBan( data.getMaPhienBan() );
        bienPhapKhacPhuc.setId( data.getId() );
        bienPhapKhacPhuc.setType( data.getType() );
        bienPhapKhacPhuc.isUpdate = data.isUpdate;

        handleTransientField( bienPhapKhacPhuc, data );

        return bienPhapKhacPhuc;
    }

    @Override
    public List<BienPhapKhacPhuc> convert(List<RDBMSBienPhapKhacPhuc> data) {
        if ( data == null ) {
            return null;
        }

        List<BienPhapKhacPhuc> list = new ArrayList<BienPhapKhacPhuc>( data.size() );
        for ( RDBMSBienPhapKhacPhuc rDBMSBienPhapKhacPhuc : data ) {
            list.add( convert( rDBMSBienPhapKhacPhuc ) );
        }

        return list;
    }
}
