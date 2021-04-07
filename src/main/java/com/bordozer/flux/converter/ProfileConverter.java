package com.bordozer.flux.converter;

import com.bordozer.flux.dto.Profile;
import com.bordozer.flux.entity.ProfileEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProfileConverter {

    public static Profile convert(final String email) {
        return Profile.builder()
                .email(email)
                .build();
    }

    public static Profile convert(final ProfileEntity entity) {
        return Profile.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .build();
    }
}
