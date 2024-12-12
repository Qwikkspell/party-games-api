package com.qwikkspell.partygamesapi.controller;

import com.qwikkspell.partygamesapi.dto.GameDTO;
import com.qwikkspell.partygamesapi.entity.Game;
import com.qwikkspell.partygamesapi.repository.GameRepository;
import com.qwikkspell.partygamesapi.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameDTO> createGame(@Valid @RequestBody GameDTO gameDTO) {
        Game game = convertToEntity(gameDTO);
        Game createdGame = gameService.createGame(game);
        GameDTO responseDTO = convertToDTO(createdGame);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{gameName}")
    public ResponseEntity<GameDTO> getGame(@PathVariable String gameName) {
        return gameService.getGameByName(gameName)
                .map(game -> ResponseEntity.ok(convertToDTO(game)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{gameName}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable String gameName, @Valid @RequestBody GameDTO gameDTO) {
        Game gameDetails = convertToEntity(gameDTO);
        Game updatedGame = gameService.updateGame(gameName, gameDetails);
        GameDTO responseDTO = convertToDTO(updatedGame);
        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{gameName}")
    public ResponseEntity<GameDTO> deleteGame(@PathVariable String gameName) {
        gameService.deleteGame(gameName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<GameDTO>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        List<GameDTO> gameDTOs = games.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(gameDTOs);
    }

    private GameDTO convertToDTO(Game game) {
        return GameDTO.builder()
                .gameName(game.getGameName())
                .description(game.getDescription())
                .build();
    }

    private Game convertToEntity(GameDTO gameDTO) {
        return Game.builder()
                .gameName(gameDTO.getGameName())
                .description(gameDTO.getDescription())
                .build();
    }
}
