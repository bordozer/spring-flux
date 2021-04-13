package com.bordozer.flux.rest;

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

@RestController
@RequestMapping(value = "/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    Publisher<ProfileDto> getAll() {
        return profileService.all();
    }

    @GetMapping("/{id}")
    Publisher<ProfileDto> getById(@PathVariable("id") final Long id) {
        return profileService.findById(id);
    }

    @PostMapping
    Publisher<ProfileDto> create(@RequestBody final ProfileDto profile) {
        return profileService.create(profile);
    }

    @PutMapping("/{id}")
    Publisher<ProfileDto> updateById(@PathVariable final Long id, @RequestBody final ProfileDto profile) {
        return profileService.update(profile);
    }

    @DeleteMapping("/{id}")
    Publisher<ResponseEntity<Void>> deleteById(@PathVariable final Long id) {
        return profileService.delete(id)
                .map($ -> ResponseEntity.noContent().build());
    }
}
