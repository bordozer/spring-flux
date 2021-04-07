package com.bordozer.flux.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.annotation.CheckForNull;

@JsonDeserialize(
        builder = ImmutableProfile.Builder.class
)
@JsonIgnoreProperties({"initialized", "initBits"})
@Value.Immutable
public interface Profile {

    @CheckForNull
    Long getId();

    String getEmail();

    static ImmutableProfile.Builder builder() {
        return new ImmutableProfile.Builder();
    }
}
