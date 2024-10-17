package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LinhVucChuyenNganh;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLinhVucChuyenNganh;
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
public class RDBMSLinhVucChuyenNganhMapperImpl implements RDBMSLinhVucChuyenNganhMapper {

    @Override
    public RDBMSLinhVucChuyenNganh convert(LinhVucChuyenNganh data) {
        if ( data == null ) {
            return null;
        }

        RDBMSLinhVucChuyenNganh rDBMSLinhVucChuyenNganh = new RDBMSLinhVucChuyenNganh();

        rDBMSLinhVucChuyenNganh.setPrimKey( data.getPrimKey() );
        rDBMSLinhVucChuyenNganh.setUpdate( data.isUpdate() );
        rDBMSLinhVucChuyenNganh.setThoiGianTao( data.getThoiGianTao() );
        rDBMSLinhVucChuyenNganh.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSLinhVucChuyenNganh.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSLinhVucChuyenNganh.setMaMuc( data.getMaMuc() );
        rDBMSLinhVucChuyenNganh.setTenMuc( data.getTenMuc() );
        rDBMSLinhVucChuyenNganh.setMaPhienBan( data.getMaPhienBan() );
        rDBMSLinhVucChuyenNganh.setType( data.getType() );
        rDBMSLinhVucChuyenNganh.isUpdate = data.isUpdate;

        handleTransientField( rDBMSLinhVucChuyenNganh, data );

        return rDBMSLinhVucChuyenNganh;
    }

    @Override
    public LinhVucChuyenNganh convert(RDBMSLinhVucChuyenNganh data) {
        if ( data == null ) {
            return null;
        }

        LinhVucChuyenNganh linhVucChuyenNganh = new LinhVucChuyenNganh();

        linhVucChuyenNganh.setPrimKey( data.getPrimKey() );
        linhVucChuyenNganh.setUpdate( data.isUpdate() );
        linhVucChuyenNganh.setThoiGianTao( data.getThoiGianTao() );
        linhVucChuyenNganh.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        linhVucChuyenNganh.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        linhVucChuyenNganh.setMaMuc( data.getMaMuc() );
        linhVucChuyenNganh.setTenMuc( data.getTenMuc() );
        linhVucChuyenNganh.setMaPhienBan( data.getMaPhienBan() );
        linhVucChuyenNganh.setId( data.getId() );
        linhVucChuyenNganh.setType( data.getType() );
        linhVucChuyenNganh.isUpdate = data.isUpdate;

        handleTransientField( linhVucChuyenNganh, data );

        return linhVucChuyenNganh;
    }

    @Override
    public List<LinhVucChuyenNganh> convert(List<RDBMSLinhVucChuyenNganh> data) {
        if ( data == null ) {
            return null;
        }

        List<LinhVucChuyenNganh> list = new ArrayList<LinhVucChuyenNganh>( data.size() );
        for ( RDBMSLinhVucChuyenNganh rDBMSLinhVucChuyenNganh : data ) {
            list.add( convert( rDBMSLinhVucChuyenNganh ) );
        }

        return list;
    }
}
