package com.bordozer.flux.repository;

import com.bordozer.flux.dto.Profile;
import com.bordozer.flux.entity.ProfileEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfileRepository {

    @SneakyThrows
    public ProfileEntity save(final Profile profile) {
        log.info("About to save profile");
        Thread.sleep(500);
        log.info("About has been saved");
        final var entity = new ProfileEntity();
        entity.setId(profile.getId());
        entity.setEmail(profile.getEmail());
        return entity;
    }
}
