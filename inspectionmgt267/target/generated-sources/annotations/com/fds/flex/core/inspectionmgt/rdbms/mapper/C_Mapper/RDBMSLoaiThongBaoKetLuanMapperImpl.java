package com.fds.flex.core.inspectionmgt.rdbms.mapper.C_Mapper;

import com.fds.flex.core.inspectionmgt.entity.C_Model.LoaiThongBaoKetLuan;
import com.fds.flex.core.inspectionmgt.rdbms.entity.C_Model.RDBMSLoaiThongBaoKetLuan;
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
public class RDBMSLoaiThongBaoKetLuanMapperImpl implements RDBMSLoaiThongBaoKetLuanMapper {

    @Override
    public RDBMSLoaiThongBaoKetLuan convert(LoaiThongBaoKetLuan data) {
        if ( data == null ) {
            return null;
        }

        RDBMSLoaiThongBaoKetLuan rDBMSLoaiThongBaoKetLuan = new RDBMSLoaiThongBaoKetLuan();

        rDBMSLoaiThongBaoKetLuan.setPrimKey( data.getPrimKey() );
        rDBMSLoaiThongBaoKetLuan.setUpdate( data.isUpdate() );
        rDBMSLoaiThongBaoKetLuan.setThoiGianTao( data.getThoiGianTao() );
        rDBMSLoaiThongBaoKetLuan.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        rDBMSLoaiThongBaoKetLuan.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        rDBMSLoaiThongBaoKetLuan.setMaMuc( data.getMaMuc() );
        rDBMSLoaiThongBaoKetLuan.setTenMuc( data.getTenMuc() );
        rDBMSLoaiThongBaoKetLuan.setMaPhienBan( data.getMaPhienBan() );
        rDBMSLoaiThongBaoKetLuan.setType( data.getType() );
        rDBMSLoaiThongBaoKetLuan.isUpdate = data.isUpdate;

        handleTransientField( rDBMSLoaiThongBaoKetLuan, data );

        return rDBMSLoaiThongBaoKetLuan;
    }

    @Override
    public LoaiThongBaoKetLuan convert(RDBMSLoaiThongBaoKetLuan data) {
        if ( data == null ) {
            return null;
        }

        LoaiThongBaoKetLuan loaiThongBaoKetLuan = new LoaiThongBaoKetLuan();

        loaiThongBaoKetLuan.setPrimKey( data.getPrimKey() );
        loaiThongBaoKetLuan.setUpdate( data.isUpdate() );
        loaiThongBaoKetLuan.setThoiGianTao( data.getThoiGianTao() );
        loaiThongBaoKetLuan.setThoiGianCapNhat( data.getThoiGianCapNhat() );
        loaiThongBaoKetLuan.setTrangThaiDuLieu( data.getTrangThaiDuLieu() );
        loaiThongBaoKetLuan.setMaMuc( data.getMaMuc() );
        loaiThongBaoKetLuan.setTenMuc( data.getTenMuc() );
        loaiThongBaoKetLuan.setMaPhienBan( data.getMaPhienBan() );
        loaiThongBaoKetLuan.setId( data.getId() );
        loaiThongBaoKetLuan.setType( data.getType() );
        loaiThongBaoKetLuan.isUpdate = data.isUpdate;

        handleTransientField( loaiThongBaoKetLuan, data );

        return loaiThongBaoKetLuan;
    }

    @Override
    public List<LoaiThongBaoKetLuan> convert(List<RDBMSLoaiThongBaoKetLuan> data) {
        if ( data == null ) {
            return null;
        }

        List<LoaiThongBaoKetLuan> list = new ArrayList<LoaiThongBaoKetLuan>( data.size() );
        for ( RDBMSLoaiThongBaoKetLuan rDBMSLoaiThongBaoKetLuan : data ) {
            list.add( convert( rDBMSLoaiThongBaoKetLuan ) );
        }

        return list;
    }
}
