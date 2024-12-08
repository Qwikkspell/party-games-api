package com.qwikkspell.partygamesapi.controller;

import com.qwikkspell.partygamesapi.dto.PlayerDTO;
import com.qwikkspell.partygamesapi.entity.Player;
import com.qwikkspell.partygamesapi.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlayerDTO> createPlayer(@Valid @RequestBody PlayerDTO playerDTO) {
        Player player = convertToEntity(playerDTO);
        Player createdPlayer = playerService.createPlayer(player);
        PlayerDTO responseDTO = convertToDTO(createdPlayer);
        return ResponseEntity.status(201).body(responseDTO);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PlayerDTO> getPlayerByUuid(@PathVariable String uuid) {
        return playerService.getPlayerByUuid(uuid)
                .map(player -> ResponseEntity.ok(convertToDTO(player)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable String uuid, @Valid @RequestBody PlayerDTO playerDTO) {
        Player playerDetails = convertToEntity(playerDTO);
        Player updatedPlayer = playerService.updatePlayer(uuid, playerDetails);
        return ResponseEntity.ok(convertToDTO(updatedPlayer));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<PlayerDTO> deletePlayer(@PathVariable String uuid) {
        playerService.deletePlayer(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        List<PlayerDTO> playerDTOs = players.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }



    private PlayerDTO convertToDTO(Player player) {
       return PlayerDTO.builder()
                .uuid(player.getUuid())
                .username(player.getUsername())
                .joinedAt(player.getJoinedAt())
                .build();
    }

    private Player convertToEntity(PlayerDTO playerDTO) {
        return Player.builder()
                .uuid(playerDTO.getUuid())
                .username(playerDTO.getUsername())
                .joinedAt(playerDTO.getJoinedAt())
                .build();
    }
}
