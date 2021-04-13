package com.bordozer.flux.reactive;

import com.bordozer.flux.dto.ProfileDto;
import com.bordozer.flux.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
class ProfileHandler {

    private final ProfileService profileService;

    Mono<ServerResponse> all(final ServerRequest request) {
        return defaultResponse(profileService.all());
    }

    Mono<ServerResponse> getById(final ServerRequest request) {
        return defaultResponse(profileService.findById(id(request)));
    }

    Mono<ServerResponse> create(final ServerRequest request) {
        final Flux<ProfileDto> flux = request
                .bodyToFlux(ProfileDto.class)
                .flatMap(profileService::create);
        return defaultWriteResponse(flux);
    }

    Mono<ServerResponse> updateById(final ServerRequest request) {
        final Flux<ProfileDto> id = request
                .bodyToFlux(ProfileDto.class)
                .flatMap(profileService::update);
        return defaultResponse(id);
    }

    Mono<ServerResponse> deleteById(final ServerRequest request) {
        return ServerResponse.noContent().build();
    }

    private static Mono<ServerResponse> defaultWriteResponse(final Publisher<ProfileDto> profiles) {
        return Mono
                .from(profiles)
                .flatMap(p -> ServerResponse
                        .created(URI.create("/profiles/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                );
    }

    private static Mono<ServerResponse> defaultResponse(final Publisher<ProfileDto> profiles) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profiles, ProfileDto.class);
    }

    private static Long id(final ServerRequest r) {
        return Long.valueOf(r.pathVariable("id"));
    }
}
