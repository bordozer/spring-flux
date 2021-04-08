package com.bordozer.flux;

import com.bordozer.flux.dto.ProfileDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ProfileWebClient {

    private static final String BASE_URL = "http://localhost:8099";

    private final WebClient client = WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Test
    void shouldGetProfiles() {
        // given

        // when
        final Flux<ProfileDto> profileFlux = client.get()
                .uri("/profiles")
                .retrieve()
                .bodyToFlux(ProfileDto.class);

        final Disposable subscribe = profileFlux
                .log()
                .subscribe(p -> log.info("======= Profile: id={}, email=\"{}\"", p.getId(), p.getEmail()));

        // then
    }

    @Test
    void shouldGetProfile() {
        // given

        // when
        final Mono<ProfileDto> profileMono = client.get()
                .uri("/profiles/{id}", "1")
                .retrieve()
                .bodyToMono(ProfileDto.class);

        profileMono
                .log()
                .subscribe(p -> log.info("======= Profile: id={}, email=\"{}\"", p.getId(), p.getEmail()));
        profileMono.block();

        // then
    }

    @Test
    void shouldGet() {
        WebTestClient
                .bindToServer()
                .baseUrl(BASE_URL)
                .build()
                .get()
                .uri("/profiles/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().jsonPath("email").isEqualTo("email1@domain.com");
    }

    @Test
    void should111() {
        Flux.just("red", "white", "blue")
                .log()
                .map(String::toUpperCase)
                .doOnNext(s -> log.info("=====> {}", s))
                .subscribe(System.out::println);
    }

    @Test
    void should222() {
        Flux.just("red", "white", "blue")
                .log()
                .map(String::toUpperCase)
                .doOnNext(s -> log.info("=====> {}", s))
                .subscribe();
    }
}
