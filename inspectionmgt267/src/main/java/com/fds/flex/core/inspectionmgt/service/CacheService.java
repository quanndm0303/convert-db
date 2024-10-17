package com.fds.flex.core.inspectionmgt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fds.flex.context.model.User;
import org.springframework.data.mongodb.core.query.Query;

public interface CacheService {
    Long countByQuery(String queryString, Query query, Class classObj);
}
