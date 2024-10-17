package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucXuLyChinh;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHinhThucXuLyChinh;
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
public class RDBMSHinhThucXuLyChinhMapperImpl implements RDBMSHinhThucXuLyChinhMapper {

    @Override
    public RDBMSHinhThucXuLyChinh convert(HinhThucXuLyChinh data) {
        if ( data == null ) {
            return null;
        }

        RDBMSHinhThucXuLyChinh rDBMSHinhThucXuLyChinh = new RDBMSHinhThucXuLyChinh();

        rDBMSHinhThucXuLyChinh.setPrimKey( data.getPrimKey() );
        rDBMSHinhThucXuLyChinh.setUpdate( data.isUpdate() );
        rDBMSHinhThucXuLyChinh.setThoiGianTao( data.getThoiGianTao() );
        rDBMSHinhThucXuLyChinh.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSHinhThucXuLyChinh.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSHinhThucXuLyChinh.setMaMuc( data.getMaMuc() );
        rDBMSHinhThucXuLyChinh.setTenMuc( data.getTenMuc() );
        rDBMSHinhThucXuLyChinh.setMaPhienBan( data.getMaPhienBan() );
        rDBMSHinhThucXuLyChinh.setType( data.getType() );
        rDBMSHinhThucXuLyChinh.isUpdate = data.isUpdate;

        handleTransientField( rDBMSHinhThucXuLyChinh, data );

        return rDBMSHinhThucXuLyChinh;
    }

    @Override
    public HinhThucXuLyChinh convert(RDBMSHinhThucXuLyChinh data) {
        if ( data == null ) {
            return null;
        }

        HinhThucXuLyChinh hinhThucXuLyChinh = new HinhThucXuLyChinh();

        hinhThucXuLyChinh.setPrimKey( data.getPrimKey() );
        hinhThucXuLyChinh.setUpdate( data.isUpdate() );
        hinhThucXuLyChinh.setThoiGianTao( data.getThoiGianTao() );
        hinhThucXuLyChinh.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        hinhThucXuLyChinh.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        hinhThucXuLyChinh.setMaMuc( data.getMaMuc() );
        hinhThucXuLyChinh.setTenMuc( data.getTenMuc() );
        hinhThucXuLyChinh.setMaPhienBan( data.getMaPhienBan() );
        hinhThucXuLyChinh.setId( data.getId() );
        hinhThucXuLyChinh.setType( data.getType() );
        hinhThucXuLyChinh.isUpdate = data.isUpdate;

        handleTransientField( hinhThucXuLyChinh, data );

        return hinhThucXuLyChinh;
    }

    @Override
    public List<HinhThucXuLyChinh> convert(List<RDBMSHinhThucXuLyChinh> data) {
        if ( data == null ) {
            return null;
        }

        List<HinhThucXuLyChinh> list = new ArrayList<HinhThucXuLyChinh>( data.size() );
        for ( RDBMSHinhThucXuLyChinh rDBMSHinhThucXuLyChinh : data ) {
            list.add( convert( rDBMSHinhThucXuLyChinh ) );
        }

        return list;
    }
}
