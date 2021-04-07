package com.bordozer.flux.service;

import com.bordozer.flux.converter.ProfileConverter;
import com.bordozer.flux.dto.ProfileDto;
import com.bordozer.flux.event.ProfileCreatedEvent;
import com.bordozer.flux.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ApplicationEventPublisher publisher;
    private final ProfileRepository profileRepository;

    @Override
    public Flux<ProfileDto> all() {
        return profileRepository.findAll()
                .map(ProfileConverter::toDto);
    }

    @Override
    public Mono<ProfileDto> findById(final Long id) {
        return profileRepository.findById(id)
                .map(ProfileConverter::toDto);
    }

    @Override
    public Mono<ProfileDto> create(final ProfileDto profile) {
        return this.profileRepository
                .save(ProfileConverter.toEntity(profile))
                .doOnSuccess(saved -> this.publisher.publishEvent(new ProfileCreatedEvent(saved)))
                .map(ProfileConverter::toDto);
    }

    @Override
    public Mono<ProfileDto> update(final ProfileDto profile) {
        return this.profileRepository
                .findById(Objects.requireNonNull(profile.getId()))
                .map(entity -> {
                    entity.setEmail(profile.getEmail());
                    return entity;
                })
                .flatMap(this.profileRepository::save)
                .map(ProfileConverter::toDto);
    }

    @Override
    public Mono<Void> delete(final Long id) {
        return this.profileRepository
                .deleteById(id);
    }
}
