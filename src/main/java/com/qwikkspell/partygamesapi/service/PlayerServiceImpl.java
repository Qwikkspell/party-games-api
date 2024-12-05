package com.qwikkspell.partygamesapi.service;

import com.qwikkspell.partygamesapi.entity.Player;
import com.qwikkspell.partygamesapi.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Optional<Player> getPlayerByUuid(String uuid) {
        return playerRepository.findById(uuid);
    }

    @Override
    public Optional<Player> getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    @Override
    public Player updatePlayer(String uuid, Player playerDetails) {
        Player existingPlayer = playerRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Player not found with UUID: " + uuid));

        existingPlayer.setUsername(playerDetails.getUsername());
        existingPlayer.setJoinedAt(playerDetails.getJoinedAt());

        return playerRepository.save(existingPlayer);
    }

    @Override
    public void deletePlayer(String uuid) {
        Player player = playerRepository.findById(uuid)
                        .orElseThrow(() -> new RuntimeException("Player not found with UUID: " + uuid));
        playerRepository.delete(player);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}
