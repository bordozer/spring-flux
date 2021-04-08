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

    Mono<ServerResponse> getById(final ServerRequest r) {
        return defaultResponse(this.profileService.findById(id(r)));
    }

    Mono<ServerResponse> all(final ServerRequest r) {
        return defaultResponse(this.profileService.all());
    }

    Mono<ServerResponse> create(final ServerRequest request) {
        final Flux<ProfileDto> flux = request
                .bodyToFlux(ProfileDto.class)
                .flatMap(this.profileService::create);
        return defaultWriteResponse(flux);
    }

    Mono<ServerResponse> updateById(final ServerRequest r) {
        final Flux<ProfileDto> id = r.bodyToFlux(ProfileDto.class)
                .flatMap(this.profileService::update);
        return defaultResponse(id);
    }

    Mono<ServerResponse> deleteById(final ServerRequest r) {
        return noContentResponse();
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

    private static Mono<ServerResponse> noContentResponse() {
        return ServerResponse.noContent().build();
    }

    private static Long id(final ServerRequest r) {
        return Long.valueOf(r.pathVariable("id"));
    }
}
