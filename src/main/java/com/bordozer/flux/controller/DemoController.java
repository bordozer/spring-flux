package com.bordozer.flux.controller;

import com.bordozer.flux.event.ProfileCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final Flux<ProfileCreatedEvent> events;
}
