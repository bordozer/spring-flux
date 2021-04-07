package com.bordozer.flux.exception;

public class EntityDoesNotExistException extends RuntimeException {

    public EntityDoesNotExistException(final Long id) {
        super(String.format("Entity with id=%s does not exist", id));
    }
}
