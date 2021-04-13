package com.bordozer.flux.reactive;

import com.bordozer.flux.dto.ProfileDto;
import com.bordozer.flux.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static com.bordozer.flux.ProfileWebClient.BASE_URL;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProfileEndpointConfigurationTest {

    private static final WebTestClient WEB_TEST_CLIENT = WebTestClient
            .bindToServer()
            .baseUrl(BASE_URL)
            .build();

    private static final ProfileDto PROFILE = ProfileDto.builder()
            .id(1023L)
            .email("some@email.com")
            .build();

    @MockBean
    private ProfileService profileService;

    @Test
    public void shouldReturnProfiles() {
        // given
        when(profileService.all()).thenReturn(Flux.fromIterable(Collections.singletonList(PROFILE)));

        // when
        WEB_TEST_CLIENT.get()
                .uri("/route/profiles")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(List.class);
    }

    @Test
    public void shouldReturnProfile() {
        // given
        when(profileService.findById(1023L)).thenReturn(Mono.just(PROFILE));

        // when
        WEB_TEST_CLIENT.get()
                .uri("/route/profiles/{id}", 1023L)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProfileDto.class)
                .isEqualTo(PROFILE);
    }
}
