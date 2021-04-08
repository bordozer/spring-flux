package com.bordozer.flux.reactive;

import com.bordozer.flux.entity.ProfileEntity;
import org.springframework.context.ApplicationEvent;

public class ProfileCreatedEvent extends ApplicationEvent {

    public ProfileCreatedEvent(final ProfileEntity entity) {
        super(entity);
    }
}
