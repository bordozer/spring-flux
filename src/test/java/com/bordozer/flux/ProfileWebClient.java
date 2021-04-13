package com.bordozer.flux;

import com.bordozer.flux.dto.ProfileDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ProfileWebClient {

    public static final String BASE_URL = "http://localhost:8099";

    private final WebClient client = WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @SneakyThrows
    @Test
    void shouldGetProfiles() {
        // given
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        // when
        final Flux<ProfileDto> profileFlux = client.get()
                .uri("/profiles")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(ProfileDto.class);

        profileFlux
                .log()
                .publishOn(Schedulers.fromExecutor(executor))
                .subscribe(p -> log.info("======= Profile: id={}, email=\"{}\"", p.getId(), p.getEmail()));

        // then
        executor.awaitTermination(2, TimeUnit.SECONDS);
    }

    @SneakyThrows
    @Test
    void shouldGetProfile() {
        // given
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        // when
        final Mono<ProfileDto> profileMono = client.get()
                .uri("/profiles/{id}", "1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ProfileDto.class);

        profileMono
                .log()
                .publishOn(Schedulers.fromExecutor(executor))
                .subscribe(p -> log.info("======= Profile: id={}, email=\"{}\"", p.getId(), p.getEmail()));

        // then
        executor.awaitTermination(2, TimeUnit.SECONDS);
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
