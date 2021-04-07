package com.bordozer.flux.service;

import com.bordozer.flux.dto.Profile;
import reactor.core.publisher.Mono;

public interface ProfileService {
    Mono<Profile> create(String email);
}
