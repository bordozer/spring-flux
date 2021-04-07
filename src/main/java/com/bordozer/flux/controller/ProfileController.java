package com.bordozer.flux.controller;

import com.bordozer.flux.dto.Profile;
import com.bordozer.flux.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileRepository;

    @PostMapping
    Publisher<ResponseEntity<Profile>> create(@RequestBody Profile profile) {
        return this.profileRepository
                .create(profile.getEmail())
                .map(p -> ResponseEntity.created(URI.create("/profiles/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build());
    }
}
