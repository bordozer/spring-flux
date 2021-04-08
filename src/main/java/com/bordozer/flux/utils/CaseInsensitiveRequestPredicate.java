package com.bordozer.flux.utils;

import org.springframework.http.server.PathContainer;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;

import java.net.URI;

public class CaseInsensitiveRequestPredicate implements RequestPredicate {

    private final RequestPredicate target;

    public CaseInsensitiveRequestPredicate(final RequestPredicate target) {
        this.target = target;
    }

    @Override
    public boolean test(final ServerRequest request) {
        return this.target.test(new LowerCaseUriServerRequestWrapper(request));
    }

    @Override
    public String toString() {
        return this.target.toString();
    }

    private static class LowerCaseUriServerRequestWrapper extends ServerRequestWrapper {

        LowerCaseUriServerRequestWrapper(final ServerRequest delegate) {
            super(delegate);
        }

        @Override
        public URI uri() {
            return URI.create(super.uri().toString().toLowerCase());
        }

        @Override
        public String path() {
            return uri().getRawPath();
        }

        @Override
        public PathContainer pathContainer() {
            return PathContainer.parsePath(path());
        }
    }
}
