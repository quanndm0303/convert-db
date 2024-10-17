package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiHoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSTrangThaiHoatDongThanhTra;
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
public class RDBMSTrangThaiHoatDongThanhTraMapperImpl implements RDBMSTrangThaiHoatDongThanhTraMapper {

    @Override
    public RDBMSTrangThaiHoatDongThanhTra convert(TrangThaiHoatDongThanhTra data) {
        if ( data == null ) {
            return null;
        }

        RDBMSTrangThaiHoatDongThanhTra rDBMSTrangThaiHoatDongThanhTra = new RDBMSTrangThaiHoatDongThanhTra();

        rDBMSTrangThaiHoatDongThanhTra.setPrimKey( data.getPrimKey() );
        rDBMSTrangThaiHoatDongThanhTra.setUpdate( data.isUpdate() );
        rDBMSTrangThaiHoatDongThanhTra.setThoiGianTao( data.getThoiGianTao() );
        rDBMSTrangThaiHoatDongThanhTra.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSTrangThaiHoatDongThanhTra.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSTrangThaiHoatDongThanhTra.setMaMuc( data.getMaMuc() );
        rDBMSTrangThaiHoatDongThanhTra.setTenMuc( data.getTenMuc() );
        rDBMSTrangThaiHoatDongThanhTra.setMaPhienBan( data.getMaPhienBan() );
        rDBMSTrangThaiHoatDongThanhTra.setType( data.getType() );
        rDBMSTrangThaiHoatDongThanhTra.isUpdate = data.isUpdate;

        handleTransientField( rDBMSTrangThaiHoatDongThanhTra, data );

        return rDBMSTrangThaiHoatDongThanhTra;
    }

    @Override
    public TrangThaiHoatDongThanhTra convert(RDBMSTrangThaiHoatDongThanhTra data) {
        if ( data == null ) {
            return null;
        }

        TrangThaiHoatDongThanhTra trangThaiHoatDongThanhTra = new TrangThaiHoatDongThanhTra();

        trangThaiHoatDongThanhTra.setPrimKey( data.getPrimKey() );
        trangThaiHoatDongThanhTra.setUpdate( data.isUpdate() );
        trangThaiHoatDongThanhTra.setThoiGianTao( data.getThoiGianTao() );
        trangThaiHoatDongThanhTra.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        trangThaiHoatDongThanhTra.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        trangThaiHoatDongThanhTra.setMaMuc( data.getMaMuc() );
        trangThaiHoatDongThanhTra.setTenMuc( data.getTenMuc() );
        trangThaiHoatDongThanhTra.setMaPhienBan( data.getMaPhienBan() );
        trangThaiHoatDongThanhTra.setId( data.getId() );
        trangThaiHoatDongThanhTra.setType( data.getType() );
        trangThaiHoatDongThanhTra.isUpdate = data.isUpdate;

        handleTransientField( trangThaiHoatDongThanhTra, data );

        return trangThaiHoatDongThanhTra;
    }

    @Override
    public List<TrangThaiHoatDongThanhTra> convert(List<RDBMSTrangThaiHoatDongThanhTra> data) {
        if ( data == null ) {
            return null;
        }

        List<TrangThaiHoatDongThanhTra> list = new ArrayList<TrangThaiHoatDongThanhTra>( data.size() );
        for ( RDBMSTrangThaiHoatDongThanhTra rDBMSTrangThaiHoatDongThanhTra : data ) {
            list.add( convert( rDBMSTrangThaiHoatDongThanhTra ) );
        }

        return list;
    }
}
