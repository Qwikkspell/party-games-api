package com.qwikkspell.partygamesapi.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String gameName) {
        super("Game not found: " + gameName);
    }
}
