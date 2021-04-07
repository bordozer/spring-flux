package com.bordozer.flux.repository;

import com.bordozer.flux.dto.Profile;
import com.bordozer.flux.entity.ProfileEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ProfileRepository {

    private static final Map<Integer, ProfileEntity> ENTITIES = new ConcurrentHashMap<>();

    @SneakyThrows
    public Mono<ProfileEntity> save(final Profile profile) {
        log.info("About to save profile");
        final var entity = new ProfileEntity();
        entity.setId(profile.getId());
        entity.setEmail(profile.getEmail());
        return Mono.just(entity);
    }

    public Flux<ProfileEntity> findAll() {
        return Flux.fromIterable(ENTITIES.values());
    }

    public Mono<ProfileEntity> findById(final Long id) {
        final var entity = ENTITIES.get(id);
        if (entity == null) {
            throw new IllegalArgumentException(String.format("Profile with id=%s does not exist", id));
        }
        return Mono.just(entity);
    }

    public Mono<Void> deleteById(final Long id) {
        final var entity = ENTITIES.remove(id);
        if (entity == null) {
            throw new IllegalArgumentException(String.format("Profile with id=%s does not exist", id));
        }
    }
}
