package com.qwikkspell.partygamesapi.repository;

import com.qwikkspell.partygamesapi.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {

}
