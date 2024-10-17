package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiCheDoThanhTra;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiCheDoThanhTra;
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
public class RDBMSLoaiCheDoThanhTraMapperImpl implements RDBMSLoaiCheDoThanhTraMapper {

    @Override
    public RDBMSLoaiCheDoThanhTra convert(LoaiCheDoThanhTra data) {
        if ( data == null ) {
            return null;
        }

        RDBMSLoaiCheDoThanhTra rDBMSLoaiCheDoThanhTra = new RDBMSLoaiCheDoThanhTra();

        rDBMSLoaiCheDoThanhTra.setPrimKey( data.getPrimKey() );
        rDBMSLoaiCheDoThanhTra.setUpdate( data.isUpdate() );
        rDBMSLoaiCheDoThanhTra.setThoiGianTao( data.getThoiGianTao() );
        rDBMSLoaiCheDoThanhTra.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSLoaiCheDoThanhTra.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSLoaiCheDoThanhTra.setMaMuc( data.getMaMuc() );
        rDBMSLoaiCheDoThanhTra.setTenMuc( data.getTenMuc() );
        rDBMSLoaiCheDoThanhTra.setMaPhienBan( data.getMaPhienBan() );
        rDBMSLoaiCheDoThanhTra.setType( data.getType() );
        rDBMSLoaiCheDoThanhTra.isUpdate = data.isUpdate;

        handleTransientField( rDBMSLoaiCheDoThanhTra, data );

        return rDBMSLoaiCheDoThanhTra;
    }

    @Override
    public LoaiCheDoThanhTra convert(RDBMSLoaiCheDoThanhTra data) {
        if ( data == null ) {
            return null;
        }

        LoaiCheDoThanhTra loaiCheDoThanhTra = new LoaiCheDoThanhTra();

        loaiCheDoThanhTra.setPrimKey( data.getPrimKey() );
        loaiCheDoThanhTra.setUpdate( data.isUpdate() );
        loaiCheDoThanhTra.setThoiGianTao( data.getThoiGianTao() );
        loaiCheDoThanhTra.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        loaiCheDoThanhTra.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        loaiCheDoThanhTra.setMaMuc( data.getMaMuc() );
        loaiCheDoThanhTra.setTenMuc( data.getTenMuc() );
        loaiCheDoThanhTra.setMaPhienBan( data.getMaPhienBan() );
        loaiCheDoThanhTra.setId( data.getId() );
        loaiCheDoThanhTra.setType( data.getType() );
        loaiCheDoThanhTra.isUpdate = data.isUpdate;

        handleTransientField( loaiCheDoThanhTra, data );

        return loaiCheDoThanhTra;
    }

    @Override
    public List<LoaiCheDoThanhTra> convert(List<RDBMSLoaiCheDoThanhTra> data) {
        if ( data == null ) {
            return null;
        }

        List<LoaiCheDoThanhTra> list = new ArrayList<LoaiCheDoThanhTra>( data.size() );
        for ( RDBMSLoaiCheDoThanhTra rDBMSLoaiCheDoThanhTra : data ) {
            list.add( convert( rDBMSLoaiCheDoThanhTra ) );
        }

        return list;
    }
}
