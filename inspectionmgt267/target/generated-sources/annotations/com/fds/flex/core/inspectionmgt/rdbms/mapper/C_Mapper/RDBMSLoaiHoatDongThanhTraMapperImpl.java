package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiHoatDongThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiHoatDongThanhTra;
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
public class RDBMSLoaiHoatDongThanhTraMapperImpl implements RDBMSLoaiHoatDongThanhTraMapper {

    @Override
    public RDBMSLoaiHoatDongThanhTra convert(LoaiHoatDongThanhTra data) {
        if ( data == null ) {
            return null;
        }

        RDBMSLoaiHoatDongThanhTra rDBMSLoaiHoatDongThanhTra = new RDBMSLoaiHoatDongThanhTra();

        rDBMSLoaiHoatDongThanhTra.setPrimKey( data.getPrimKey() );
        rDBMSLoaiHoatDongThanhTra.setUpdate( data.isUpdate() );
        rDBMSLoaiHoatDongThanhTra.setThoiGianTao( data.getThoiGianTao() );
        rDBMSLoaiHoatDongThanhTra.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSLoaiHoatDongThanhTra.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSLoaiHoatDongThanhTra.setMaMuc( data.getMaMuc() );
        rDBMSLoaiHoatDongThanhTra.setTenMuc( data.getTenMuc() );
        rDBMSLoaiHoatDongThanhTra.setMaPhienBan( data.getMaPhienBan() );
        rDBMSLoaiHoatDongThanhTra.setType( data.getType() );
        rDBMSLoaiHoatDongThanhTra.isUpdate = data.isUpdate;

        handleTransientField( rDBMSLoaiHoatDongThanhTra, data );

        return rDBMSLoaiHoatDongThanhTra;
    }

    @Override
    public LoaiHoatDongThanhTra convert(RDBMSLoaiHoatDongThanhTra data) {
        if ( data == null ) {
            return null;
        }

        LoaiHoatDongThanhTra loaiHoatDongThanhTra = new LoaiHoatDongThanhTra();

        loaiHoatDongThanhTra.setPrimKey( data.getPrimKey() );
        loaiHoatDongThanhTra.setUpdate( data.isUpdate() );
        loaiHoatDongThanhTra.setThoiGianTao( data.getThoiGianTao() );
        loaiHoatDongThanhTra.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        loaiHoatDongThanhTra.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        loaiHoatDongThanhTra.setMaMuc( data.getMaMuc() );
        loaiHoatDongThanhTra.setTenMuc( data.getTenMuc() );
        loaiHoatDongThanhTra.setMaPhienBan( data.getMaPhienBan() );
        loaiHoatDongThanhTra.setId( data.getId() );
        loaiHoatDongThanhTra.setType( data.getType() );
        loaiHoatDongThanhTra.isUpdate = data.isUpdate;

        handleTransientField( loaiHoatDongThanhTra, data );

        return loaiHoatDongThanhTra;
    }

    @Override
    public List<LoaiHoatDongThanhTra> convert(List<RDBMSLoaiHoatDongThanhTra> data) {
        if ( data == null ) {
            return null;
        }

        List<LoaiHoatDongThanhTra> list = new ArrayList<LoaiHoatDongThanhTra>( data.size() );
        for ( RDBMSLoaiHoatDongThanhTra rDBMSLoaiHoatDongThanhTra : data ) {
            list.add( convert( rDBMSLoaiHoatDongThanhTra ) );
        }

        return list;
    }
}
