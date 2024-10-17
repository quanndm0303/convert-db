package com.fds.flex.core.cadmgt.service.impl;

import com.fds.flex.core.cadmgt.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheServiceImpl implements CacheService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Long countByQuery(String queryString, Query query, Class classObj) {
        return mongoTemplate.count(Query.of(query).limit(-1).skip(-1), classObj);
    }
}
