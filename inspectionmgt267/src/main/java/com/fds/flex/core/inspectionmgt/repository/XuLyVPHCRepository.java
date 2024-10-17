package com.fds.flex.core.inspectionmgt.repository;

import com.fds.flex.core.inspectionmgt.entity.T_Model.XuLyVPHC;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface XuLyVPHCRepository extends MongoRepository<XuLyVPHC, String> {


    @Query("{$and:[{'uuid': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<XuLyVPHC> findByUuidAndTrangThaiDuLieu(String uuid, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'SoBienBanVPHC': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    List<XuLyVPHC> findBySoBienBanVPHCAndTrangThaiDuLieu(String soBienBanVPHC, String trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'SoQuyetDinh': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    List<XuLyVPHC> findBySoQuyetDinhAndTrangThaiDuLieu(String soQuyetDinh, String trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<XuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaDinhDanh': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<XuLyVPHC> findByMaDinhDanhAndTrangThaiDuLieu(String maDinhDanh, String[] trangThaiDuLieu_MaMuc);

}
