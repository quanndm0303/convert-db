package com.fds.flex.core.cadmgt.repository;

import com.fds.flex.core.cadmgt.entity.T_Model.XuLyDonThu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface XuLyDonThuRepository extends MongoRepository<XuLyDonThu, String> {


    @Query("{$and:[{'uuid': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<XuLyDonThu> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<XuLyDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDonThu': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<XuLyDonThu> findByMaDonThuAndTrangThaiDuLieu(String soHieuDonThu, String trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<XuLyDonThu> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'VuViecDonThu.MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    List<XuLyDonThu> findByVuViecDonThu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    @Query(value = "{$and:[{MaDonThu : {$regex: ?0 }}, {'TrangThaiDuLieu.MaMuc': '02'}]}", count = true)
    Long countMaDonThu(String regex);

    @Query("{$and:[{'TiepCongDan.MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<XuLyDonThu> findByTiepCongDanAndTrangThaiDuLieu(String maDinhDanhTiepCongDan, String trangThaiDuLieu_maMuc);

    @Query("{$and:[{'TiepCongDan.MaDinhDanh': {$in: ?0}},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    List<XuLyDonThu> findByTiepCongDanAndTrangThaiDuLieu(String[] maDinhDanhTiepCongDan, String trangThaiDuLieu_maMuc);

}

