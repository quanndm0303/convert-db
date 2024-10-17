package com.fds.flex.core.inspectionmgt.rdbms.service.T_Service.impl;

import com.fds.flex.core.inspectionmgt.rdbms.entity.T_Model.RDBMSBaoCaoTongHop;
import com.fds.flex.core.inspectionmgt.rdbms.repository.T_Repository.RDBMSBaoCaoTongHopRepository;
import com.fds.flex.core.inspectionmgt.rdbms.service.T_Service.RDBMSBaoCaoTongHopService;
import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;
import java.util.Optional;

@Service
public class RDBMSBaoCaoTongHopServiceImpl implements RDBMSBaoCaoTongHopService {

    //@Autowired
    private RDBMSBaoCaoTongHopRepository rdbmsBaoCaoTongHopRepository;

    @Override
    @Cacheable(value = "BaoCaoTongHop", key = "#id", condition = "#result != null && #result.isPresent()")
    public Optional<RDBMSBaoCaoTongHop> findById(String id) {
        return rdbmsBaoCaoTongHopRepository.findById(Long.valueOf(id));
    }

    @Override
    @CacheEvict(value = "BaoCaoTongHop", allEntries = true)
    public void deleteBaoCaoTongHop(RDBMSBaoCaoTongHop rdbmsBaoCaoTongHop) {
        rdbmsBaoCaoTongHopRepository.deleteById(rdbmsBaoCaoTongHop.getId());
    }

    @Override
    public void deleteBaoCaoTongHopByMaLopDuLieu_LopDuLieu(Long maLopDuLieu, String lopDuLieu) {

    }

    @Override
    @CacheEvict(value = "BaoCaoTongHop", allEntries = true)
    public RDBMSBaoCaoTongHop updateBaoCaoTongHop(RDBMSBaoCaoTongHop rdbmsBaoCaoTongHop) {
        return rdbmsBaoCaoTongHopRepository.saveAndFlush(rdbmsBaoCaoTongHop);
    }

    @Override
    @CacheEvict(value = "BaoCaoTongHop", allEntries = true)
    @Transactional(rollbackFor = {RuntimeException.class})
    public Map<String, RDBMSBaoCaoTongHop> update(Map<String, RDBMSBaoCaoTongHop> map) {
        map.forEach((k, v) -> {
            if (k.equals(TrangThaiDuLieu.TrangThai.CanXoa.getMaMuc())) {
                rdbmsBaoCaoTongHopRepository.delete(v);
            } else {
                rdbmsBaoCaoTongHopRepository.save(v);
            }
        });

        return map;
    }
}
