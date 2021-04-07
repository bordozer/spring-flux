package com.bordozer.flux.service;

import com.bordozer.flux.dto.ProfileDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileService {

    Flux<ProfileDto> all();

    Mono<ProfileDto> findById(Long id);

    Mono<ProfileDto> create(String email);

    Mono<ProfileDto> update(Long id, String email);

    Mono<Boolean> delete(Long id);
}
