package com.bordozer.flux.converter;

import com.bordozer.flux.dto.ProfileDto;
import com.bordozer.flux.entity.ProfileEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProfileConverter {

    public static ProfileEntity toEntity(final ProfileDto profile) {
        final var entity = new ProfileEntity();
        entity.setEmail(profile.getEmail());
        return entity;
    }

    public static ProfileDto toDto(final ProfileEntity entity) {
        return ProfileDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .build();
    }
}
