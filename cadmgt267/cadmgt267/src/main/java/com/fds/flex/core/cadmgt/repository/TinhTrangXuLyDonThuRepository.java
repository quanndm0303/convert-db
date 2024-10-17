package com.fds.flex.core.cadmgt.repository;

import com.fds.flex.core.cadmgt.entity.C_Model.TinhTrangXuLyDonThu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TinhTrangXuLyDonThuRepository extends MongoRepository<TinhTrangXuLyDonThu, String> {


    @Query("{$and:[{'MaMuc': ?0},{'TrangThaiDuLieu.MaMuc': {$in: ?1}}]}")
    List<TinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String[] trangThaiDuLieu_MaMuc);

    @Query("{$and:[{'SuDungChoXuLyDonThu': true},{'TrangThaiDuLieu.MaMuc': '02'}]}")
    List<TinhTrangXuLyDonThu> findBySuDungChoXuLyDonThu_TTDL();

    @Query("{$and:[{'SuDungChoVuViecDonThu': true},{'TrangThaiDuLieu.MaMuc': '02'}]}")
    List<TinhTrangXuLyDonThu> findBySuDungChoVuViecDonThu_TTDL();

    @Query("{$and:[{'MaMuc': ?0},{'TrangThaiDuLieu.MaMuc': ?1}]}")
    Optional<TinhTrangXuLyDonThu> findByMaMucAndTrangThaiDuLieu(String maMuc, String trangThaiDuLieu_MaMuc);
}
