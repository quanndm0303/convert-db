package com.fds.flex.core.inspectionmgt.service;

import com.fds.flex.core.inspectionmgt.entity.S_Model.ThanhVienDoan;

import java.util.Optional;

public interface HoatDongThanhTra_ThanhVienDoanService {
    Optional<ThanhVienDoan> findThanhVienDoanBy(String id);

    public void deleteThanhVienDoan(ThanhVienDoan thanhVienDoan);
}
