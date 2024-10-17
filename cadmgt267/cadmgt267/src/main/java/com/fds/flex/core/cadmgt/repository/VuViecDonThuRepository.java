package com.fds.flex.core.cadmgt.repository;

import com.fds.flex.core.cadmgt.entity.T_Model.VuViecDonThu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface VuViecDonThuRepository extends MongoRepository<VuViecDonThu, String> {

    @Query("{$and:[{'uuid': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<VuViecDonThu> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<VuViecDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<VuViecDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'HoSoNghiepVu.MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<VuViecDonThu> findByHSNV_TTDL(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);
}

