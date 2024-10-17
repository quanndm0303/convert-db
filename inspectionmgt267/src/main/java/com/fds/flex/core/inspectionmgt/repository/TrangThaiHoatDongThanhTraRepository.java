package com.fds.flex.core.inspectionmgt.repository;

import com.fds.flex.core.inspectionmgt.entity.C_Model.TrangThaiHoatDongThanhTra;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TrangThaiHoatDongThanhTraRepository extends MongoRepository<TrangThaiHoatDongThanhTra, String> {


    @Query("{$and:[{'MaMuc': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<TrangThaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    @Query("{'TrangThaiDuLieu.MaMuc': ?0}")
    List<TrangThaiHoatDongThanhTra> findByTrangThaiDuLieu(String trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaMuc': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<TrangThaiHoatDongThanhTra> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
