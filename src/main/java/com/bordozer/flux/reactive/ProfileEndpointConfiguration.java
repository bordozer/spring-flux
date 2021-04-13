package com.bordozer.flux.reactive;

import com.bordozer.flux.utils.CaseInsensitiveRequestPredicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProfileEndpointConfiguration {

    @Bean
    RouterFunction<ServerResponse> routes(final ProfileHandler handler) {
        return route(i(GET("/route/profiles")), handler::all)
                .andRoute(i(GET("/route/profiles/{id}")), handler::getById)
                .andRoute(i(POST("/route/profiles")), handler::create)
                .andRoute(i(PUT("/route/profiles/{id}")), handler::updateById)
                .andRoute(i(DELETE("/route/profiles/{id}")), handler::deleteById);
    }

    private static RequestPredicate i(RequestPredicate target) {
        return new CaseInsensitiveRequestPredicate(target);
    }
}
