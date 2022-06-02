package com.proxiad.restGameService.services;

import com.proxiad.restGameService.entities.Game;
import com.proxiad.restGameService.entities.User;

import java.util.List;

public interface GameService {
    Game startNewGame(User user);

    Game makeTry(Character input, Long id);

    List<Game> getActiveGames();

    List<Game> getAllGames();

    Game getById(Long id);

    boolean isOver(Long gameID);
}
