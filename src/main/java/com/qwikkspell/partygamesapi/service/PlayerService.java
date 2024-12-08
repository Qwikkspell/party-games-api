package com.qwikkspell.partygamesapi.service;

import com.qwikkspell.partygamesapi.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    Player createPlayer(Player player);
    Optional<Player> getPlayerByUuid(String uuid);
    Optional<Player> getPlayerByUsername(String username);
    Player updatePlayer(String uuid, Player playerDetails);
    void deletePlayer(String uuid);
    List<Player> getAllPlayers();
}
