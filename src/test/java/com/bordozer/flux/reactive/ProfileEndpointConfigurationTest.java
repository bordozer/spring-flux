package com.bordozer.flux.reactive;

import com.bordozer.flux.dto.ProfileDto;
import com.bordozer.flux.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProfileEndpointConfigurationTest {

    @Autowired
    private ProfileEndpointConfiguration profileEndpointConfiguration;
    @Autowired
    private ProfileHandler profileHandler;

    @MockBean
    private ProfileService profileService;

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenCorrectEmployee() {
        final WebTestClient client = WebTestClient
                .bindToRouterFunction(profileEndpointConfiguration.routes(profileHandler))
                .build();

        final ProfileDto profile = ProfileDto.builder()
                .id(1023L)
                .email("some@email.com")
                .build();

        when(profileService.findById(1023L)).thenReturn(Mono.just(profile));

        client.get()
                .uri("/profiles1/{id}", 1023L)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProfileDto.class)
                .isEqualTo(profile);
    }
}
