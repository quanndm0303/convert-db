package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HieuLucThiHanh;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSHieuLucThiHanh;
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
public class RDBMSHieuLucThiHanhMapperImpl implements RDBMSHieuLucThiHanhMapper {

    @Override
    public RDBMSHieuLucThiHanh convert(HieuLucThiHanh data) {
        if ( data == null ) {
            return null;
        }

        RDBMSHieuLucThiHanh rDBMSHieuLucThiHanh = new RDBMSHieuLucThiHanh();

        rDBMSHieuLucThiHanh.setPrimKey( data.getPrimKey() );
        rDBMSHieuLucThiHanh.setUpdate( data.isUpdate() );
        rDBMSHieuLucThiHanh.setThoiGianTao( data.getThoiGianTao() );
        rDBMSHieuLucThiHanh.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSHieuLucThiHanh.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSHieuLucThiHanh.setMaMuc( data.getMaMuc() );
        rDBMSHieuLucThiHanh.setTenMuc( data.getTenMuc() );
        rDBMSHieuLucThiHanh.setMaPhienBan( data.getMaPhienBan() );
        rDBMSHieuLucThiHanh.setType( data.getType() );
        rDBMSHieuLucThiHanh.isUpdate = data.isUpdate;

        handleTransientField( rDBMSHieuLucThiHanh, data );

        return rDBMSHieuLucThiHanh;
    }

    @Override
    public HieuLucThiHanh convert(RDBMSHieuLucThiHanh data) {
        if ( data == null ) {
            return null;
        }

        HieuLucThiHanh hieuLucThiHanh = new HieuLucThiHanh();

        hieuLucThiHanh.setPrimKey( data.getPrimKey() );
        hieuLucThiHanh.setUpdate( data.isUpdate() );
        hieuLucThiHanh.setThoiGianTao( data.getThoiGianTao() );
        hieuLucThiHanh.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        hieuLucThiHanh.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        hieuLucThiHanh.setMaMuc( data.getMaMuc() );
        hieuLucThiHanh.setTenMuc( data.getTenMuc() );
        hieuLucThiHanh.setMaPhienBan( data.getMaPhienBan() );
        hieuLucThiHanh.setId( data.getId() );
        hieuLucThiHanh.setType( data.getType() );
        hieuLucThiHanh.isUpdate = data.isUpdate;

        handleTransientField( hieuLucThiHanh, data );

        return hieuLucThiHanh;
    }

    @Override
    public List<HieuLucThiHanh> convert(List<RDBMSHieuLucThiHanh> data) {
        if ( data == null ) {
            return null;
        }

        List<HieuLucThiHanh> list = new ArrayList<HieuLucThiHanh>( data.size() );
        for ( RDBMSHieuLucThiHanh rDBMSHieuLucThiHanh : data ) {
            list.add( convert( rDBMSHieuLucThiHanh ) );
        }

        return list;
    }
}
