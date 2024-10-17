package com.fds.flex.core.inspectionmgt.repository;

import com.fds.flex.core.inspectionmgt.entity.T_Model.KhaoSatNamTinhHinh;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface KhaoSatNamTinhHinhRepository extends MongoRepository<KhaoSatNamTinhHinh, String> {

    @Query("{$and:[{'uuid': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<KhaoSatNamTinhHinh> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<KhaoSatNamTinhHinh> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<KhaoSatNamTinhHinh> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

}
