package com.qwikkspell.partygamesapi.service;

import com.qwikkspell.partygamesapi.entity.Game;
import com.qwikkspell.partygamesapi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game createGame(Game game) {
       return gameRepository.save(game);
    }

    @Override
    public Optional<Game> getGameByName(String gameName) {
        return gameRepository.findById(gameName);
    }

    @Override
    public Game updateGame(String gameName, Game gameDetails) {
        Game existingGame = gameRepository.findById(gameName)
                .orElseThrow(() -> new RuntimeException("Game not found with name: " + gameName));
        existingGame.setDescription(gameDetails.getDescription());
        return gameRepository.save(existingGame);
    }

    @Override
    public void deleteGame(String gameName) {
        Game game = gameRepository.findById(gameName)
                .orElseThrow(() -> new RuntimeException("Game not found with name: " + gameName));

        gameRepository.delete(game);
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }








}
