package com.fds.flex.core.cadmgt.repository;

import com.fds.flex.core.cadmgt.entity.T_Model.TiepCongDan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TiepCongDanRepository extends MongoRepository<TiepCongDan, String> {


    @Query("{$and:[{'uuid': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<TiepCongDan> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<TiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<TiepCongDan> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'VuViecDonThu.MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    List<TiepCongDan> findByVuViecDonThuMaDinhDanhAndTrangThaiDuLieu(String vuViecDonThu_maDinhDanh, String trangThaiDuLieu_MaMuc);

    @Query(value = "{$and:[{'SoThuTuTiep' : {$regex: ?0 }}, {'TrangThaiDuLieu.MaMuc': '02'}]}", count = true)
    Long countSoThuTuTiep(String regex);

    @Query("{$and:[{'SoThuTuTiep': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<TiepCongDan> findBySoThuTuTiepAndTrangThaiDuLieu(String soHieuDonThu, String trangThaiDuLieu_MaMuc);
}

