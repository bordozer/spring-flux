package com.bordozer.flux.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(
        builder = ImmutableProfile.Builder.class
)
@JsonIgnoreProperties({"initialized", "initBits"})
@Value.Immutable
public interface Profile {

    String getI();

    String getEmail();

    static ImmutableProfile.Builder builder() {
        return new ImmutableProfile.Builder();
    }
}
