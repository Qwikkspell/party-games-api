package com.qwikkspell.partygamesapi.exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String uuid) {
        super("Player not found with UUID: " + uuid);
    }
}
