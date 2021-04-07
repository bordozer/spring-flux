package com.bordozer.flux.service;

import com.bordozer.flux.converter.ProfileConverter;
import com.bordozer.flux.dto.Profile;
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
    public Flux<Profile> all() {
        return profileRepository.findAll()
                .map(ProfileConverter::convert);
    }

    @Override
    public Mono<Profile> findById(final Long id) {
        return profileRepository.findById(id)
                .map(ProfileConverter::convert);
    }

    @Override
    public Mono<Profile> create(String email) {
        return this.profileRepository
                .save(ProfileConverter.convert(email))
                .doOnSuccess(profile -> this.publisher.publishEvent(new ProfileCreatedEvent(profile)))
                .map(ProfileConverter::convert);
    }

    @Override
    public Mono<Profile> update(final Long id, final String email) {
        return this.profileRepository
                .findById(id)
                .map(p -> Profile.builder().id(p.getId()).email(email))
                .flatMap(this.profileRepository::save)
                .map(ProfileConverter::convert);
    }

    @Override
    public Mono<Boolean> delete(final Long id) {
        return this.profileRepository
                .findById(id)
                .flatMap(p -> this.profileRepository.deleteById(p.getId()).thenReturn(p));
    }
}
