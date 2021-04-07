package com.bordozer.flux.service;

import com.bordozer.flux.dto.Profile;
import com.bordozer.flux.event.ProfileCreatedEvent;
import com.bordozer.flux.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ApplicationEventPublisher publisher;
    private final ProfileRepository profileRepository;

    @Override
    public Mono<Profile> create(String email) {
        return this.profileRepository
                .save(Profile.builder().email(email).build())
                .doOnSuccess(profile -> this.publisher.publishEvent(new ProfileCreatedEvent(profile)))
                .map(entity -> Profile.builder().id(entity.getId()).email(entity.getEmail()).build());
    }
}
