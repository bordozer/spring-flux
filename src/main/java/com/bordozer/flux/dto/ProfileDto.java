package com.bordozer.flux.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.annotation.CheckForNull;

@JsonDeserialize(
        builder = ImmutableProfileDto.Builder.class
)
@JsonIgnoreProperties({"initialized", "initBits"})
@Value.Immutable
public interface ProfileDto {

    @CheckForNull
    Long getId();

    String getEmail();

    static ImmutableProfileDto.Builder builder() {
        return new ImmutableProfileDto.Builder();
    }
}
