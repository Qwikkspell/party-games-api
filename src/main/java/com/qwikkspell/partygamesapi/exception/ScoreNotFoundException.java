package com.qwikkspell.partygamesapi.exception;

public class ScoreNotFoundException extends RuntimeException {
    public ScoreNotFoundException(Long scoreId) {
        super("Score not found with id " + scoreId);
    }
}
