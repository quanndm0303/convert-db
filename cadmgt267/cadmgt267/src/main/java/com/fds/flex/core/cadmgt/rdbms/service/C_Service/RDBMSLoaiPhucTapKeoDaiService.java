package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import com.fds.flex.core.cadmgt.rdbms.entity.C_Model.RDBMSLoaiPhucTapKeoDai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RDBMSLoaiPhucTapKeoDaiService {

    Optional<RDBMSLoaiPhucTapKeoDai> findById(String id);

    void deleteLoaiPhucTapKeoDai(RDBMSLoaiPhucTapKeoDai rdbmsLoaiPhucTapKeoDai);

    RDBMSLoaiPhucTapKeoDai updateLoaiPhucTapKeoDai(RDBMSLoaiPhucTapKeoDai rdbmsLoaiPhucTapKeoDai);

    Map<String, RDBMSLoaiPhucTapKeoDai> update(Map<String, RDBMSLoaiPhucTapKeoDai> map);

    Page<RDBMSLoaiPhucTapKeoDai> filter(String keyword, String[] trangThaiDuLieu_MaMuc, Pageable pageable);

    List<RDBMSLoaiPhucTapKeoDai> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    Optional<RDBMSLoaiPhucTapKeoDai> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
