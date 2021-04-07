package com.bordozer.flux.controller;

import com.bordozer.flux.event.ProfileCreatedEvent;
import com.bordozer.flux.event.ProfileCreatedEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class DemoController {

    private final Flux<ProfileCreatedEvent> events;
    private final ObjectMapper objectMapper;

    public DemoController(final ProfileCreatedEventPublisher eventPublisher, final ObjectMapper objectMapper) {
        this.events = Flux.create(eventPublisher).share();
        this.objectMapper = objectMapper;
    }

    @GetMapping(path = "/profiles", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> profiles() {
        return this.events.map(event -> {
            try {
                return objectMapper.writeValueAsString(event) + "\n\n";
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
