package com.bordozer.flux.service;

import com.bordozer.flux.dto.Profile;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileService {

    Flux<Profile> all();

    Mono<Profile> findById(Long id);

    Mono<Profile> create(String email);

    Mono<Profile> update(Long id, String email);

    Mono<Boolean> delete(Long id);
}
