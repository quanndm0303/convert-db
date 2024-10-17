package com.fds.flex.core.inspectionmgt.publisher;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PublisherEvent extends ApplicationEvent {

    private final String eventType;

    public PublisherEvent(String eventType, Object source) {
        super(source);
        this.eventType = eventType;
    }

}