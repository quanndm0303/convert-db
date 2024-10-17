package com.fds.flex.core.cadmgt.repository;

import com.fds.flex.core.cadmgt.entity.C_Model.TuCachPhapLy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TuCachPhapLyRepository extends MongoRepository<TuCachPhapLy, String> {


    @Query("{$and:[{'MaMuc': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<TuCachPhapLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'MaMuc': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<TuCachPhapLy> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
