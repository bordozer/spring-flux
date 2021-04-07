package com.bordozer.flux.controller;

import com.bordozer.flux.event.ProfileCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final Flux<ProfileCreatedEvent> events;
    private final ObjectMapper objectMapper;

    @GetMapping(path = "/sse/profiles", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
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
