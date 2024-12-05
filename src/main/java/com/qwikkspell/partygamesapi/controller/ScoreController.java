package com.qwikkspell.partygamesapi.controller;

import com.qwikkspell.partygamesapi.dto.ScoreRequestDTO;
import com.qwikkspell.partygamesapi.dto.ScoreResponseDTO;
import com.qwikkspell.partygamesapi.entity.Game;
import com.qwikkspell.partygamesapi.entity.Player;
import com.qwikkspell.partygamesapi.entity.Score;
import com.qwikkspell.partygamesapi.service.GameService;
import com.qwikkspell.partygamesapi.service.PlayerService;
import com.qwikkspell.partygamesapi.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;
    private final PlayerService playerService;
    private final GameService gameService;

    @Autowired
    public ScoreController(ScoreService scoreService, PlayerService playerService, GameService gameService) {
        this.scoreService = scoreService;
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<ScoreResponseDTO> addScore(@Validated @RequestBody ScoreRequestDTO scoreRequestDTO) {
        Score score = convertToEntity(scoreRequestDTO);

        Score savedScore = scoreService.addScore(score);

        ScoreResponseDTO responseDTO = convertToDTO(savedScore);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScoreResponseDTO>> getAllScores() {
        List<Score> scores = scoreService.getAllScores();

        List<ScoreResponseDTO> responseDTOs = scores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<ScoreResponseDTO>> getTopScores(
            @RequestParam String gameName,
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<Score> topScores = scoreService.getTopScores(gameName, limit);

        List<ScoreResponseDTO> responseDTOs = topScores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/best")
    public ResponseEntity<ScoreResponseDTO> getBestScore(
            @RequestParam String playerUuid,
            @RequestParam String gameName
    ) {
        Optional<Score> bestScore = scoreService.getBestScore(playerUuid, gameName);

        return bestScore.map(score -> new ResponseEntity<>(convertToDTO(score), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreResponseDTO> updateScore(
            @PathVariable Long id,
            @Validated @RequestBody ScoreRequestDTO scoreRequestDTO
    ) {
        Score scoreDetails = convertToEntity(scoreRequestDTO);

        Score updatedScore = scoreService.updateScore(id, scoreDetails);

        return new ResponseEntity<>(convertToDTO(updatedScore), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    private Score convertToEntity(ScoreRequestDTO scoreRequestDTO) {
        Player player = playerService.getPlayerByUuid(scoreRequestDTO.getPlayerUuid())
                .orElseThrow(() -> new RuntimeException("Player not found with UUID: " + scoreRequestDTO.getPlayerUuid()));

        Game game = gameService.getGameByName(scoreRequestDTO.getGameName())
                .orElseThrow(() -> new RuntimeException("Game not found with name: " + scoreRequestDTO.getGameName()));

        return Score.builder()
                .scoreValue(scoreRequestDTO.getScoreValue())
                .achievedAt(scoreRequestDTO.getAchievedAt())
                .player(player)
                .game(game)
                .build();
    }

    private ScoreResponseDTO convertToDTO(Score score) {
        return ScoreResponseDTO.builder()
                .scoreId(score.getScoreId())
                .scoreValue(score.getScoreValue())
                .achievedAt(score.getAchievedAt())
                .playerUuid(score.getPlayer().getUuid())
                .playerUsername(score.getPlayer().getUsername())
                .gameName(score.getGame().getGameName())
                .gameDescription(score.getGame().getDescription())
                .build();
    }
}
