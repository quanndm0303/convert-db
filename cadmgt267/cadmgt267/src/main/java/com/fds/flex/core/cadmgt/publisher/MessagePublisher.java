package com.fds.flex.core.cadmgt.publisher;

public interface MessagePublisher {
    void publish(String topic, String message);
}
