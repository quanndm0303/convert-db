package com.fds.flex.core.cadmgt.rdbms.service.C_Service;

import org.springframework.data.mongodb.core.query.Query;

public interface CacheService {
    Long countByQuery(String queryString, Query query, Class classObj);
}
