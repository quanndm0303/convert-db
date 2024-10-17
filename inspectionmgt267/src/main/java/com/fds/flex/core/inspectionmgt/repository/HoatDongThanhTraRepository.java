package com.fds.flex.core.inspectionmgt.repository;

import com.fds.flex.core.inspectionmgt.entity.T_Model.HoatDongThanhTra;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface HoatDongThanhTraRepository extends MongoRepository<HoatDongThanhTra, String> {

    @Query("{$and:[{'uuid': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<HoatDongThanhTra> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<HoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<HoatDongThanhTra> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'HoSoNghiepVu.MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<HoatDongThanhTra> findByHSNVMaDinhDanhAndTrangThaiDuLieu(String hsnv_MaDinhDanh, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'NhiemVuCongViec.MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<HoatDongThanhTra> findByNVCVMaDinhDanhAndTrangThaiDuLieu(String hsnv_MaDinhDanh, String[] trangThaiDuLieu_MaMuc);
}
