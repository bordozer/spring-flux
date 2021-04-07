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
    public Mono<ProfileDto> create(String email) {
        return this.profileRepository
                .save(ProfileConverter.toEntity(email))
                .doOnSuccess(profile -> this.publisher.publishEvent(new ProfileCreatedEvent(profile)))
                .map(ProfileConverter::toDto);
    }

    @Override
    public Mono<ProfileDto> update(final Long id, final String email) {
        return this.profileRepository
                .findById(id)
                .flatMap(this.profileRepository::save)
                .map(ProfileConverter::toDto);
    }

    @Override
    public Mono<Boolean> delete(final Long id) {
        return this.profileRepository
                .findById(id)
                .flatMap(p -> this.profileRepository.deleteById(p.getId()).thenReturn(p));
    }
}
