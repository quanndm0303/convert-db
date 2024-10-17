package com.fds.flex.core.inspectionmgt.repository;

import com.fds.flex.core.inspectionmgt.entity.C_Model.HinhThucXuLy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface HinhThucXuLyRepository extends MongoRepository<HinhThucXuLy, String> {


    @Query("{$and:[{'MaMuc': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<HinhThucXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaMuc': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<HinhThucXuLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}