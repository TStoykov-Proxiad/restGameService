package com.proxiad.restGameService.repositories;

import com.proxiad.restGameService.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g " +
            "WHERE g.endTime = null")
    Optional<List<Game>> findAllActiveGames();
}