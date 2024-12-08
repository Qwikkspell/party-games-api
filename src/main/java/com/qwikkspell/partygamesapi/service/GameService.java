package com.qwikkspell.partygamesapi.service;

import com.qwikkspell.partygamesapi.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {
    Game createGame(Game game);
    Optional<Game> getGameByName(String gameName);
    Game updateGame(String gameName, Game gameDetails);
    void deleteGame(String gameName);
    List<Game> getAllGames();
}
