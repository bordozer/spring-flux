package com.bordozer.flux.reactive;

import com.bordozer.flux.dto.ProfileDto;
import com.bordozer.flux.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static com.bordozer.flux.ProfileWebClient.BASE_URL;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProfileEndpointConfigurationTest {

    private static final ProfileDto PROFILE = ProfileDto.builder()
            .id(1023L)
            .email("some@email.com")
            .build();

    @Autowired
    private ProfileEndpointConfiguration profileEndpointConfiguration;
    @Autowired
    private ProfileHandler profileHandler;

    @MockBean
    private ProfileService profileService;

    @Test
    public void should() {
        final WebTestClient client = WebTestClient
                .bindToRouterFunction(profileEndpointConfiguration.routes(profileHandler))
                .build();

        when(profileService.findById(1023L)).thenReturn(Mono.just(PROFILE));

        client.get()
                .uri("/profiles/{id}", 1023L)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProfileDto.class)
                .isEqualTo(PROFILE);
    }
}
