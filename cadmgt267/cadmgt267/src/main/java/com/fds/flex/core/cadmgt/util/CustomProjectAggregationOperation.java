package com.fds.flex.core.cadmgt.util;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

public class CustomProjectAggregationOperation implements AggregationOperation {
    private final String jsonOperation;

    public CustomProjectAggregationOperation(String jsonOperation) {
        this.jsonOperation = jsonOperation;
    }

    @Override
    public Document toDocument(AggregationOperationContext aggregationOperationContext) {
        return aggregationOperationContext.getMappedObject(Document.parse(jsonOperation));
    }
}
