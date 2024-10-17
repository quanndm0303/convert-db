package com.fds.flex.core.inspectionmgt.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.constant.CacheConstant;
import com.fds.flex.core.inspectionmgt.service.CacheService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    //@Cacheable(cacheNames = {"count"}, key = "{#queryString, #classObj.getSimpleName()}")
    public Long countByQuery(String queryString, Query query, Class classObj) {
        return mongoTemplate.count(Query.of(query).limit(-1).skip(-1), classObj);
    }

}
