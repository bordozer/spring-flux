package com.bordozer.flux.controller;

import com.bordozer.flux.dto.ProfileDto;
import com.bordozer.flux.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping(value = "/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    Publisher<ProfileDto> getAll() {
        return this.profileService.all();
    }

    @GetMapping("/{id}")
    Publisher<ProfileDto> getById(@PathVariable("id") final Long id) {
        return this.profileService.findById(id);
    }

    @PostMapping
    Publisher<ResponseEntity<ProfileDto>> create(@RequestBody final ProfileDto profile) {
        return this.profileService
                .create(profile.getEmail())
                .map(p -> ResponseEntity.created(URI.create("/profiles/" + p.getId()))
                        .contentType(APPLICATION_JSON)
                        .build());
    }

    @PutMapping("/{id}")
    Publisher<ResponseEntity<ProfileDto>> updateById(@PathVariable final Long id, @RequestBody final ProfileDto profile) {
        return Mono
                .just(profile)
                .flatMap(p -> this.profileService.update(id, p.getEmail()))
                .map(p -> ResponseEntity
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .build()
                );
    }

    @DeleteMapping("/{id}")
    Publisher<Void> deleteById(@PathVariable final Long id) {
        this.profileService.delete(id);
        return Mono.empty();
    }
}