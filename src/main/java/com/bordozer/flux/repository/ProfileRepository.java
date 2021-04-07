package com.bordozer.flux.repository;

import com.bordozer.flux.entity.ProfileEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class ProfileRepository {

    private static final Map<Integer, ProfileEntity> ENTITIES = new ConcurrentHashMap<>();
    private static final AtomicLong index = new AtomicLong(1);

    @SneakyThrows
    public Mono<ProfileEntity> save(final ProfileEntity entity) {
        log.info("About to save profile");
        if (entity.getId() == null) {
            entity.setId(index.getAndIncrement());
        }
        return Mono.just(entity);
    }

    public Flux<ProfileEntity> findAll() {
        log.info("About to get all profiles");
        return Flux.fromIterable(ENTITIES.values());
    }

    public Mono<ProfileEntity> findById(final Long id) {
        log.info("About to get profile by ID");
        final var entity = ENTITIES.get(id);
        if (entity == null) {
            throw new IllegalArgumentException(String.format("Profile with id=%s does not exist", id));
        }
        return Mono.just(entity);
    }

    public Mono<Void> deleteById(final Long id) {
        log.info("About to delete profile");
        final var entity = ENTITIES.remove(id);
        if (entity == null) {
            throw new IllegalArgumentException(String.format("Profile with id=%s does not exist", id));
        }
        return Mono.empty();
    }
}
