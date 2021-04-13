package com.bordozer.flux.reactive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ServerSentEventController {

    private final Flux<ProfileCreatedEvent> events;
    private final ObjectMapper objectMapper;

    public ServerSentEventController(final ProfileCreatedEventPublisher eventPublisher, final ObjectMapper objectMapper) {
        this.events = Flux.create(eventPublisher).share();
        this.objectMapper = objectMapper;
    }

    @GetMapping(path = "/sse/profiles", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> profiles() {
        return events.map(event -> {
            try {
                return objectMapper.writeValueAsString(event) + "\n\n";
            } catch (final JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
