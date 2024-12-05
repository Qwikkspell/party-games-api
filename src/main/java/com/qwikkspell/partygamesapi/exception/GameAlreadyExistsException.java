package com.qwikkspell.partygamesapi.exception;

public class GameAlreadyExistsException extends RuntimeException {
    public GameAlreadyExistsException(String gameName) {
        super("Game already exists: " + gameName);
    }
}
