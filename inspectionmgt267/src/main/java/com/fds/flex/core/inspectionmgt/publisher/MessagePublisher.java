package com.fds.flex.core.inspectionmgt.publisher;

public interface MessagePublisher {
    void publish(String topic, String message);
}
