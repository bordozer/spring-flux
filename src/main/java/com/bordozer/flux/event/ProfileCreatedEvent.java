package com.bordozer.flux.event;

import com.bordozer.flux.dto.Profile;
import org.springframework.context.ApplicationEvent;

public class ProfileCreatedEvent extends ApplicationEvent {

    public ProfileCreatedEvent(final Profile source) {
        super(source);
    }
}
